package ru.sgu.csit.selectioncommittee.gui.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import ru.sgu.csit.selectioncommittee.gui.MatriculantTable;
import ru.sgu.csit.selectioncommittee.gui.dialogs.SortDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.iSORT16;

/**
 * Date: Jun 27, 2010
 * Time: 10:05:45 PM
 *
 * @author : xx & hd
 */
@Component("sortAction")
public class SortAction extends AbstractAction {
    @Autowired
    private JFrame owner;

    @Autowired
    private MatriculantTable matriculantTable;

    public SortAction() {
        super("Сортировка", iSORT16);
        putValue(Action.SHORT_DESCRIPTION, "Выбрать столбцы для сортировки");
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F6"));
    }

    public void setOwner(JFrame owner) {
        this.owner = owner;
    }

    public void setMatriculantTable(MatriculantTable matriculantTable) {
        this.matriculantTable = matriculantTable;
    }

    @Secured("ROLE_VIEWER")
    @Override
    public void actionPerformed(ActionEvent e) {
        SortDialog sortDialog = new SortDialog(owner, matriculantTable);
        sortDialog.setVisible(true);
    }
}
