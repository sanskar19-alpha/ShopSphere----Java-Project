package com.shopsphere.swing.view;

import com.shopsphere.model.Category;
import com.shopsphere.model.Product;
import com.shopsphere.swing.controller.ProductController;

import javax.swing.*;
import java.awt.*;

/** Product management: JTable + form with JTextField, JTextArea, JComboBox, JCheckBox, JRadioButton and JButton. */
public class ProductFrame extends BaseTableFrame {
    private static final long serialVersionUID = 1L;
    private final JTextField idField = new JTextField(6);
    private final JTextField nameField = new JTextField(18);
    private final JTextField brandField = new JTextField(12);
    private final JComboBox<Category> categoryBox = new JComboBox<>();
    private final JTextArea descriptionArea = new JTextArea(3, 18);
    private final JTextField priceField = new JTextField(8);
    private final JTextField discountField = new JTextField(8);
    private final JTextField stockField = new JTextField(8);
    private final JCheckBox activeBox = new JCheckBox("Visible in store", true);
    private final JRadioButton allRadio = new JRadioButton("All", true);
    private final JRadioButton activeRadio = new JRadioButton("Active");
    private final JRadioButton hiddenRadio = new JRadioButton("Hidden");
    private final JButton newBtn = new JButton("New");
    private final JButton saveBtn = new JButton("Save");
    private final JButton removeBtn = new JButton("Remove from store");
    private final JButton refreshBtn = new JButton("Refresh");

    public ProductFrame() {
        super("Products", 1000, 560);
        idField.setEditable(false);
        categoryBox.setRenderer(new DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;
            @Override public Component getListCellRendererComponent(JList<?> l, Object v, int i, boolean s, boolean f) {
                return super.getListCellRendererComponent(l, v instanceof Category c ? c.getName() : "", i, s, f);
            }
        });
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        ButtonGroup filter = new ButtonGroup();
        JPanel filterBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterBar.add(new JLabel("Show:"));
        for (JRadioButton r : new JRadioButton[]{allRadio, activeRadio, hiddenRadio}) { filter.add(r); filterBar.add(r); }
        filterBar.add(refreshBtn);
        root.add(filterBar, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Product details"));
        UiUtil.addRow(form, 0, "ID:", idField);
        UiUtil.addRow(form, 1, "Name:", nameField);
        UiUtil.addRow(form, 2, "Brand:", brandField);
        UiUtil.addRow(form, 3, "Category:", categoryBox);
        UiUtil.addRow(form, 4, "Description:", new JScrollPane(descriptionArea));
        UiUtil.addRow(form, 5, "Price:", priceField);
        UiUtil.addRow(form, 6, "Discount %:", discountField);
        UiUtil.addRow(form, 7, "Stock:", stockField);
        UiUtil.addRow(form, 8, "", activeBox);
        UiUtil.finishForm(form, 9);
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(newBtn); buttons.add(saveBtn); buttons.add(removeBtn);
        JPanel east = new JPanel(new BorderLayout());
        east.add(form, BorderLayout.CENTER);
        east.add(buttons, BorderLayout.SOUTH);
        root.add(east, BorderLayout.EAST);
    }

    @Override public void attach() { new ProductController(this); }

    public void showProduct(Product p) {
        idField.setText(p.getProductId() == 0 ? "" : String.valueOf(p.getProductId()));
        nameField.setText(p.getName() == null ? "" : p.getName());
        brandField.setText(p.getBrand() == null ? "" : p.getBrand());
        descriptionArea.setText(p.getDescription() == null ? "" : p.getDescription());
        priceField.setText(p.getPrice() == 0 ? "" : String.valueOf(p.getPrice()));
        discountField.setText(String.valueOf(p.getDiscount()));
        stockField.setText(String.valueOf(p.getStock()));
        activeBox.setSelected(p.isStatus());
        for (int i = 0; i < categoryBox.getItemCount(); i++)
            if (categoryBox.getItemAt(i).getCategoryId() == p.getCategoryId()) categoryBox.setSelectedIndex(i);
    }

    public JTextField getIdField() { return idField; }
    public JTextField getNameField() { return nameField; }
    public JTextField getBrandField() { return brandField; }
    public JComboBox<Category> getCategoryBox() { return categoryBox; }
    public JTextArea getDescriptionArea() { return descriptionArea; }
    public JTextField getPriceField() { return priceField; }
    public JTextField getDiscountField() { return discountField; }
    public JTextField getStockField() { return stockField; }
    public JCheckBox getActiveBox() { return activeBox; }
    public JRadioButton getAllRadio() { return allRadio; }
    public JRadioButton getActiveRadio() { return activeRadio; }
    public JRadioButton getHiddenRadio() { return hiddenRadio; }
    public JButton getNewBtn() { return newBtn; }
    public JButton getSaveBtn() { return saveBtn; }
    public JButton getRemoveBtn() { return removeBtn; }
    public JButton getRefreshBtn() { return refreshBtn; }
}
