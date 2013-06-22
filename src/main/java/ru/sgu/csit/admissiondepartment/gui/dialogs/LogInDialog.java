package ru.sgu.csit.admissiondepartment.gui.dialogs;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.sgu.csit.admissiondepartment.gui.MainFrame;
import ru.sgu.csit.admissiondepartment.gui.actions.ExitAction;
import ru.sgu.csit.admissiondepartment.gui.utils.GBConstraints;
import ru.sgu.csit.admissiondepartment.gui.utils.HibernateSettings;

import static ru.sgu.csit.admissiondepartment.gui.utils.MessageUtil.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Date: Jun 14, 2010
 * Time: 3:04:05 PM
 *
 * @author xx & hd
 */
public class LogInDialog extends JDialog {
    private AuthenticationManager authenticationManager;

    private HibernateSettings hibernateSettings = HibernateSettings.getSettings();

    private Action openDBOptionAction = new OpenDBOptionAction();
    private Action loginAction = new LoginAction();
    private Action exitAction = new ExitAction();

    private MainFrame owner;

    private JTextField loginField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);

    public LogInDialog() {
        super((JFrame)null, "Вход в систему", true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        add(new JLabel("Логин:"), new GBConstraints(0, 0));
        add(loginField, new GBConstraints(1, 0, true));
        add(new JLabel("Пароль:"), new GBConstraints(0, 1));
        add(passwordField, new GBConstraints(1, 1, true));
        add(createButtonPanel(), new GBConstraints(0, 2, 2, 1));

        pack();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        Dimension dialogSize = getSize();
        int x = (int) (screenSize.getWidth() / 2 - dialogSize.getWidth() / 2);
        int y = (int) (screenSize.getHeight() / 2 - dialogSize.getHeight() / 2);
        setLocation(x, y);

        JPanel content = (JPanel) getContentPane();
        content.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "CLOSE_DIALOG");
        content.getActionMap().put("CLOSE_DIALOG", exitAction);
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void setOwner(MainFrame owner) {
        this.owner = owner;
    }

    public MainFrame getOwner() {
        return owner;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.add(new JButton(openDBOptionAction), new GBConstraints(0, 0));
        buttonPanel.add(new JLabel(), new GBConstraints(1, 0, true));

        JButton loginButton = new JButton(loginAction);
        getRootPane().setDefaultButton(loginButton);
        
        buttonPanel.add(loginButton, new GBConstraints(2, 0));
        buttonPanel.add(new JButton(exitAction), new GBConstraints(3, 0));
        return buttonPanel;
    }

    private boolean validateForm() {
        if (!loginField.getText().matches("\\w+")) {
            showWarningMessage("Введите логин");
            return false;
        }
        return true;
    }

    private class OpenDBOptionAction extends AbstractAction {
        private OpenDBOptionAction() {
            putValue(Action.NAME, "Настроить доступ к СУБД");
        }

        public void actionPerformed(ActionEvent e) {
            new DatabaseOptionDialog(LogInDialog.this).setVisible(true);
        }
    }

    private class LoginAction extends AbstractAction {
        private LoginAction() {
            putValue(Action.NAME, "Войти");
        }

        public void actionPerformed(ActionEvent e) {
            if (validateForm()) {
                if (!hibernateSettings.isConfigured()) {
                    showWarningMessage("Необходимо настроить доступ к СУБД");
                    return;
                }

                String username = loginField.getText();
                char[] password = passwordField.getPassword();

                passwordField.setText("");
                loginField.selectAll();
                loginField.requestFocus();

                hibernateSettings.setUserNameAndPassword(username, password);

                if (!hibernateSettings.tryLogin()) {
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

                owner.setVisible(true);
                owner.reloadAllData();

                LogInDialog.this.setVisible(false);
            }
        }
    }
}
