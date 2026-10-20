package com.shopsphere.controller;

import com.shopsphere.model.User;
import com.shopsphere.service.CartService;
import com.shopsphere.service.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/** Shared helpers for all controllers (the "C" in web MVC). */
public abstract class BaseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected User currentUser(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        return s == null ? null : (User) s.getAttribute("user");
    }

    /** Forwards to a JSP; the "loaded" flag lets the JSP know it was reached through its controller. */
    protected void render(HttpServletRequest req, HttpServletResponse resp, String jsp) throws ServletException, IOException {
        req.setAttribute("loaded", Boolean.TRUE);
        req.getRequestDispatcher("/" + jsp).forward(req, resp);
    }

    protected void redirect(HttpServletRequest req, HttpServletResponse resp, String path) throws IOException {
        resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + path));
    }

    /** One-shot message shown by the header fragment after the next page load. */
    protected void flash(HttpServletRequest req, String type, String message) {
        HttpSession s = req.getSession();
        s.setAttribute("flashType", type);     // success | danger | warning | info
        s.setAttribute("flashMsg", message);
    }

    protected void fail(HttpServletRequest req, HttpServletResponse resp, ServiceException e, String backPath) throws IOException {
        flash(req, "danger", e.getMessage());
        redirect(req, resp, backPath);
    }

    protected void refreshCartCount(HttpServletRequest req) {
        User u = currentUser(req);
        if (u == null) return;
        try {
            req.getSession().setAttribute("cartCount", new CartService().count(u.getUserId()));
        } catch (ServiceException e) {
            req.getSession().setAttribute("cartCount", 0);
        }
    }

    /** Only allow same-site relative redirect targets (prevents open-redirect attacks). */
    protected String safeNext(String next, String fallback) {
        if (next != null && next.startsWith("/") && !next.startsWith("//") && !next.contains("\\") && !next.contains("://")) return next;
        return fallback;
    }

    protected int intParam(HttpServletRequest req, String name, int def) {
        return com.shopsphere.util.Validator.toInt(req.getParameter(name), def);
    }
}
