package com.shopsphere.swing.view;

import com.shopsphere.swing.controller.CategoryController;

import javax.swing.*;
import java.awt.*;

public class CategoryFrame extends BaseTableFrame {
    private static final long serialVersionUID = 1L;
    private final JTextField idField = new JTextField(5);
    private final JTextField nameField = new JTextField(16);
    private final JTextField descriptionField = new JTextField(20);
    private final JCheckBox activeBox = new JCheckBox("Active", true);
    private final JButton newBtn = new JButton("New");
    private final JButton saveBtn = new JButton("Save");
    private final JButton deleteBtn = new JButton("Delete");

    public CategoryFrame() {
        super("Categories", 760, 420);
        idField.setEditable(false);
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Category"));
        UiUtil.addRow(form, 0, "ID:", idField);
        UiUtil.addRow(form, 1, "Name:", nameField);
        UiUtil.addRow(form, 2, "Description:", descriptionField);
        UiUtil.addRow(form, 3, "", activeBox);
        UiUtil.finishForm(form, 4);
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(newBtn); buttons.add(saveBtn); buttons.add(deleteBtn);
        JPanel east = new JPanel(new BorderLayout());
        east.add(form, BorderLayout.CENTER);
        east.add(buttons, BorderLayout.SOUTH);
        root.add(east, BorderLayout.EAST);
    }

    @Override public void attach() { new CategoryController(this); }

    public JTextField getIdField() { return idField; }
    public JTextField getNameField() { return nameField; }
    public JTextField getDescriptionField() { return descriptionField; }
    public JCheckBox getActiveBox() { return activeBox; }
    public JButton getNewBtn() { return newBtn; }
    public JButton getSaveBtn() { return saveBtn; }
    public JButton getDeleteBtn() { return deleteBtn; }
}
