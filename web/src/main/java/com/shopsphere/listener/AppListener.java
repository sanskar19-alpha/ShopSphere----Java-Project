package com.shopsphere.listener;

import com.shopsphere.util.DBConnection;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.sql.Connection;
import java.util.Date;
import java.util.logging.Logger;

/** Application-level event handling: runs when the web application starts and stops. */
@WebListener
public class AppListener implements ServletContextListener {
    private static final Logger LOG = Logger.getLogger(AppListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext ctx = event.getServletContext();
        ctx.setAttribute("appName", "ShopSphere");
        ctx.setAttribute("startedAt", new Date());
        ctx.setAttribute("activeSessions", 0);
        LOG.info("ShopSphere starting. Context path: " + ctx.getContextPath());
        try (Connection c = DBConnection.getConnection()) {
            LOG.info("Database connection OK: " + c.getMetaData().getURL());
            ctx.setAttribute("dbOnline", Boolean.TRUE);
        } catch (Exception e) {
            ctx.setAttribute("dbOnline", Boolean.FALSE);
            LOG.warning("Database is NOT reachable: " + e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        LOG.info("ShopSphere shutting down.");
    }
}
