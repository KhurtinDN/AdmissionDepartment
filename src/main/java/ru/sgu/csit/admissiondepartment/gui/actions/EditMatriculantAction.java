package ru.sgu.csit.admissiondepartment.gui.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import ru.sgu.csit.admissiondepartment.common.Matriculant;
import ru.sgu.csit.admissiondepartment.factory.DataAccessFactory;
import ru.sgu.csit.admissiondepartment.gui.MatriculantTable;
import ru.sgu.csit.admissiondepartment.gui.dialogs.MatriculantDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ru.sgu.csit.admissiondepartment.gui.utils.MessageUtil.showWarningMessage;
import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.*;

/**
 * Date: Jun 27, 2010
 * Time: 10:17:46 PM
 *
 * @author : xx & hd
 */
@Component
public class EditMatriculantAction extends AbstractAction {

    @Autowired
    private MatriculantTable matriculantTable;

    @Autowired
    private MatriculantDialog matriculantDialog;

    public EditMatriculantAction() {
        super(tEDIT, iEDIT16);
        putValue(Action.SHORT_DESCRIPTION, tEDIT_DESCRIPTION);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F4"));
    }

    @Secured("ROLE_WORKER")
    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedIndex = matriculantTable.getSelectedRow();

        if (selectedIndex >= 0) {
            Matriculant matriculant = DataAccessFactory.getMatriculants()
                    .get(matriculantTable.convertViewRowIndexToMatriculants(selectedIndex));

            matriculantDialog.setTitle("Редактирование данных студента");
            matriculantDialog.setMatriculant(matriculant);
            matriculantDialog.setVisible(true);
        } else {
            showWarningMessage("Выберите сначала абитуриента");
        }
    }
}
