package ru.sgu.csit.admissiondepartment.gui.actions.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sgu.csit.admissiondepartment.gui.MainFrame;
import ru.sgu.csit.admissiondepartment.gui.dialogs.LoginDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.iABOUT;

/**
 * Date: Jun 27, 2010
 * Time: 10:39:23 PM
 *
 * @author : xx & hd
 */
@Component
public class OpenLoginDialogAction extends AbstractAction {

    @Autowired
    private MainFrame mainFrame;

    @Autowired
    private LoginDialog loginDialog;

    public OpenLoginDialogAction() {
        super("Сменить пользователя", iABOUT);
        putValue(Action.SHORT_DESCRIPTION, "Войти под другим пользователем");
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F11"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mainFrame.setVisible(false);
        loginDialog.setVisible(true);
    }
}
