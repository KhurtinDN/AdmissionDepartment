package ru.sgu.csit.admissiondepartment.gui.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import ru.sgu.csit.admissiondepartment.common.Speciality;
import ru.sgu.csit.admissiondepartment.factory.DataAccessFactory;
import ru.sgu.csit.admissiondepartment.gui.MatriculantTable;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.tCALCALL;
import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.tCALCFORSPECIALITY_DESCRIPTION;
import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.tCALCFOR_PREF;

/**
 * Date: Jun 27, 2010
 * Time: 11:14:48 PM
 *
 * @author : xx & hd
 */
@Component("calculateMatriculantsAction")
public class CalculateMatriculantsAction extends AbstractAction {
    @Autowired
    private MatriculantTable matriculantTable;

    public CalculateMatriculantsAction() {
        super("Список отображения");
        putValue(Action.SHORT_DESCRIPTION, tCALCFORSPECIALITY_DESCRIPTION);
    }

    @Secured("ROLE_VIEWER")
    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox specialityComboBox = (JComboBox) e.getSource();

        String itemName = (String) specialityComboBox.getSelectedItem();

        if (itemName.equals(tCALCALL)) {
            MatriculantTable.resetRowIndexes();
            matriculantTable.repaint();
        } else {
            for (Speciality speciality : DataAccessFactory.getSpecialities()) {
                if (itemName.equals(tCALCFOR_PREF + speciality.getName())) {
                    matriculantTable.sortBy(speciality);
                    matriculantTable.refresh();

                    return;
                }
            }
        }
    }
}
