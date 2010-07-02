package ru.sgu.csit.selectioncommittee.gui.dialogs;

import ru.sgu.csit.selectioncommittee.gui.actions.CloseAction;
import ru.sgu.csit.selectioncommittee.gui.utils.GBConstraints;
import ru.sgu.csit.selectioncommittee.gui.utils.HibernateSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Vector;

import static ru.sgu.csit.selectioncommittee.gui.utils.MessageUtil.showWarningMessage;

/**
 * Date: Jun 28, 2010
 * Time: 5:21:39 AM
 *
 * @author : xx & hd
 */
public class DatabaseOptionDialog extends JDialog {
    private Action saveAction = new SaveAction();
    private Action closeAction = new CloseAction(this);

    private HibernateSettings hibernateSettings = HibernateSettings.getSettings();

    private JComboBox dbTypeComboBox;
    private JTextField dbHostTextField;
    private JSpinner dbPortSpinner;
    private JTextField dbNameTextField;

    public DatabaseOptionDialog(JDialog owner) {
        super(owner, "Настройка доступа к СУБД", true);
        setLayout(new GridBagLayout());

        Vector<String> dbTypes = new Vector<String>();
        dbTypes.add("Выберите...");
        dbTypes.addAll(hibernateSettings.getSupportedDatabaseList());
        String selectedDatabaseType = hibernateSettings.getDatabaseType();
        int selectedIndex = 0;
        for (int i = 0; i < dbTypes.size(); ++i) {
            if (dbTypes.get(i).equalsIgnoreCase(selectedDatabaseType)) {
                selectedIndex = i;
            }
        }
        dbTypeComboBox = new JComboBox(dbTypes);
        dbTypeComboBox.setSelectedIndex(selectedIndex);

        dbHostTextField = new JTextField(hibernateSettings.getHost());
        dbPortSpinner = new JSpinner();
        dbPortSpinner.setValue(hibernateSettings.getPort());
        dbNameTextField = new JTextField(hibernateSettings.getDatabaseName(), 20);

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
                String selectedDatabaseType = dbTypeComboBox.getSelectedItem().toString();
                String host = dbHostTextField.getText();
                Integer port = (Integer) dbPortSpinner.getValue();
                String databaseName = dbNameTextField.getText();

                hibernateSettings.setConnectionUrl(selectedDatabaseType, host, port, databaseName);
                hibernateSettings.saveSettings();
                closeAction.actionPerformed(null);
            }
        }
    }
}