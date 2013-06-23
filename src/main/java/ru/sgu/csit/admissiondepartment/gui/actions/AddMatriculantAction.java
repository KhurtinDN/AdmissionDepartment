package ru.sgu.csit.admissiondepartment.gui.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import ru.sgu.csit.admissiondepartment.common.Matriculant;
import ru.sgu.csit.admissiondepartment.gui.dialogs.MatriculantDialog;

import javax.swing.*;

import java.awt.event.ActionEvent;

import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.*;

/**
 * Date: Jun 27, 2010
 * Time: 10:14:01 PM
 *
 * @author : xx & hd
 */
@Component
public class AddMatriculantAction extends AbstractAction {

    @Autowired
    private MatriculantDialog matriculantDialog;

    public AddMatriculantAction() {
        super(tADD, iADD16);
        putValue(Action.SHORT_DESCRIPTION, tADD_DESCRIPTION);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F7"));
    }

    @Secured("ROLE_WORKER")
    @Override
    public void actionPerformed(ActionEvent e) {
        matriculantDialog.setTitle("Добавление нового абитуриента");
        matriculantDialog.setMatriculant(new Matriculant());
        matriculantDialog.setVisible(true);
    }
}
