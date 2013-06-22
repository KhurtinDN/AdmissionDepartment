package ru.sgu.csit.admissiondepartment.gui.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.tCLOSE;
import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.tCLOSE_DESCRIPTION;

/**
 * Date: Jun 28, 2010
 * Time: 4:16:33 AM
 *
 * @author : xx & hd
 */
public class CloseAction extends AbstractAction {
    private JDialog owner;

    public CloseAction(JDialog owner) {
        super(tCLOSE);
        putValue(Action.SHORT_DESCRIPTION, tCLOSE_DESCRIPTION);
        this.owner = owner;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        owner.setVisible(false);
    }
}
