package ru.sgu.csit.admissiondepartment.gui.actions.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sgu.csit.admissiondepartment.gui.dialogs.DatabaseOptionDialog;
import ru.sgu.csit.admissiondepartment.gui.dialogs.LoginDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

@Component
public class OpenDBSettingsDialogAction extends AbstractAction {

    @Autowired
    private LoginDialog loginDialog;

    public OpenDBSettingsDialogAction() {
        putValue(Action.NAME, "Настроить доступ к СУБД");
    }

    public void actionPerformed(ActionEvent e) {
        new DatabaseOptionDialog(loginDialog).setVisible(true);
    }
}
