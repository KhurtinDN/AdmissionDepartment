package ru.sgu.csit.admissiondepartment.gui.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import ru.sgu.csit.admissiondepartment.common.Matriculant;
import ru.sgu.csit.admissiondepartment.dao.MatriculantDAO;
import ru.sgu.csit.admissiondepartment.factory.DataAccessFactory;
import ru.sgu.csit.admissiondepartment.gui.MainFrame;
import ru.sgu.csit.admissiondepartment.gui.MatriculantTable;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ru.sgu.csit.admissiondepartment.gui.utils.MessageUtil.showConfirmDialog;
import static ru.sgu.csit.admissiondepartment.gui.utils.MessageUtil.showWarningMessage;
import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.iDELETE16;
import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.tDELETE;
import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.tDELETE_DESCRIPTION;

/**
 * Date: Jun 27, 2010
 * Time: 10:24:39 PM
 *
 * @author : xx & hd
 */
@Component
public class DeleteMatriculantAction extends AbstractAction {

    @Autowired
    private MainFrame mainFrame;

    @Autowired
    private MatriculantTable matriculantTable;

    @Autowired
    private MatriculantDAO matriculantDAO;

    public DeleteMatriculantAction() {
        super(tDELETE, iDELETE16);
        putValue(Action.SHORT_DESCRIPTION, tDELETE_DESCRIPTION);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F8"));
    }

    @Secured("ROLE_WORKER")
    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedIndex = matriculantTable.getSelectedRow();

        if (selectedIndex >= 0) {
            if (showConfirmDialog("Удалить абитуриента?")) {
                Matriculant matriculant = DataAccessFactory.getMatriculants()
                        .get(matriculantTable.convertViewRowIndexToMatriculants(selectedIndex));

                DataAccessFactory.getMatriculants().remove(
                        matriculantTable.convertViewRowIndexToMatriculants(selectedIndex));
                matriculantTable.deleteFromViewIndex(selectedIndex);
                matriculantDAO.delete(matriculant);
                mainFrame.refresh();
            }
        } else {
            showWarningMessage("Выберите сначала абитуриента");
        }
    }
}
