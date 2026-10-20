package com.shopsphere.swing.view;

import com.shopsphere.swing.controller.InventoryController;

import javax.swing.*;
import java.awt.*;

public class InventoryFrame extends BaseTableFrame {
    private static final long serialVersionUID = 1L;
    private final JCheckBox lowOnly = new JCheckBox("Low stock only (10 or fewer)");
    private final JTextField stockField = new JTextField(6);
    private final JButton saveBtn = new JButton("Set stock for selected product");

    public InventoryFrame() {
        super("Inventory", 820, 480);
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(lowOnly);
        root.add(top, BorderLayout.NORTH);
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(new JLabel("New stock:")); bottom.add(stockField); bottom.add(saveBtn);
        root.add(bottom, BorderLayout.SOUTH);
    }

    @Override public void attach() { new InventoryController(this); }

    public JCheckBox getLowOnly() { return lowOnly; }
    public JTextField getStockField() { return stockField; }
    public JButton getSaveBtn() { return saveBtn; }
}
