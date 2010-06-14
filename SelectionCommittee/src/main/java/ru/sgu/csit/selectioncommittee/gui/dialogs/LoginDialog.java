package ru.sgu.csit.selectioncommittee.gui.dialogs;

import ru.sgu.csit.selectioncommittee.factory.LocalSessionFactory;
import ru.sgu.csit.selectioncommittee.gui.MainFrame;
import ru.sgu.csit.selectioncommittee.gui.utils.GBConstraints;

import static ru.sgu.csit.selectioncommittee.gui.utils.MessageUtil.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Vector;

/**
 * Date: Jun 14, 2010
 * Time: 3:04:05 PM
 *
 * @author xx & hd
 */
public class LoginDialog extends JDialog {
    private Action openDBOptionAction = new OpenDBOptionAction();
    private Action loginAction = new LoginAction();
    private Action closeAction = new CloseAction();

    private JFrame owner;

    private JTextField loginField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);

    public LoginDialog(JFrame owner) {
        super(owner, "Вход в систему", true);
        this.owner = owner;
        setLayout(new GridBagLayout());

        add(new JLabel("Логин:"), new GBConstraints(0, 0));
        add(loginField, new GBConstraints(1, 0, true));
        add(new JLabel("Пароль:"), new GBConstraints(0, 1));
        add(passwordField, new GBConstraints(1, 1, true));
        add(createButtonPanel(), new GBConstraints(0, 2, 2, 1));

        pack();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        Dimension dialogSize = getSize();
        int x = (int) (screenSize.getWidth() / 2 - dialogSize.getWidth() / 2);
        int y = (int) (screenSize.getHeight() / 2 - dialogSize.getHeight() / 2);
        setLocation(x, y);

        JPanel content = (JPanel) getContentPane();
        content.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "CLOSE_DIALOG");
        content.getActionMap().put("CLOSE_DIALOG", closeAction);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.add(new JButton(openDBOptionAction), new GBConstraints(0, 0));
        buttonPanel.add(new JLabel(), new GBConstraints(1, 0, true));
        buttonPanel.add(new JButton(loginAction), new GBConstraints(2, 0));
        buttonPanel.add(new JButton(closeAction), new GBConstraints(3, 0));
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
            new DatabaseOptionDialog(LoginDialog.this).setVisible(true);
        }
    }

    private class LoginAction extends AbstractAction {
        private LoginAction() {
            putValue(Action.NAME, "Логин");
        }

        public void actionPerformed(ActionEvent e) {
            if (validateForm()) {
                Properties dbProperties = new Properties();
                try {
                    FileInputStream fileInputStream = new FileInputStream("settings/hibernate.properties");
                    dbProperties.load(fileInputStream);
                } catch (IOException ioe) {
                    showWarningMessage("Необходимо настроить доступ к СУБД");
                    return;
                }

                String login = loginField.getText();
                String password = new String(passwordField.getPassword());

                dbProperties.put("hibernate.connection.username", login);
                dbProperties.put("hibernate.connection.password", password);

                if (!LocalSessionFactory.createNewSessionFactory(dbProperties)) {
                    showWarningMessage("<html>Авторизация провалилась. Возможные причины:<br><ul>" +
                            "<li>Неверный логин или пароль</li>" +
                            "<li>Неправльно настроен доступ к СУБД</li>" +
                            "</ul></html>");
                    return;
                }

                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        MainFrame mainFrame = new MainFrame();
                        mainFrame.setDefaultCloseOperation(MainFrame.DO_NOTHING_ON_CLOSE);
                        mainFrame.setVisible(true);
                    }
                });
                LoginDialog.this.setVisible(false);
            }
        }
    }

    private class CloseAction extends AbstractAction {
        private CloseAction() {
            putValue(Action.NAME, "Закрыть");
        }

        public void actionPerformed(ActionEvent e) {
            if (owner == null) {
                System.exit(0);
            } else {
                LoginDialog.this.setVisible(false);
            }
        }
    }

    private class DatabaseOptionDialog extends JDialog {
        private Action saveAction = new SaveAction();
        private Action closeAction = new CloseAction();

        private Vector<String> dbNames = new Vector<String>();

        {
            dbNames.add("Выбрать...");
            dbNames.add("MySql");
            dbNames.add("PostgreSQL");
        }

        private JComboBox dbTypeComboBox = new JComboBox(dbNames);
        private JTextField dbHostTextField = new JTextField("localhost");
        private JSpinner dbPortSpinner = new JSpinner();
        private JTextField dbNameTextField = new JTextField("SelectionCommittee");

        private DatabaseOptionDialog(JDialog owner) {
            super(owner, "Настройка доступа к СУБД", true);
            setLayout(new GridBagLayout());

            add(new JLabel("База данных:"), new GBConstraints(0, 0));
            add(dbTypeComboBox, new GBConstraints(1, 0, true));
            add(new JLabel("Сервер:"), new GBConstraints(0, 1));
            add(dbHostTextField, new GBConstraints(1, 1, true));
            add(new JLabel("Порт:"), new GBConstraints(0, 2));
            add(dbPortSpinner, new GBConstraints(1, 2, true));
            add(new JLabel("Имя базы данных:"), new GBConstraints(0, 3));
            add(dbNameTextField, new GBConstraints(1, 3, true));

            add(createButtonPanel(), new GBConstraints(0, 4, 2, 1, true));

            pack();

            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension screenSize = toolkit.getScreenSize();
            Dimension dialogSize = getSize();
            int x = (int) (screenSize.getWidth() / 2 - dialogSize.getWidth() / 2);
            int y = (int) (screenSize.getHeight() / 2 - dialogSize.getHeight() / 2);
            setLocation(x, y);

            JPanel content = (JPanel) getContentPane();
            content.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "CLOSE_DIALOG");
            content.getActionMap().put("CLOSE_DIALOG", closeAction);
        }

        private JPanel createButtonPanel() {
            JPanel buttonPanel = new JPanel(new GridBagLayout());
            buttonPanel.add(new JLabel(), new GBConstraints(0, 0, true));
            buttonPanel.add(new JButton(saveAction), new GBConstraints(1, 0));
            buttonPanel.add(new JButton(closeAction), new GBConstraints(2, 0));
            return buttonPanel;
        }

        private boolean validateForm() {
            if (dbTypeComboBox.getSelectedIndex() < 1) {
                showWarningMessage("Необходимо выбрать базу данных");
                return false;
            }
            if (!dbHostTextField.getText().matches("[\\w\\.]+")) {
                showWarningMessage("Необходимо ввести корректный хост сервера");
                return false;
            }
            Integer port = (Integer) dbPortSpinner.getValue();
            if (0 > port || port > 65536) {
                showWarningMessage("Введите корректный номер порта");
                return false;
            }
            if (!dbNameTextField.getText().matches("\\w+")) {
                showWarningMessage("Введите корректное имя базы данных");
                return false;
            }
            return true;
        }

        private class SaveAction extends AbstractAction {
            private SaveAction() {
                putValue(Action.NAME, "Сохранить");
            }

            public void actionPerformed(ActionEvent e) {
                if (validateForm()) {
                    Properties properties = new Properties();

                    String driver_class = null;
                    String dialect = null;

                    String selectedDatabaseType = dbTypeComboBox.getSelectedItem().toString().toLowerCase();
                    if (selectedDatabaseType.equals("mysql")) {
                        driver_class = "com.mysql.jdbc.Driver";
                        dialect = "org.hibernate.dialect.MySQL5InnoDBDialect";
                    } else if (selectedDatabaseType.equals("postgresql")) {
                        driver_class = "org.postgresql.Driver";
                        dialect = "org.hibernate.dialect.PostgreSQLDialect";
                    }

                    String host = dbHostTextField.getText();
                    String port = dbPortSpinner.getValue().toString();
                    String name = dbNameTextField.getText();

                    String url = createDatabaseUrl(selectedDatabaseType, host, port, name);

                    properties.put("hibernate.connection.driver_class", driver_class);
                    properties.put("hibernate.connection.dialect", dialect);
                    properties.put("hibernate.connection.url", url);

                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream("settings/hibernate.properties");
                        properties.store(fileOutputStream, "Hibernate properties");
                        fileOutputStream.close();
                    } catch (IOException e1) {
                        showErrorMessage("Во время сохранения настроек случилась ошибка:\n" + e);
                    }

                    closeAction.actionPerformed(null);
                }
            }

            private String createDatabaseUrl(String selectedDatabaseType, String host, String port, String name) {
                StringBuilder urlStringBuilder = new StringBuilder("jdbc:");
                urlStringBuilder.append(selectedDatabaseType).append("://");
                urlStringBuilder.append(host).append(":");
                urlStringBuilder.append(port).append("/");
                urlStringBuilder.append(name);
                return urlStringBuilder.toString();
            }
        }

        private class CloseAction extends AbstractAction {
            private CloseAction() {
                putValue(Action.NAME, "Закрыть");
            }

            public void actionPerformed(ActionEvent e) {
                DatabaseOptionDialog.this.setVisible(false);
            }
        }
    }
}
