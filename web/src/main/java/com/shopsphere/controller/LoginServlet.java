package com.shopsphere.controller;

import com.shopsphere.model.User;
import com.shopsphere.service.AuthService;
import com.shopsphere.service.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/** Customer login. Also used by AdminLoginServlet through {@link #establishSession}. */
@WebServlet("/login")
public class LoginServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private static final int MAX_FAILURES = 5;
    private final AuthService auth = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = currentUser(req);
        if (u != null) { redirect(req, resp, u.isAdmin() ? "/admin/dashboard" : "/home"); return; }
        req.setAttribute("next", req.getParameter("next"));
        render(req, resp, "login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String next = req.getParameter("next");
        try {
            User u = authenticate(req);
            establishSession(req, u);
            redirect(req, resp, safeNext(next, u.isAdmin() ? "/admin/dashboard" : "/home"));
        } catch (ServiceException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("email", req.getParameter("email"));
            req.setAttribute("next", next);
            render(req, resp, "login.jsp");
        }
    }

    /** Checks credentials with a simple per-session brute-force limit. */
    static User authenticate(HttpServletRequest req) throws ServiceException {
        HttpSession session = req.getSession();
        Integer failures = (Integer) session.getAttribute("loginFailures");
        if (failures != null && failures >= MAX_FAILURES)
            throw new ServiceException("Too many failed attempts. Close the browser and try again later.");
        try {
            User u = new AuthService().login(req.getParameter("email"), req.getParameter("password"));
            session.removeAttribute("loginFailures");
            return u;
        } catch (ServiceException e) {
            session.setAttribute("loginFailures", failures == null ? 1 : failures + 1);
            throw e;
        }
    }

    /** Stores the login in the session. A new session id is issued to prevent session fixation. */
    static void establishSession(HttpServletRequest req, User u) {
        req.changeSessionId();
        HttpSession session = req.getSession();
        session.setAttribute("user", u);
        session.setAttribute("role", u.getRole());
        try {
            session.setAttribute("cartCount", new com.shopsphere.service.CartService().count(u.getUserId()));
        } catch (ServiceException e) {
            session.setAttribute("cartCount", 0);
        }
    }
}
