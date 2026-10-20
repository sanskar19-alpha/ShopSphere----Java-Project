package com.shopsphere.swing.view;

import javax.swing.*;
import java.awt.*;

/** MVC View: administrator login window (JFrame, JLabel, JTextField, JPasswordField, JCheckBox, JButton). */
public class LoginFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private final JTextField emailField = new JTextField("admin@shopsphere.com", 22);
    private final JPasswordField passwordField = new JPasswordField(22);
    private final JCheckBox showPassword = new JCheckBox("Show password");
    private final JButton loginButton = new JButton("Login");
    private final JButton exitButton = new JButton("Exit");
    private final JLabel statusLabel = new JLabel(" ");

    public LoginFrame() {
        super("ShopSphere Admin - Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        JLabel title = new JLabel("ShopSphere Administration", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        root.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        UiUtil.addRow(form, 0, "Email:", emailField);
        UiUtil.addRow(form, 1, "Password:", passwordField);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1; c.gridy = 2; c.anchor = GridBagConstraints.WEST;
        form.add(showPassword, c);
        root.add(form, BorderLayout.CENTER);

        statusLabel.setForeground(Color.RED);
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(exitButton);
        buttons.add(loginButton);
        JPanel south = new JPanel(new BorderLayout());
        south.add(statusLabel, BorderLayout.NORTH);
        south.add(buttons, BorderLayout.SOUTH);
        root.add(south, BorderLayout.SOUTH);

        setContentPane(root);
        getRootPane().setDefaultButton(loginButton);
        showPassword.addActionListener(e -> passwordField.setEchoChar(showPassword.isSelected() ? (char) 0 : '\u2022'));
        pack();
        setResizable(false);
        UiUtil.centre(this);
    }

    public String getEmail() { return emailField.getText().trim(); }
    public String getPassword() { return new String(passwordField.getPassword()); }
    public JButton getLoginButton() { return loginButton; }
    public JButton getExitButton() { return exitButton; }
    public void showError(String msg) { statusLabel.setText(msg); }
    public void clearPassword() { passwordField.setText(""); }
}
