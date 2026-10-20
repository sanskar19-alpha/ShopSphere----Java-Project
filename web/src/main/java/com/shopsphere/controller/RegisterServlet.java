package com.shopsphere.controller;

import com.shopsphere.model.User;
import com.shopsphere.service.AuthService;
import com.shopsphere.service.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private final AuthService auth = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (currentUser(req) != null) { redirect(req, resp, "/home"); return; }
        render(req, resp, "register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name"), email = req.getParameter("email"), mobile = req.getParameter("mobile");
        try {
            User u = auth.register(name, email, req.getParameter("password"), req.getParameter("confirm"), mobile);
            flash(req, "success", "Account created. Please log in, " + u.getName() + ".");
            redirect(req, resp, "/login");
        } catch (ServiceException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("name", name);          // keep what the user typed (never the password)
            req.setAttribute("email", email);
            req.setAttribute("mobile", mobile);
            render(req, resp, "register.jsp");
        }
    }
}
