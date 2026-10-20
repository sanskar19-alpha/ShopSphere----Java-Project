package com.shopsphere.swing.view;

import javax.swing.*;
import java.awt.*;

/** Small helpers shared by all frames. */
public final class UiUtil {
    private UiUtil() {}

    public static void error(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "ShopSphere", JOptionPane.ERROR_MESSAGE);
    }

    public static void info(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "ShopSphere", JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(Component parent, String message) {
        return JOptionPane.showConfirmDialog(parent, message, "Please confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    /** Adds "label : component" as one row of a GridBagLayout form. */
    public static void addRow(JPanel form, int row, String label, JComponent field) {
        GridBagConstraints l = new GridBagConstraints();
        l.gridx = 0; l.gridy = row; l.anchor = GridBagConstraints.NORTHEAST; l.insets = new Insets(4, 4, 4, 8);
        form.add(new JLabel(label), l);
        GridBagConstraints f = new GridBagConstraints();
        f.gridx = 1; f.gridy = row; f.weightx = 1; f.fill = GridBagConstraints.HORIZONTAL; f.insets = new Insets(4, 0, 4, 4);
        form.add(field, f);
    }

    /** Adds an invisible stretching row so the form fields stay at the top. */
    public static void finishForm(JPanel form, int nextRow) {
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0; g.gridy = nextRow; g.gridwidth = 2; g.weighty = 1; g.fill = GridBagConstraints.VERTICAL;
        form.add(new JPanel(), g);
    }

    public static JButton button(String text, String tooltip) {
        JButton b = new JButton(text);
        b.setToolTipText(tooltip);
        return b;
    }

    public static void centre(JFrame f) { f.setLocationRelativeTo(null); }
}
