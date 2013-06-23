package ru.sgu.csit.admissiondepartment.gui.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import ru.sgu.csit.admissiondepartment.gui.MatriculantTable;
import ru.sgu.csit.admissiondepartment.gui.dialogs.SortDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.iSORT16;

/**
 * Date: Jun 27, 2010
 * Time: 10:05:45 PM
 *
 * @author : xx & hd
 */
@Component
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

    @Secured("ROLE_VIEWER")
    @Override
    public void actionPerformed(ActionEvent e) {
        SortDialog sortDialog = new SortDialog(owner, matriculantTable);
        sortDialog.setVisible(true);
    }
}
