package com.shopsphere.controller;

import com.shopsphere.model.Coupon;
import com.shopsphere.service.AdminService;
import com.shopsphere.service.ServiceException;
import com.shopsphere.util.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;

@WebServlet("/admin/coupons")
public class AdminCouponServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private final AdminService admin = new AdminService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("coupons", admin.coupons());
        } catch (ServiceException e) {
            req.setAttribute("error", e.getMessage());
        }
        render(req, resp, "admin/coupons.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String action = req.getParameter("action");
            if ("toggle".equals(action)) {
                admin.setCouponActive(intParam(req, "couponId", -1), "1".equals(req.getParameter("active")));
                flash(req, "success", "Coupon updated.");
            } else {
                Coupon c = new Coupon();
                c.setCode(req.getParameter("code"));
                c.setDiscountType(req.getParameter("discountType"));
                Double v = Validator.toDouble(req.getParameter("discountValue"));
                Double min = Validator.toDouble(req.getParameter("minimumOrder"));
                Double max = Validator.toDouble(req.getParameter("maximumDiscount"));
                c.setDiscountValue(v == null ? 0 : v);
                c.setMinimumOrder(min == null ? 0 : min);
                c.setMaximumDiscount(max == null ? 0 : max);
                String exp = req.getParameter("expiryDate");
                try {
                    c.setExpiryDate(exp == null || exp.isBlank() ? null : Date.valueOf(exp));
                } catch (IllegalArgumentException e) {
                    throw new ServiceException("Expiry date must look like 2030-12-31.");
                }
                c.setStatus(true);
                admin.addCoupon(c);
                flash(req, "success", "Coupon created.");
            }
        } catch (ServiceException e) {
            flash(req, "danger", e.getMessage());
        }
        redirect(req, resp, "/admin/coupons");
    }
}
