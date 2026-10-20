package com.shopsphere.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Syllabus demo: JNDI. Looks up (1) an env-entry declared in web.xml and (2) a DataSource
 * declared in META-INF/context.xml, using the java:comp/env naming context of the web container.
 */
@WebServlet("/demo/jndi")
public class JndiDemoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("jndiLines", new java.util.ArrayList<String>());
        @SuppressWarnings("unchecked")
        java.util.List<String> lines = (java.util.List<String>) req.getAttribute("jndiLines");
        try {
            Context env = (Context) new InitialContext().lookup("java:comp/env");
            lines.add("OK  java:comp/env/storeName (env-entry in web.xml) = " + env.lookup("storeName"));
        } catch (NamingException e) {
            lines.add("FAIL env-entry lookup: " + e.getMessage());
        }
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/ShopSphereDB");
            try (Connection c = ds.getConnection();
                 PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM products");
                 ResultSet rs = ps.executeQuery()) {
                rs.next();
                lines.add("OK  java:comp/env/jdbc/ShopSphereDB (DataSource from context.xml) -> products in database: " + rs.getInt(1));
            }
        } catch (Exception e) {
            lines.add("FAIL DataSource lookup: " + e + "  (copy the MySQL driver jar into Tomcat's lib folder - see README)");
        }
        req.getRequestDispatcher("/demo/jndi-result.jsp").forward(req, resp);
    }
}
