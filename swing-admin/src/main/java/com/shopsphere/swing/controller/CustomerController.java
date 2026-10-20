package com.shopsphere.swing.controller;

import com.shopsphere.model.User;
import com.shopsphere.service.AdminService;
import com.shopsphere.service.ServiceException;
import com.shopsphere.swing.model.GenericTableModel;
import com.shopsphere.swing.view.CustomerFrame;
import com.shopsphere.swing.view.UiUtil;

public class CustomerController {
    private final CustomerFrame view;
    private final AdminService service = new AdminService();
    private final GenericTableModel<User> model = new GenericTableModel<>(
            new String[]{"ID", "Name", "Email", "Mobile", "Joined", "Status"},
            u -> new Object[]{u.getUserId(), u.getName(), u.getEmail(), u.getMobile(), u.getCreatedAt(), u.isStatus() ? "ACTIVE" : "BLOCKED"});

    public CustomerController(CustomerFrame view) {
        this.view = view;
        view.getTable().setModel(model);
        view.setColumnWidths(40, 140, 220, 100, 150, 70);
        view.getBlockBtn().addActionListener(e -> setActive(false));
        view.getUnblockBtn().addActionListener(e -> setActive(true));
        view.getRefreshBtn().addActionListener(e -> reload());
        reload();
    }

    private void reload() {
        try { model.setRows(service.customers()); }
        catch (ServiceException e) { UiUtil.error(view, e.getMessage()); }
    }

    private void setActive(boolean active) {
        int row = view.getTable().getSelectedRow();
        if (row < 0) { UiUtil.error(view, "Select a customer first."); return; }
        User u = model.getAt(view.getTable().convertRowIndexToModel(row));
        try { service.setCustomerActive(u.getUserId(), active); reload(); }
        catch (ServiceException e) { UiUtil.error(view, e.getMessage()); }
    }
}
