package com.shopsphere.controller;

import com.shopsphere.model.Address;
import com.shopsphere.model.Coupon;
import com.shopsphere.model.Order;
import com.shopsphere.model.User;
import com.shopsphere.service.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Checkout: address, coupon and (mock) payment. The browser only sends choices
 * (address id, coupon code, payment method); prices and totals are recomputed on the server.
 */
@WebServlet("/checkout")
public class CheckoutServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private final CartService cartService = new CartService();
    private final CheckoutService checkout = new CheckoutService();
    private final AdminService accountService = new AdminService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = currentUser(req);
        try {
            CartSummary cart = cartService.getCart(u.getUserId());
            if (cart.isEmpty()) {
                flash(req, "warning", "Your cart is empty.");
                redirect(req, resp, "/cart");
                return;
            }
            double discount = 0;
            String code = (String) req.getSession().getAttribute("couponCode");
            if (code != null) {
                try {
                    Coupon cp = checkout.validateCoupon(u.getUserId(), code, cart.getSubtotal());
                    discount = Math.round(cp.calculateDiscount(cart.getSubtotal()) * 100) / 100.0;
                } catch (ServiceException e) {          // coupon no longer valid (e.g. cart changed)
                    req.getSession().removeAttribute("couponCode");
                    code = null;
                    req.setAttribute("couponError", e.getMessage());
                }
            }
            req.setAttribute("cart", cart);
            req.setAttribute("addresses", accountService.addresses(u.getUserId()));
            req.setAttribute("couponCode", code);
            req.setAttribute("discount", discount);
            req.setAttribute("total", Math.round((cart.getSubtotal() - discount) * 100) / 100.0);
            render(req, resp, "checkout.jsp");
        } catch (ServiceException e) {
            fail(req, resp, e, "/cart");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = currentUser(req);
        HttpSession session = req.getSession();
        String action = req.getParameter("action");
        try {
            if ("applyCoupon".equals(action)) {
                String code = req.getParameter("code") == null ? "" : req.getParameter("code").trim().toUpperCase();
                checkout.validateCoupon(u.getUserId(), code, cartService.getCart(u.getUserId()).getSubtotal());
                session.setAttribute("couponCode", code);
                flash(req, "success", "Coupon " + code + " applied.");
            } else if ("removeCoupon".equals(action)) {
                session.removeAttribute("couponCode");
                flash(req, "info", "Coupon removed.");
            } else if ("addAddress".equals(action)) {
                Address a = new Address();
                a.setUserId(u.getUserId());
                a.setAddressLine(req.getParameter("addressLine"));
                a.setCity(req.getParameter("city"));
                a.setState(req.getParameter("state"));
                a.setPincode(req.getParameter("pincode"));
                a.setAddressType(req.getParameter("addressType"));
                accountService.addAddress(a);
                flash(req, "success", "Address saved.");
            } else if ("place".equals(action)) {
                String payment = req.getParameter("paymentMethod");
                validatePaymentDetails(req, payment);
                Order o = checkout.placeOrder(u.getUserId(), intParam(req, "addressId", -1),
                        (String) session.getAttribute("couponCode"), payment);
                session.removeAttribute("couponCode");
                refreshCartCount(req);
                redirect(req, resp, "/order-success?id=" + o.getOrderId());
                return;
            }
        } catch (ServiceException e) {
            flash(req, "danger", e.getMessage());
        }
        redirect(req, resp, "/checkout");
    }

    /** Mock payment gateway input checks. Card/UPI details are validated and then discarded - never stored. */
    private void validatePaymentDetails(HttpServletRequest req, String method) throws ServiceException {
        if ("UPI".equals(method)) {
            String upi = req.getParameter("upiId");
            if (upi == null || !upi.matches("[A-Za-z0-9._-]{2,}@[A-Za-z]{2,}")) throw new ServiceException("Enter a valid UPI ID (for example name@bank).");
        } else if ("CARD".equals(method)) {
            String num = req.getParameter("cardNumber") == null ? "" : req.getParameter("cardNumber").replaceAll("\\s", "");
            String cvv = req.getParameter("cvv");
            if (!num.matches("\\d{16}")) throw new ServiceException("Card number must be 16 digits.");
            if (cvv == null || !cvv.matches("\\d{3}")) throw new ServiceException("CVV must be 3 digits.");
        }
    }
}
