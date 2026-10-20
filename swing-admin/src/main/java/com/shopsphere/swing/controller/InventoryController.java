package com.shopsphere.swing.controller;

import com.shopsphere.model.Product;
import com.shopsphere.service.ProductService;
import com.shopsphere.service.ServiceException;
import com.shopsphere.swing.model.GenericTableModel;
import com.shopsphere.swing.view.InventoryFrame;
import com.shopsphere.swing.view.UiUtil;

import javax.swing.*;
import java.awt.*;

public class InventoryController {
    private final InventoryFrame view;
    private final ProductService service = new ProductService();
    private final GenericTableModel<Product> model = new GenericTableModel<>(
            new String[]{"ID", "Product", "Category", "Stock"},
            p -> new Object[]{p.getProductId(), p.getName(), p.getCategoryName(), p.getStock()});

    public InventoryController(InventoryFrame view) {
        this.view = view;
        view.getTable().setModel(model);
        view.setColumnWidths(40, 280, 140, 70);
        // colour rows by stock level using a custom renderer
        view.getTable().setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;
            @Override public Component getTableCellRendererComponent(JTable t, Object v, boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                Product p = model.getAt(t.convertRowIndexToModel(row));
                if (!sel && p != null) c.setBackground(p.getStock() == 0 ? new Color(255, 205, 205) : p.getStock() <= 10 ? new Color(255, 240, 190) : Color.WHITE);
                return c;
            }
        });
        view.getLowOnly().addActionListener(e -> reload());
        view.getSaveBtn().addActionListener(e -> save());
        view.getTable().getSelectionModel().addListSelectionListener(e -> {
            int row = view.getTable().getSelectedRow();
            if (!e.getValueIsAdjusting() && row >= 0)
                view.getStockField().setText(String.valueOf(model.getAt(view.getTable().convertRowIndexToModel(row)).getStock()));
        });
        reload();
    }

    private void reload() {
        try { model.setRows(view.getLowOnly().isSelected() ? service.lowStock() : service.adminList()); }
        catch (ServiceException e) { UiUtil.error(view, e.getMessage()); }
    }

    private void save() {
        int row = view.getTable().getSelectedRow();
        if (row < 0) { UiUtil.error(view, "Select a product first."); return; }
        try {
            service.updateStock(model.getAt(view.getTable().convertRowIndexToModel(row)).getProductId(), Integer.parseInt(view.getStockField().getText().trim()));
            reload();
        } catch (NumberFormatException e) { UiUtil.error(view, "Stock must be a whole number.");
        } catch (ServiceException e) { UiUtil.error(view, e.getMessage()); }
    }
}
