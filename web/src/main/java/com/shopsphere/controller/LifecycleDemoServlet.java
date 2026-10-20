package com.shopsphere.controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * Syllabus demo: Servlet lifecycle (init / service / doGet / doPost / destroy),
 * ServletConfig (per-servlet init-params from web.xml) and ServletContext (application-wide
 * context-params and attributes). Registered in web.xml at /demo/lifecycle.
 */
public class LifecycleDemoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(LifecycleDemoServlet.class.getName());

    private final AtomicInteger requests = new AtomicInteger();
    private String greeting;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);                                     // 1. container calls init() once
        greeting = config.getInitParameter("greeting");         // ServletConfig = this servlet only
        LOG.info("LifecycleDemoServlet.init() greeting=" + greeting);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("service() called for " + req.getMethod());    // 2. service() dispatches to doGet()/doPost()
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext ctx = getServletContext();               // ServletContext = whole web application
        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Servlet lifecycle demo</title>"
                    + "<link rel='stylesheet' href='" + req.getContextPath() + "/css/style.css'></head><body class='p-4'>");
            out.println("<div class='container'><h2>Servlet lifecycle, ServletConfig and ServletContext</h2>");
            List<String> rows = new ArrayList<>();
            rows.add("Requests handled by this servlet instance: <b>" + requests.incrementAndGet() + "</b> (one instance, many requests)");
            rows.add("ServletConfig init-param <code>greeting</code>: <b>" + escape(greeting) + "</b>");
            rows.add("ServletConfig servlet name: <b>" + getServletConfig().getServletName() + "</b>");
            rows.add("ServletContext attribute <code>appName</code>: <b>" + ctx.getAttribute("appName") + "</b>");
            rows.add("ServletContext init-param <code>supportEmail</code>: <b>" + escape(ctx.getInitParameter("supportEmail")) + "</b>");
            rows.add("Server: <b>" + escape(ctx.getServerInfo()) + "</b>, Servlet API " + ctx.getMajorVersion() + "." + ctx.getMinorVersion());
            rows.add("Active sessions (HttpSessionListener): <b>" + ctx.getAttribute("activeSessions") + "</b>");
            rows.add("Started at (ServletContextListener): <b>" + ctx.getAttribute("startedAt") + "</b>");
            rows.add("HTTP method: <b>" + req.getMethod() + "</b>, protocol <b>" + req.getProtocol() + "</b>, URI <b>" + escape(req.getRequestURI()) + "</b>");
            rows.add("Request header User-Agent: <b>" + escape(req.getHeader("User-Agent")) + "</b>");
            out.println("<ul>");
            for (String r : rows) out.println("<li>" + r + "</li>");
            out.println("</ul>");
            out.println("<p>Lifecycle: loading &rarr; instantiation &rarr; <code>init()</code> &rarr; "
                    + "<code>service()</code> &rarr; <code>doGet()/doPost()</code> &rarr; <code>destroy()</code></p>");
            out.println("<p><a href='" + req.getContextPath() + "/demo/index.jsp'>Back to demos</a></p></div></body></html>");
        }
    }

    @Override
    public void destroy() {                                     // 3. container calls destroy() once on undeploy/shutdown
        LOG.info("LifecycleDemoServlet.destroy() after " + requests.get() + " requests");
    }

    private static String escape(String s) {
        if (s == null) return "(not set)";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
}
