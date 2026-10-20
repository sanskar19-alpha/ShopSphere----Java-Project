package com.shopsphere.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicInteger;

/** Session event handling: counts active sessions and issues the CSRF token for each new session. */
@WebListener
public class SessionListener implements HttpSessionListener {
    private static final AtomicInteger ACTIVE = new AtomicInteger();
    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        byte[] b = new byte[24];
        RANDOM.nextBytes(b);
        se.getSession().setAttribute("csrfToken", Base64.getUrlEncoder().withoutPadding().encodeToString(b));
        se.getSession().setAttribute("cartCount", 0);
        ServletContext ctx = se.getSession().getServletContext();
        ctx.setAttribute("activeSessions", ACTIVE.incrementAndGet());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        se.getSession().getServletContext().setAttribute("activeSessions", Math.max(0, ACTIVE.decrementAndGet()));
    }
}
