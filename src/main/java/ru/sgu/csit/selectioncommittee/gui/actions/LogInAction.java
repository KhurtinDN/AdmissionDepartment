package ru.sgu.csit.selectioncommittee.gui.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import ru.sgu.csit.selectioncommittee.gui.MainFrame;
import ru.sgu.csit.selectioncommittee.gui.dialogs.LogInDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.iABOUT;

/**
 * Date: Jun 27, 2010
 * Time: 10:39:23 PM
 *
 * @author : xx & hd
 */
public class LogInAction extends AbstractAction {
    private MainFrame owner;
    
    private LogInDialog logInDialog;

    public LogInAction() {
        super("Сменить пользователя", iABOUT);
        putValue(Action.SHORT_DESCRIPTION, "Войти под другим пользователем");
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F11"));
    }

    public void setOwner(MainFrame owner) {
        this.owner = owner;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        owner.setVisible(false);
        if (logInDialog == null) {
            ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ApplicationContext.xml");
            logInDialog = applicationContext.getBean(LogInDialog.class);

        }
        logInDialog.setVisible(true);
    }
}
