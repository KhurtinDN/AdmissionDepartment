package ru.sgu.csit.selectioncommittee.gui.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import ru.sgu.csit.selectioncommittee.common.Matriculant;
import ru.sgu.csit.selectioncommittee.gui.MainFrame;
import ru.sgu.csit.selectioncommittee.gui.dialogs.MatriculantDialog;

import javax.swing.*;

import java.awt.event.ActionEvent;

import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.*;

/**
 * Date: Jun 27, 2010
 * Time: 10:14:01 PM
 *
 * @author : xx & hd
 */
@Component("addMatriculantAction")
public class AddMatriculantAction extends AbstractAction {
    @Autowired
    private MainFrame owner;

    private MatriculantDialog matriculantDialog;

    public AddMatriculantAction() {
        super(tADD, iADD16);
        putValue(Action.SHORT_DESCRIPTION, tADD_DESCRIPTION);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F7"));
    }

    public void setOwner(MainFrame owner) {
        this.owner = owner;
    }

    @Secured("ROLE_WORKER")
    @Override
    public void actionPerformed(ActionEvent e) {
        Matriculant matriculant = new Matriculant();
        if (matriculantDialog == null) {
            matriculantDialog = new MatriculantDialog(owner, true, matriculant);
        } else {
            matriculantDialog.setMatriculant(matriculant);
        }
        matriculantDialog.setVisible(true);
    }
}
