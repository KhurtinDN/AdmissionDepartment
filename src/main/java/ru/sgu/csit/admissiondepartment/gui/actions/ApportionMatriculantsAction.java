package ru.sgu.csit.admissiondepartment.gui.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import ru.sgu.csit.admissiondepartment.gui.MatriculantTable;
import ru.sgu.csit.admissiondepartment.gui.dialogs.ApportionMatriculantsDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.iAPPORTION16;
import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.tAPPORTION_SPEC;
import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.tAPPORTION_SPEC_DESCRIPTION;

/**
 * Date: Jun 27, 2010
 * Time: 10:55:59 PM
 *
 * @author : xx & hd
 */
@Component("apportionMatriculantsAction")
public class ApportionMatriculantsAction extends AbstractAction {
    @Autowired
    private JFrame owner;
    
    @Autowired
    private MatriculantTable matriculantTable;

    private ApportionMatriculantsDialog apportionMatriculantsDialog;

    public ApportionMatriculantsAction() {
        super(tAPPORTION_SPEC, iAPPORTION16);
        putValue(Action.SHORT_DESCRIPTION, tAPPORTION_SPEC_DESCRIPTION);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F9"));
    }

    @Secured("ROLE_WORKER")
    @Override
    public void actionPerformed(ActionEvent e) {
        if (apportionMatriculantsDialog == null) {
            apportionMatriculantsDialog = new ApportionMatriculantsDialog(owner, matriculantTable);
        }
        apportionMatriculantsDialog.setVisible(true);
    }
}
