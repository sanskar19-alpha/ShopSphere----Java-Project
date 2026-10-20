package com.shopsphere.controller;

import com.shopsphere.model.Address;
import com.shopsphere.model.User;
import com.shopsphere.service.AdminService;
import com.shopsphere.service.AuthService;
import com.shopsphere.service.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/** Profile page: personal details, password change and saved addresses. */
@WebServlet("/profile")
public class ProfileServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private final AuthService auth = new AuthService();
    private final AdminService account = new AdminService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("addresses", account.addresses(currentUser(req).getUserId()));
        } catch (ServiceException e) {
            req.setAttribute("error", e.getMessage());
        }
        render(req, resp, "profile.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = currentUser(req);
        String action = req.getParameter("action");
        try {
            if ("update".equals(action)) {
                User fresh = auth.updateProfile(u.getUserId(), req.getParameter("name"), req.getParameter("mobile"));
                req.getSession().setAttribute("user", fresh);
                flash(req, "success", "Profile updated.");
            } else if ("password".equals(action)) {
                auth.changePassword(u.getUserId(), req.getParameter("current"), req.getParameter("next"), req.getParameter("confirm"));
                flash(req, "success", "Password changed.");
            } else if ("addAddress".equals(action)) {
                Address a = new Address();
                a.setUserId(u.getUserId());
                a.setAddressLine(req.getParameter("addressLine"));
                a.setCity(req.getParameter("city"));
                a.setState(req.getParameter("state"));
                a.setPincode(req.getParameter("pincode"));
                a.setAddressType(req.getParameter("addressType"));
                account.addAddress(a);
                flash(req, "success", "Address added.");
            } else if ("deleteAddress".equals(action)) {
                account.deleteAddress(intParam(req, "addressId", -1), u.getUserId());
                flash(req, "info", "Address deleted.");
            }
        } catch (ServiceException e) {
            flash(req, "danger", e.getMessage());
        }
        redirect(req, resp, "/profile");
    }
}
