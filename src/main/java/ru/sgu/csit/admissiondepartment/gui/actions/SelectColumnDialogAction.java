package ru.sgu.csit.admissiondepartment.gui.actions;

import org.springframework.beans.factory.annotation.Autowired;
import ru.sgu.csit.admissiondepartment.gui.MainFrame;
import ru.sgu.csit.admissiondepartment.gui.MatriculantTable;
import ru.sgu.csit.admissiondepartment.gui.dialogs.SelectColumnDialog;

import javax.swing.*;

import java.awt.event.ActionEvent;

import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.tSHOWCOLUMN_DESCRIPTION;

/**
 * Created by IntelliJ IDEA.
 * User: Администратор
 * Date: 13.07.2010
 * Time: 12:31:42
 * To change this template use File | Settings | File Templates.
 */
public class SelectColumnDialogAction extends AbstractAction {
    @Autowired
    private MainFrame owner;

    private MatriculantTable matriculantTable;
    private SelectColumnDialog selectColumnDialog;

        public SelectColumnDialogAction(MatriculantTable matriculantTable) {
            this.matriculantTable = matriculantTable;
            putValue(Action.NAME, "Видимость столбцов");
            putValue(Action.SHORT_DESCRIPTION, tSHOWCOLUMN_DESCRIPTION);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectColumnDialog == null) {
                selectColumnDialog = new SelectColumnDialog(owner, matriculantTable);
            }
            selectColumnDialog.setVisible(true);
        }
    }
