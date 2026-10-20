package com.shopsphere.controller;

import com.shopsphere.service.AdminService;
import com.shopsphere.service.OrderService;
import com.shopsphere.service.ProductService;
import com.shopsphere.service.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderService orders = new OrderService();
        AdminService admin = new AdminService();
        try {
            req.setAttribute("totalOrders", orders.totalOrders());
            req.setAttribute("revenue", orders.revenue());
            req.setAttribute("statusCounts", orders.statusCounts());
            req.setAttribute("topProducts", orders.topProducts());
            req.setAttribute("customerCount", admin.customerCount());
            req.setAttribute("productCount", admin.productCount());
            req.setAttribute("lowStock", new ProductService().lowStock());
        } catch (ServiceException e) {
            req.setAttribute("error", e.getMessage());
        }
        render(req, resp, "admin/dashboard.jsp");
    }
}
