package com.shopsphere.controller;

import com.shopsphere.service.ProductService;
import com.shopsphere.service.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/** Inventory view: all products with stock, low-stock highlighted, quick stock update. */
@WebServlet("/admin/inventory")
public class AdminInventoryServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private final ProductService service = new ProductService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            boolean onlyLow = "low".equals(req.getParameter("view"));
            req.setAttribute("products", onlyLow ? service.lowStock() : service.adminList());
            req.setAttribute("onlyLow", onlyLow);
        } catch (ServiceException e) {
            req.setAttribute("error", e.getMessage());
        }
        render(req, resp, "admin/inventory.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            service.updateStock(intParam(req, "productId", -1), intParam(req, "stock", -1));
            flash(req, "success", "Stock updated.");
        } catch (ServiceException e) {
            flash(req, "danger", e.getMessage());
        }
        redirect(req, resp, "/admin/inventory" + ("low".equals(req.getParameter("view")) ? "?view=low" : ""));
    }
}
