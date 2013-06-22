package ru.sgu.csit.admissiondepartment.gui.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import ru.sgu.csit.admissiondepartment.common.Matriculant;
import ru.sgu.csit.admissiondepartment.factory.DataAccessFactory;
import ru.sgu.csit.admissiondepartment.gui.MatriculantTable;
import ru.sgu.csit.admissiondepartment.gui.dialogs.MatriculantInfoDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ru.sgu.csit.admissiondepartment.gui.utils.MessageUtil.showWarningMessage;
import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.iINFO16;
import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.tINFO;
import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.tINFO_DESCRIPTION;

/**
 * Date: Jun 27, 2010
 * Time: 10:31:00 PM
 *
 * @author : xx & hd
 */
@Component("matriculantInfoAction")
public class MatriculantInfoAction extends AbstractAction {
    @Autowired
    private JFrame owner;

    @Autowired
    private MatriculantTable matriculantTable;
    
    private MatriculantInfoDialog matriculantInfoDialog;

    public MatriculantInfoAction() {
        super(tINFO, iINFO16);
        putValue(Action.SHORT_DESCRIPTION, tINFO_DESCRIPTION);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F3"));
    }

    @Secured("ROLE_VIEWER")
    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedIndex = matriculantTable.getSelectedRow();
        if (selectedIndex >= 0) {
            Matriculant matriculant = DataAccessFactory.getMatriculants()
                    .get(matriculantTable.convertViewRowIndexToMatriculants(selectedIndex));

            if (matriculantInfoDialog == null) {
                matriculantInfoDialog = new MatriculantInfoDialog(owner);
            }
            matriculantInfoDialog.showInfo(matriculant.printToString());
        } else {
            showWarningMessage("Выберите сначала абитуриента");
        }
    }
}
