package ru.sgu.csit.admissiondepartment.gui.actions;

import org.springframework.security.access.annotation.Secured;
import ru.sgu.csit.admissiondepartment.gui.MatriculantTable;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.tSHOWENT_DESCRIPTION;
import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.tSHOWENT_PREF;
import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.tSHOWNOTENT_ITEM;

/**
 * Date: Jun 27, 2010
 * Time: 11:10:05 PM
 *
 * @author : xx & hd
 */
public class ShowMatriculantsAction extends AbstractAction {

    private MatriculantTable matriculantTable;

    private String specialityName;

    public ShowMatriculantsAction(MatriculantTable matriculantTable, String specialityName) {
        this.matriculantTable = matriculantTable;
        this.specialityName = specialityName;
        if (tSHOWNOTENT_ITEM.equals(specialityName)) {
            putValue(Action.NAME, specialityName);
        } else {
            putValue(Action.NAME, tSHOWENT_PREF + specialityName);
        }
        putValue(Action.SHORT_DESCRIPTION, tSHOWENT_DESCRIPTION);
    }

    @Secured("ROLE_VIEWER")
    @Override
    public void actionPerformed(ActionEvent e) {
        JCheckBoxMenuItem showMenuItem = (JCheckBoxMenuItem) e.getSource();

        if (tSHOWNOTENT_ITEM.equals(specialityName)) {
            MatriculantTable.setShowNotEntrance(showMenuItem.isSelected());
        } else {
            MatriculantTable.setShowEntrance(specialityName, showMenuItem.isSelected());
        }
        MatriculantTable.recalculateViewRows();
        matriculantTable.refresh();
    }
}
