package com.shopsphere.controller;

import com.shopsphere.util.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/** Internationalization: /lang?to=en|hi&back=/path switches the UI language and returns to the page. */
@WebServlet("/lang")
public class LanguageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String to = req.getParameter("to");
        if ("en".equals(to) || "hi".equals(to)) {
            req.getSession().setAttribute("lang", to);
            CookieUtil.set(req, resp, "lang", to, 60 * 60 * 24 * 365);
        }
        String back = req.getParameter("back");
        if (back == null || !back.startsWith("/") || back.startsWith("//") || back.contains("://") || back.contains("\\")) back = "/home";
        resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + back));
    }
}
