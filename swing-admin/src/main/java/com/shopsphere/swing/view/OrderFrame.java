package com.shopsphere.swing.view;

import com.shopsphere.service.OrderService;
import com.shopsphere.swing.controller.OrderController;

import javax.swing.*;
import java.awt.*;

public class OrderFrame extends BaseTableFrame {
    private static final long serialVersionUID = 1L;
    private final JComboBox<String> filterBox = new JComboBox<>();
    private final JComboBox<String> statusBox = new JComboBox<>(OrderService.STATUSES.toArray(new String[0]));
    private final JButton updateBtn = new JButton("Update status");
    private final JTextArea detailsArea = new JTextArea(5, 40);
    private final transient Runnable onChange;

    public OrderFrame(Runnable onChange) {
        super("Orders", 900, 560);
        this.onChange = onChange;
        filterBox.addItem("ALL");
        for (String s : OrderService.STATUSES) filterBox.addItem(s);
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Filter:")); top.add(filterBox);
        root.add(top, BorderLayout.NORTH);

        detailsArea.setEditable(false);
        JPanel bottom = new JPanel(new BorderLayout(6, 6));
        bottom.add(new JScrollPane(detailsArea), BorderLayout.CENTER);
        JPanel update = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        update.add(new JLabel("New status:")); update.add(statusBox); update.add(updateBtn);
        bottom.add(update, BorderLayout.SOUTH);
        root.add(bottom, BorderLayout.SOUTH);
    }

    @Override public void attach() { new OrderController(this); }

    public JComboBox<String> getFilterBox() { return filterBox; }
    public JComboBox<String> getStatusBox() { return statusBox; }
    public JButton getUpdateBtn() { return updateBtn; }
    public JTextArea getDetailsArea() { return detailsArea; }
    public void notifyChanged() { if (onChange != null) onChange.run(); }
}
