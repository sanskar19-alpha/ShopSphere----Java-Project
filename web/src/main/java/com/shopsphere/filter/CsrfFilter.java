package com.shopsphere.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

/**
 * Cross-site request forgery protection: every POST must carry the per-session token
 * (hidden field "csrf") that the JSP forms print from ${sessionScope.csrfToken}.
 */
public class CsrfFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if ("POST".equalsIgnoreCase(req.getMethod())) {
            HttpSession session = req.getSession(false);
            String expected = session == null ? null : (String) session.getAttribute("csrfToken");
            String given = req.getParameter("csrf");
            if (expected == null || given == null
                    || !MessageDigest.isEqual(expected.getBytes(StandardCharsets.UTF_8), given.getBytes(StandardCharsets.UTF_8))) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid or missing security token. Go back, refresh the page and try again.");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
