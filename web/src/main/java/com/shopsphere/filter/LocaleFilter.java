package com.shopsphere.filter;

import com.shopsphere.util.CookieUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Internationalization support: picks the UI language from ?lang=en|hi, the "lang" cookie
 * or the default, stores it in the session and remembers it in a cookie. JSPs read ${sessionScope.lang}.
 */
public class LocaleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String path = req.getRequestURI().substring(req.getContextPath().length());
        if (path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/images/")) {
            chain.doFilter(request, response);       // static files: no session needed
            return;
        }
        HttpSession session = req.getSession();

        String requested = req.getParameter("lang");
        if (isSupported(requested)) {
            session.setAttribute("lang", requested);
            CookieUtil.set(req, resp, "lang", requested, 60 * 60 * 24 * 365);
        } else if (session.getAttribute("lang") == null) {
            String fromCookie = CookieUtil.get(req, "lang");
            session.setAttribute("lang", isSupported(fromCookie) ? fromCookie : "en");
        }
        chain.doFilter(request, response);
    }

    private boolean isSupported(String l) { return "en".equals(l) || "hi".equals(l); }
}
