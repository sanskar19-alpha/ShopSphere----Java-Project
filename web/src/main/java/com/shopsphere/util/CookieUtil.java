package com.shopsphere.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/** Cookie helpers. Cookies hold only NON-sensitive data (never passwords). */
public final class CookieUtil {
    private CookieUtil() {}

    public static String get(HttpServletRequest req, String name) {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) return null;
        for (Cookie c : cookies) if (c.getName().equals(name)) return c.getValue();
        return null;
    }

    public static void set(HttpServletRequest req, HttpServletResponse resp, String name, String value, int maxAgeSeconds) {
        Cookie c = new Cookie(name, value);
        c.setPath(req.getContextPath().isEmpty() ? "/" : req.getContextPath());
        c.setMaxAge(maxAgeSeconds);
        c.setHttpOnly(true);
        c.setAttribute("SameSite", "Lax");
        resp.addCookie(c);
    }
}
