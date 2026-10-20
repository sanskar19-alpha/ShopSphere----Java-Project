package com.shopsphere.controller;

import com.shopsphere.model.User;
import com.shopsphere.service.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/** Admin login page (lives outside /admin/* so it is reachable before login). */
@WebServlet("/admin-login")
public class AdminLoginServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = currentUser(req);
        if (u != null && u.isAdmin()) { redirect(req, resp, "/admin/dashboard"); return; }
        req.setAttribute("adminMode", Boolean.TRUE);
        render(req, resp, "login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User u = LoginServlet.authenticate(req);
            if (!u.isAdmin()) throw new ServiceException("This account does not have administrator access.");
            LoginServlet.establishSession(req, u);
            redirect(req, resp, "/admin/dashboard");
        } catch (ServiceException e) {
            req.setAttribute("adminMode", Boolean.TRUE);
            req.setAttribute("error", e.getMessage());
            req.setAttribute("email", req.getParameter("email"));
            render(req, resp, "login.jsp");
        }
    }
}
