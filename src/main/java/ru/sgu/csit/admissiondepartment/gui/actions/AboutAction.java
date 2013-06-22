package ru.sgu.csit.admissiondepartment.gui.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import ru.sgu.csit.admissiondepartment.gui.dialogs.AboutDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.iABOUT;
import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.tABOUT;
import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.tABOUT_DESCRIPTION;

/**
 * Date: Jun 27, 2010
 * Time: 10:28:32 PM
 *
 * @author : xx & hd
 */
@Component("aboutAction")
public class AboutAction extends AbstractAction {
    @Autowired
    private JFrame owner;

    private AboutDialog dialog = null;

    public AboutAction() {
        super(tABOUT, iABOUT);
        putValue(Action.SHORT_DESCRIPTION, tABOUT_DESCRIPTION);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F1"));
    }

    @Secured("ROLE_VIEWER")
    @Override
    public void actionPerformed(ActionEvent e) {
        if (dialog == null) {
            dialog = new AboutDialog(owner);
        }
        dialog.setVisible(true);
    }
}
