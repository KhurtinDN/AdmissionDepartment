package ru.sgu.csit.admissiondepartment.gui.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import ru.sgu.csit.admissiondepartment.gui.MatriculantTable;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.tHIGHLIGHTING;
import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.tHIGHLIGHTING_DESCRIPTION;

/**
 * Date: Jun 27, 2010
 * Time: 10:52:28 PM
 *
 * @author : xx & hd
 */
@Component("switchHighlightingTableAction")
public class SwitchHighlightingTableAction extends AbstractAction {
    @Autowired
    private MatriculantTable matriculantTable;

    public SwitchHighlightingTableAction() {
        super(tHIGHLIGHTING);
        putValue(Action.SHORT_DESCRIPTION, tHIGHLIGHTING_DESCRIPTION);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl L"));
    }

    @Secured("ROLE_VIEWER")
    @Override
    public void actionPerformed(ActionEvent e) {
        JCheckBoxMenuItem lightMenuItem = (JCheckBoxMenuItem) e.getSource();

        MatriculantTable.setHighlighting(lightMenuItem.isSelected());
        matriculantTable.repaint();
    }
}
