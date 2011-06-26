package ru.sgu.csit.selectioncommittee.gui.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import ru.sgu.csit.selectioncommittee.common.Matriculant;
import ru.sgu.csit.selectioncommittee.factory.DataAccessFactory;
import ru.sgu.csit.selectioncommittee.gui.MainFrame;
import ru.sgu.csit.selectioncommittee.gui.MatriculantTable;
import ru.sgu.csit.selectioncommittee.gui.dialogs.MatriculantDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ru.sgu.csit.selectioncommittee.gui.utils.MessageUtil.showWarningMessage;
import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.iEDIT16;
import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.tEDIT;
import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.tEDIT_DESCRIPTION;

/**
 * Date: Jun 27, 2010
 * Time: 10:17:46 PM
 *
 * @author : xx & hd
 */
@Component("editMatriculantAction")
public class EditMatriculantAction extends AbstractAction {
    @Autowired
    private MainFrame owner;

    @Autowired
    private MatriculantTable matriculantTable;

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

            if (matriculantDialog == null) {
                matriculantDialog = new MatriculantDialog(owner, false, matriculant);
            } else {
                matriculantDialog.setMatriculant(matriculant);
            }
            matriculantDialog.setVisible(true);
        } else {
            showWarningMessage("Выберите сначала абитуриента");
        }
    }
}
