package com.shopsphere.swing.controller;

import com.shopsphere.service.AdminService;
import com.shopsphere.service.OrderService;
import com.shopsphere.service.ServiceException;
import com.shopsphere.swing.view.*;

import java.text.NumberFormat;
import java.util.Locale;

public class DashboardController {
    private final AdminDashboard view;

    public DashboardController(AdminDashboard view) {
        this.view = view;
        view.getProductsBtn().addActionListener(e -> open(new ProductFrame()));
        view.getCategoriesBtn().addActionListener(e -> open(new CategoryFrame()));
        view.getCustomersBtn().addActionListener(e -> open(new CustomerFrame()));
        view.getOrdersBtn().addActionListener(e -> open(new OrderFrame(this::refreshStats)));
        view.getInventoryBtn().addActionListener(e -> open(new InventoryFrame()));
        view.getExitItem().addActionListener(e -> System.exit(0));
        view.getLogoutItem().addActionListener(e -> {
            view.dispose();
            LoginFrame login = new LoginFrame();
            new LoginController(login);
            login.setVisible(true);
        });
        refreshStats();
    }

    private void open(BaseTableFrame frame) {
        frame.attach();
        frame.setVisible(true);
    }

    void refreshStats() {
        try {
            OrderService orders = new OrderService();
            AdminService admin = new AdminService();
            String revenue = NumberFormat.getCurrencyInstance(Locale.of("en", "IN")).format(orders.revenue());
            view.setStats(orders.totalOrders(), revenue, admin.customerCount(), admin.productCount());
        } catch (ServiceException e) {
            UiUtil.error(view, e.getMessage());
        }
    }
}
