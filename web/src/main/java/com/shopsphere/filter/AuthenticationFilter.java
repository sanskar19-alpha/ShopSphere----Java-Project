package com.shopsphere.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Protects customer pages (cart, checkout, orders, wishlist, profile ...).
 * Request -> logged in? -> yes: continue / no: redirect to the login page.
 */
public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        if (session != null && session.getAttribute("user") != null) {
            resp.setHeader("Cache-Control", "no-store");          // don't show private pages after logout via Back button
            chain.doFilter(request, response);
            return;
        }
        String target = req.getRequestURI().substring(req.getContextPath().length());
        if ("GET".equalsIgnoreCase(req.getMethod()) && req.getQueryString() != null) target += "?" + req.getQueryString();
        String next = "GET".equalsIgnoreCase(req.getMethod()) ? "?next=" + URLEncoder.encode(target, StandardCharsets.UTF_8) : "";
        resp.sendRedirect(req.getContextPath() + "/login" + next);
    }
}
