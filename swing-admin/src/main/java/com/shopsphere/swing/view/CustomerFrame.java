package com.shopsphere.swing.view;

import com.shopsphere.swing.controller.CustomerController;

import javax.swing.*;
import java.awt.*;

public class CustomerFrame extends BaseTableFrame {
    private static final long serialVersionUID = 1L;
    private final JButton blockBtn = new JButton("Block");
    private final JButton unblockBtn = new JButton("Unblock");
    private final JButton refreshBtn = new JButton("Refresh");

    public CustomerFrame() {
        super("Customers", 820, 420);
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bar.add(blockBtn); bar.add(unblockBtn); bar.add(refreshBtn);
        root.add(bar, BorderLayout.SOUTH);
    }

    @Override public void attach() { new CustomerController(this); }

    public JButton getBlockBtn() { return blockBtn; }
    public JButton getUnblockBtn() { return unblockBtn; }
    public JButton getRefreshBtn() { return refreshBtn; }
}
