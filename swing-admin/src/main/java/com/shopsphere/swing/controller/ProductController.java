package com.shopsphere.swing.controller;

import com.shopsphere.model.Category;
import com.shopsphere.model.Product;
import com.shopsphere.service.ProductService;
import com.shopsphere.service.ServiceException;
import com.shopsphere.swing.model.GenericTableModel;
import com.shopsphere.swing.view.ProductFrame;
import com.shopsphere.swing.view.UiUtil;

import java.util.List;
import java.util.stream.Collectors;

public class ProductController {
    private final ProductFrame view;
    private final ProductService service = new ProductService();
    private final GenericTableModel<Product> model = new GenericTableModel<>(
            new String[]{"ID", "Name", "Brand", "Category", "Price", "Disc %", "Stock", "Status"},
            p -> new Object[]{p.getProductId(), p.getName(), p.getBrand(), p.getCategoryName(), p.getPrice(), p.getDiscount(), p.getStock(), p.isStatus() ? "ACTIVE" : "HIDDEN"});

    public ProductController(ProductFrame view) {
        this.view = view;
        view.getTable().setModel(model);
        view.setColumnWidths(40, 230, 100, 120, 70, 60, 60, 70);
        try {
            for (Category c : service.categories(false)) view.getCategoryBox().addItem(c);
        } catch (ServiceException e) { UiUtil.error(view, e.getMessage()); }

        view.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int row = view.getTable().getSelectedRow();
            if (row >= 0) {
                Product p = model.getAt(view.getTable().convertRowIndexToModel(row));
                if (p != null) view.showProduct(p);
            }
        });
        view.getNewBtn().addActionListener(e -> { view.getTable().clearSelection(); Product p = new Product(); p.setStatus(true); view.showProduct(p); });
        view.getSaveBtn().addActionListener(e -> save());
        view.getRemoveBtn().addActionListener(e -> remove());
        view.getRefreshBtn().addActionListener(e -> reload());
        view.getAllRadio().addActionListener(e -> reload());
        view.getActiveRadio().addActionListener(e -> reload());
        view.getHiddenRadio().addActionListener(e -> reload());
        reload();
    }

    private void reload() {
        try {
            List<Product> all = service.adminList();
            if (view.getActiveRadio().isSelected()) all = all.stream().filter(Product::isStatus).collect(Collectors.toList());
            else if (view.getHiddenRadio().isSelected()) all = all.stream().filter(p -> !p.isStatus()).collect(Collectors.toList());
            model.setRows(all);
        } catch (ServiceException e) { UiUtil.error(view, e.getMessage()); }
    }

    private void save() {
        try {
            Product p = new Product();
            p.setProductId(view.getIdField().getText().isBlank() ? 0 : Integer.parseInt(view.getIdField().getText()));
            p.setName(view.getNameField().getText().trim());
            p.setBrand(view.getBrandField().getText().trim());
            Category c = (Category) view.getCategoryBox().getSelectedItem();
            p.setCategoryId(c == null ? 0 : c.getCategoryId());
            p.setDescription(view.getDescriptionArea().getText().trim());
            p.setPrice(Double.parseDouble(view.getPriceField().getText().trim()));
            p.setDiscount(view.getDiscountField().getText().isBlank() ? 0 : Double.parseDouble(view.getDiscountField().getText().trim()));
            p.setStock(Integer.parseInt(view.getStockField().getText().trim()));
            p.setStatus(view.getActiveBox().isSelected());
            service.save(p);                      // validation lives in the service layer
            UiUtil.info(view, "Product saved.");
            reload();
        } catch (NumberFormatException ex) {
            UiUtil.error(view, "Price, discount and stock must be valid numbers.");
        } catch (ServiceException ex) {
            UiUtil.error(view, ex.getMessage());
        }
    }

    private void remove() {
        String id = view.getIdField().getText();
        if (id.isBlank()) { UiUtil.error(view, "Select a product first."); return; }
        if (!UiUtil.confirm(view, "Remove this product from the store? Order history is kept.")) return;
        try {
            service.deactivate(Integer.parseInt(id));
            reload();
        } catch (ServiceException ex) { UiUtil.error(view, ex.getMessage()); }
    }
}
