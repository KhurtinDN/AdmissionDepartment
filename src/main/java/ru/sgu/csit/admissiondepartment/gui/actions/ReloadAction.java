package ru.sgu.csit.admissiondepartment.gui.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import ru.sgu.csit.admissiondepartment.gui.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.iREFRESH16;

/**
 * Date: Jun 27, 2010
 * Time: 11:06:18 PM
 *
 * @author : xx & hd
 */
@Component
public class ReloadAction extends AbstractAction {

    @Autowired
    private MainFrame mainFrame;

    public ReloadAction() {
        super("Обновить", iREFRESH16);
        putValue(Action.SHORT_DESCRIPTION, "Обновить");
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F5"));
    }

    @Secured("ROLE_VIEWER")
    @Override
    public void actionPerformed(ActionEvent e) {
        mainFrame.reloadAllData();
    }
}
