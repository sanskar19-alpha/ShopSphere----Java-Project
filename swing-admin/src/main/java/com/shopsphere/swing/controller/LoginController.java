package com.shopsphere.swing.controller;

import com.shopsphere.model.User;
import com.shopsphere.service.AuthService;
import com.shopsphere.service.ServiceException;
import com.shopsphere.swing.view.AdminDashboard;
import com.shopsphere.swing.view.LoginFrame;

/** MVC Controller: connects LoginFrame (view) to AuthService (model/service). */
public class LoginController {
    private final LoginFrame view;
    private final AuthService auth = new AuthService();

    public LoginController(LoginFrame view) {
        this.view = view;
        view.getLoginButton().addActionListener(e -> login());
        view.getExitButton().addActionListener(e -> System.exit(0));
    }

    private void login() {
        try {
            User u = auth.login(view.getEmail(), view.getPassword());
            if (!u.isAdmin()) throw new ServiceException("This account is not an administrator.");
            view.dispose();
            AdminDashboard dashboard = new AdminDashboard(u);
            new DashboardController(dashboard);
            dashboard.setVisible(true);
        } catch (ServiceException ex) {
            view.showError(ex.getMessage());
            view.clearPassword();
        }
    }
}
