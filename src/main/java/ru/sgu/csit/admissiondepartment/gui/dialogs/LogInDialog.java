package ru.sgu.csit.admissiondepartment.gui.dialogs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sgu.csit.admissiondepartment.gui.actions.ExitAction;
import ru.sgu.csit.admissiondepartment.gui.actions.login.LoginAction;
import ru.sgu.csit.admissiondepartment.gui.actions.login.OpenDBSettingsDialogAction;
import ru.sgu.csit.admissiondepartment.gui.utils.GBConstraints;

import static ru.sgu.csit.admissiondepartment.gui.utils.MessageUtil.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Date: Jun 14, 2010
 * Time: 3:04:05 PM
 *
 * @author xx & hd
 */
@Component
public class LoginDialog extends JDialog {

    @Autowired
    private OpenDBSettingsDialogAction openDBSettingsDialogAction;

    @Autowired
    private LoginAction loginAction;

    @Autowired
    private ExitAction exitAction;

    private JTextField loginField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);

    public LoginDialog() {
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

    public boolean validateForm() {
        if (!loginField.getText().matches("\\w+")) {
            showWarningMessage("Введите логин");
            return false;
        }
        return true;
    }

    public String getLogin() {
        return loginField.getText();
    }

    public char[] getPassword() {
        return passwordField.getPassword();
    }

    public void reset() {
        loginField.selectAll();
        loginField.requestFocus();
        passwordField.setText("");
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.add(new JButton(openDBSettingsDialogAction), new GBConstraints(0, 0));
        buttonPanel.add(new JLabel(), new GBConstraints(1, 0, true));

        JButton loginButton = new JButton(loginAction);
        getRootPane().setDefaultButton(loginButton);

        buttonPanel.add(loginButton, new GBConstraints(2, 0));
        buttonPanel.add(new JButton(exitAction), new GBConstraints(3, 0));
        return buttonPanel;
    }
}
