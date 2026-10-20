package com.shopsphere.swing.view;

import com.shopsphere.model.User;

import javax.swing.*;
import java.awt.*;

/** MVC View: main window with navigation buttons, summary labels and a Look&Feel menu. */
public class AdminDashboard extends JFrame {
    private static final long serialVersionUID = 1L;
    private final JButton productsBtn = UiUtil.button("Products", "Add, edit and remove products");
    private final JButton categoriesBtn = UiUtil.button("Categories", "Manage categories");
    private final JButton customersBtn = UiUtil.button("Customers", "View and block customers");
    private final JButton ordersBtn = UiUtil.button("Orders", "Update order status");
    private final JButton inventoryBtn = UiUtil.button("Inventory", "Stock levels and low-stock view");
    private final JLabel ordersLabel = new JLabel("-");
    private final JLabel revenueLabel = new JLabel("-");
    private final JLabel customersLabel = new JLabel("-");
    private final JLabel productsLabel = new JLabel("-");
    private final JMenuItem logoutItem = new JMenuItem("Logout");
    private final JMenuItem exitItem = new JMenuItem("Exit");

    public AdminDashboard(User admin) {
        super("ShopSphere Admin Dashboard - " + admin.getName());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JMenuBar bar = new JMenuBar();
        JMenu file = new JMenu("File");
        file.add(logoutItem);
        file.addSeparator();
        file.add(exitItem);
        bar.add(file);
        bar.add(buildLookAndFeelMenu());
        setJMenuBar(bar);

        JPanel stats = new JPanel(new GridLayout(1, 4, 10, 10));
        stats.add(card("Orders", ordersLabel));
        stats.add(card("Revenue", revenueLabel));
        stats.add(card("Customers", customersLabel));
        stats.add(card("Active products", productsLabel));

        JPanel nav = new JPanel(new GridLayout(1, 5, 10, 10));
        for (JButton b : new JButton[]{productsBtn, categoriesBtn, customersBtn, ordersBtn, inventoryBtn}) {
            b.setPreferredSize(new Dimension(120, 60));
            nav.add(b);
        }

        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        root.add(stats, BorderLayout.NORTH);
        root.add(nav, BorderLayout.CENTER);
        setContentPane(root);
        pack();
        UiUtil.centre(this);
    }

    private JPanel card(String title, JLabel value) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder(title));
        value.setFont(value.getFont().deriveFont(Font.BOLD, 20f));
        value.setHorizontalAlignment(SwingConstants.CENTER);
        p.add(value, BorderLayout.CENTER);
        return p;
    }

    /** Pluggable look and feel: JRadioButtonMenuItem per installed L&F. */
    private JMenu buildLookAndFeelMenu() {
        JMenu menu = new JMenu("Look & Feel");
        ButtonGroup group = new ButtonGroup();
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            JRadioButtonMenuItem item = new JRadioButtonMenuItem(info.getName(), info.getClassName().equals(UIManager.getLookAndFeel().getClass().getName()));
            item.addActionListener(e -> {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                    for (Window w : Window.getWindows()) SwingUtilities.updateComponentTreeUI(w);
                } catch (Exception ex) {
                    UiUtil.error(this, "Cannot switch look and feel: " + ex.getMessage());
                }
            });
            group.add(item);
            menu.add(item);
        }
        return menu;
    }

    public JButton getProductsBtn() { return productsBtn; }
    public JButton getCategoriesBtn() { return categoriesBtn; }
    public JButton getCustomersBtn() { return customersBtn; }
    public JButton getOrdersBtn() { return ordersBtn; }
    public JButton getInventoryBtn() { return inventoryBtn; }
    public JMenuItem getLogoutItem() { return logoutItem; }
    public JMenuItem getExitItem() { return exitItem; }

    public void setStats(int orders, String revenue, int customers, int products) {
        ordersLabel.setText(String.valueOf(orders));
        revenueLabel.setText(revenue);
        customersLabel.setText(String.valueOf(customers));
        productsLabel.setText(String.valueOf(products));
    }
}
