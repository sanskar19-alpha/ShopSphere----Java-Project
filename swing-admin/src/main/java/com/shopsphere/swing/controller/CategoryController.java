package com.shopsphere.swing.controller;

import com.shopsphere.model.Category;
import com.shopsphere.service.ProductService;
import com.shopsphere.service.ServiceException;
import com.shopsphere.swing.model.GenericTableModel;
import com.shopsphere.swing.view.CategoryFrame;
import com.shopsphere.swing.view.UiUtil;

public class CategoryController {
    private final CategoryFrame view;
    private final ProductService service = new ProductService();
    private final GenericTableModel<Category> model = new GenericTableModel<>(
            new String[]{"ID", "Name", "Description", "Status"},
            c -> new Object[]{c.getCategoryId(), c.getName(), c.getDescription(), c.isStatus() ? "ACTIVE" : "HIDDEN"});

    public CategoryController(CategoryFrame view) {
        this.view = view;
        view.getTable().setModel(model);
        view.setColumnWidths(40, 140, 220, 70);
        view.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting() || view.getTable().getSelectedRow() < 0) return;
            Category c = model.getAt(view.getTable().convertRowIndexToModel(view.getTable().getSelectedRow()));
            if (c != null) show(c);
        });
        view.getNewBtn().addActionListener(e -> { view.getTable().clearSelection(); show(new Category()); view.getActiveBox().setSelected(true); });
        view.getSaveBtn().addActionListener(e -> save());
        view.getDeleteBtn().addActionListener(e -> delete());
        reload();
    }

    private void show(Category c) {
        view.getIdField().setText(c.getCategoryId() == 0 ? "" : String.valueOf(c.getCategoryId()));
        view.getNameField().setText(c.getName() == null ? "" : c.getName());
        view.getDescriptionField().setText(c.getDescription() == null ? "" : c.getDescription());
        view.getActiveBox().setSelected(c.isStatus());
    }

    private void reload() {
        try { model.setRows(service.categories(false)); }
        catch (ServiceException e) { UiUtil.error(view, e.getMessage()); }
    }

    private void save() {
        Category c = new Category();
        c.setCategoryId(view.getIdField().getText().isBlank() ? 0 : Integer.parseInt(view.getIdField().getText()));
        c.setName(view.getNameField().getText().trim());
        c.setDescription(view.getDescriptionField().getText().trim());
        c.setStatus(view.getActiveBox().isSelected());
        try { service.saveCategory(c); reload(); show(new Category()); }
        catch (ServiceException e) { UiUtil.error(view, e.getMessage()); }
    }

    private void delete() {
        if (view.getIdField().getText().isBlank()) { UiUtil.error(view, "Select a category first."); return; }
        if (!UiUtil.confirm(view, "Delete this category?")) return;
        try { service.deleteCategory(Integer.parseInt(view.getIdField().getText())); reload(); show(new Category()); }
        catch (ServiceException e) { UiUtil.error(view, e.getMessage()); }
    }
}
