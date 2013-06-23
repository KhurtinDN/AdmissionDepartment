package ru.sgu.csit.admissiondepartment.gui.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sgu.csit.admissiondepartment.gui.utils.MessageUtil;
import ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication;
import ru.sgu.csit.admissiondepartment.system.ApplicationSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.iEXIT16;
import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.tEXIT;
import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.tEXIT_DESCRIPTION;

/**
 * Date: Jun 27, 2010
 * Time: 9:57:38 PM
 *
 * @author xx & hd
 */
@Component
public class ExitAction extends AbstractAction {

    @Autowired
    private ApplicationSettings applicationSettings;

    public ExitAction() {
        super(tEXIT, iEXIT16);
        putValue(Action.SHORT_DESCRIPTION, tEXIT_DESCRIPTION);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F12"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (MessageUtil.showConfirmDialog(ResourcesForApplication.tCONFIRM_CLOSE_APP)) {
            applicationSettings.saveSettings();
            System.exit(0);
        }
    }
}
