package ru.sgu.csit.admissiondepartment.gui.actions.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.sgu.csit.admissiondepartment.gui.MainFrame;
import ru.sgu.csit.admissiondepartment.gui.dialogs.LoginDialog;
import ru.sgu.csit.admissiondepartment.system.DatabaseSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ru.sgu.csit.admissiondepartment.gui.utils.MessageUtil.showWarningMessage;

@Component
public class LoginAction extends AbstractAction {

    @Autowired
    private MainFrame mainFrame;

    @Autowired
    private LoginDialog loginDialog;

    @Autowired
    private DatabaseSettings databaseSettings;

    @Autowired
    private AuthenticationManager authenticationManager;

    public LoginAction() {
        putValue(Action.NAME, "Войти");
    }

    public void actionPerformed(ActionEvent e) {
        if (loginDialog.validateForm()) {
            if (!databaseSettings.isConfigured()) {
                showWarningMessage("Необходимо настроить доступ к СУБД");
                return;
            }

            String username = loginDialog.getLogin();
            char[] password = loginDialog.getPassword();

            loginDialog.reset();

            if (!databaseSettings.tryLogin(username, password)) {
                showWarningMessage("<html>Авторизация провалилась. Возможные причины:<br><ul>" +
                        "<li>Неверный логин или пароль</li>" +
                        "<li>Неправильно настроен доступ к СУБД</li>" +
                        "</ul></html>");
                return;
            }

            Authentication authentication = null;
            try {
                Authentication requestAuthentication = new UsernamePasswordAuthenticationToken(username, password);
                authentication = authenticationManager.authenticate(requestAuthentication);
            } catch (AuthenticationException ae) {
                System.err.println("Authentication failed: " + ae.getMessage());
            } finally {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            mainFrame.setVisible(true);
            mainFrame.reloadAllData();

            loginDialog.setVisible(false);
        }
    }
}
