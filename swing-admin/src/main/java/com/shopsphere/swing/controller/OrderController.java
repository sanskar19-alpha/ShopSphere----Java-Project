package com.shopsphere.swing.controller;

import com.shopsphere.model.Order;
import com.shopsphere.model.OrderItem;
import com.shopsphere.service.OrderService;
import com.shopsphere.service.ServiceException;
import com.shopsphere.swing.model.GenericTableModel;
import com.shopsphere.swing.view.OrderFrame;
import com.shopsphere.swing.view.UiUtil;

public class OrderController {
    private final OrderFrame view;
    private final OrderService service = new OrderService();
    private final GenericTableModel<Order> model = new GenericTableModel<>(
            new String[]{"Order", "Customer", "Date", "Total", "Payment", "Pay status", "Status"},
            o -> new Object[]{o.getOrderId(), o.getUserName(), o.getCreatedAt(), o.getTotalAmount(), o.getPaymentMethod(), o.getPaymentStatus(), o.getOrderStatus()});

    public OrderController(OrderFrame view) {
        this.view = view;
        view.getTable().setModel(model);
        view.setColumnWidths(60, 140, 160, 90, 80, 90, 90);
        view.getFilterBox().addActionListener(e -> reload());
        view.getUpdateBtn().addActionListener(e -> update());
        view.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) showDetails();
        });
        reload();
    }

    private Order selected() {
        int row = view.getTable().getSelectedRow();
        return row < 0 ? null : model.getAt(view.getTable().convertRowIndexToModel(row));
    }

    private void reload() {
        try {
            String f = (String) view.getFilterBox().getSelectedItem();
            model.setRows(service.adminList("ALL".equals(f) ? null : f));
            view.getDetailsArea().setText("");
        } catch (ServiceException e) { UiUtil.error(view, e.getMessage()); }
    }

    private void showDetails() {
        Order o = selected();
        if (o == null) return;
        try {
            Order full = service.details(o.getOrderId(), -1);
            StringBuilder sb = new StringBuilder("Order #" + full.getOrderId() + " for " + full.getUserName() + "\n");
            for (OrderItem i : full.getItems())
                sb.append("  ").append(i.getProductName()).append("  x").append(i.getQuantity()).append("  @ ").append(i.getPrice()).append('\n');
            sb.append("Discount: ").append(full.getDiscount()).append("   Total: ").append(full.getTotalAmount());
            view.getDetailsArea().setText(sb.toString());
            view.getStatusBox().setSelectedItem(full.getOrderStatus());
        } catch (ServiceException e) { UiUtil.error(view, e.getMessage()); }
    }

    private void update() {
        Order o = selected();
        if (o == null) { UiUtil.error(view, "Select an order first."); return; }
        try {
            service.changeStatus(o.getOrderId(), (String) view.getStatusBox().getSelectedItem());
            reload();
            view.notifyChanged();
        } catch (ServiceException e) { UiUtil.error(view, e.getMessage()); }
    }
}
