package com.shopsphere.controller;

import com.shopsphere.model.User;
import com.shopsphere.service.CartService;
import com.shopsphere.service.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private final CartService cart = new CartService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = currentUser(req);
        try {
            req.setAttribute("cart", cart.getCart(u.getUserId()));
            render(req, resp, "cart.jsp");
        } catch (ServiceException e) {
            req.setAttribute("error", e.getMessage());
            render(req, resp, "cart.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = currentUser(req);
        String action = req.getParameter("action");
        int productId = intParam(req, "productId", -1);
        String back = safeNext(req.getParameter("back"), "/cart");
        try {
            switch (action == null ? "" : action) {
                case "add" -> {
                    cart.add(u.getUserId(), productId, intParam(req, "quantity", 1));
                    flash(req, "success", "Added to your cart.");
                }
                case "update" -> {
                    cart.update(u.getUserId(), productId, intParam(req, "quantity", 1));
                    flash(req, "success", "Cart updated.");
                    back = "/cart";
                }
                case "remove" -> {
                    cart.remove(u.getUserId(), productId);
                    flash(req, "info", "Item removed.");
                    back = "/cart";
                }
                default -> flash(req, "danger", "Unknown cart action.");
            }
        } catch (ServiceException e) {
            flash(req, "danger", e.getMessage());
        }
        refreshCartCount(req);
        redirect(req, resp, back);
    }
}
