package com.shopsphere.filter;

import jakarta.servlet.*;
import java.io.IOException;

/** Forces UTF-8 for request parameters (so Hindi text and the rupee sign survive). */
public class EncodingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        chain.doFilter(req, resp);
    }
}
