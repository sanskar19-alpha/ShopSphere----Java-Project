package com.shopsphere.swing;

import com.shopsphere.swing.controller.LoginController;
import com.shopsphere.swing.view.LoginFrame;

import javax.swing.*;

/** Entry point of the Swing administration application. */
public class SwingAdminApp {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());     // pluggable look and feel
        } catch (Exception ignored) {
            // keep the default Metal look and feel
        }
        SwingUtilities.invokeLater(() -> {                                            // build UI on the Event Dispatch Thread
            LoginFrame login = new LoginFrame();
            new LoginController(login);
            login.setVisible(true);
        });
    }
}
