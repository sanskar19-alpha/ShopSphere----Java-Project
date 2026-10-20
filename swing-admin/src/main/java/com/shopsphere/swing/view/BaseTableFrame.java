package com.shopsphere.swing.view;

import javax.swing.*;
import java.awt.*;

/** Common layout of the management windows: a JTable (JScrollPane) in the centre and a form/buttons area around it. */
public abstract class BaseTableFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    protected final JTable table = new JTable();
    protected final JPanel root = new JPanel(new BorderLayout(8, 8));

    protected BaseTableFrame(String title, int width, int height) {
        super(title);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);          // closing a sub-window must not exit the application
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);
        root.add(new JScrollPane(table), BorderLayout.CENTER);
        setContentPane(root);
        setSize(width, height);
        UiUtil.centre(this);
    }

    /** Creates the controller for this view (called once by the dashboard controller). */
    public abstract void attach();

    public JTable getTable() { return table; }

    /** Sets preferred column widths (call after the table model has been set). */
    public void setColumnWidths(int... widths) {
        for (int i = 0; i < widths.length && i < table.getColumnCount(); i++) table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
    }
}
