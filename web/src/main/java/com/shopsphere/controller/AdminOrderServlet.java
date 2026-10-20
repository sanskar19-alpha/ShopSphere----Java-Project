package com.shopsphere.controller;

import com.shopsphere.service.OrderService;
import com.shopsphere.service.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/orders")
public class AdminOrderServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private final OrderService orders = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String status = req.getParameter("status");
        try {
            int id = intParam(req, "id", 0);
            if (id > 0) req.setAttribute("order", orders.details(id, -1));
            req.setAttribute("orders", orders.adminList(status));
        } catch (ServiceException e) {
            req.setAttribute("error", e.getMessage());
        }
        req.setAttribute("statuses", OrderService.STATUSES);
        req.setAttribute("filter", status == null ? "" : status);
        render(req, resp, "admin/orders.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = intParam(req, "orderId", -1);
        try {
            orders.changeStatus(id, req.getParameter("status"));
            flash(req, "success", "Order #" + id + " updated.");
        } catch (ServiceException e) {
            flash(req, "danger", e.getMessage());
        }
        redirect(req, resp, "/admin/orders");
    }
}
