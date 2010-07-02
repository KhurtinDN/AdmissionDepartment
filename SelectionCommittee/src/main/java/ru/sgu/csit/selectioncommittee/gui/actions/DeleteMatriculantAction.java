package ru.sgu.csit.selectioncommittee.gui.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import ru.sgu.csit.selectioncommittee.common.Matriculant;
import ru.sgu.csit.selectioncommittee.factory.DataAccessFactory;
import ru.sgu.csit.selectioncommittee.gui.MatriculantTable;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ru.sgu.csit.selectioncommittee.gui.utils.MessageUtil.showConfirmDialog;
import static ru.sgu.csit.selectioncommittee.gui.utils.MessageUtil.showWarningMessage;
import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.iDELETE16;
import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.tDELETE;
import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.tDELETE_DESCRIPTION;

/**
 * Date: Jun 27, 2010
 * Time: 10:24:39 PM
 *
 * @author : xx & hd
 */
@Component("deleteMatriculantAction")
public class DeleteMatriculantAction extends AbstractAction {
    @Autowired
    private MatriculantTable matriculantTable;

    public DeleteMatriculantAction() {
        super(tDELETE, iDELETE16);
        putValue(Action.SHORT_DESCRIPTION, tDELETE_DESCRIPTION);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F8"));
    }

    public void setMatriculantTable(MatriculantTable matriculantTable) {
        this.matriculantTable = matriculantTable;
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
                MatriculantTable.deleteFromViewIndex(selectedIndex);
                DataAccessFactory.getMatriculantDAO().delete(matriculant);
                matriculantTable.refresh();
            }
        } else {
            showWarningMessage("Выберите сначала абитуриента");
        }
    }
}
