package com.shopsphere.controller;

import com.shopsphere.model.User;
import com.shopsphere.service.OrderService;
import com.shopsphere.service.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/** /orders (history), /order?id= (details + tracking), /order-success?id= (confirmation). */
@WebServlet({"/orders", "/order", "/order-success"})
public class OrderServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private final OrderService orders = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = currentUser(req);
        String path = req.getServletPath();
        try {
            if ("/orders".equals(path)) {
                req.setAttribute("orders", orders.forUser(u.getUserId()));
                render(req, resp, "orders.jsp");
            } else {
                req.setAttribute("order", orders.details(intParam(req, "id", -1), u.getUserId()));
                render(req, resp, "/order-success".equals(path) ? "order-success.jsp" : "order-details.jsp");
            }
        } catch (ServiceException e) {
            fail(req, resp, e, "/orders");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = currentUser(req);
        int id = intParam(req, "id", -1);
        try {
            if ("cancel".equals(req.getParameter("action"))) {
                orders.cancelByCustomer(id, u.getUserId());
                flash(req, "info", "Order #" + id + " has been cancelled.");
            }
        } catch (ServiceException e) {
            flash(req, "danger", e.getMessage());
        }
        redirect(req, resp, "/order?id=" + id);
    }
}
