package ru.sgu.csit.selectioncommittee.gui.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import ru.sgu.csit.selectioncommittee.gui.MatriculantTable;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.tAUTORESIZE;
import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.tAUTORESIZE_DESCRIPTION;

/**
 * Date: Jun 27, 2010
 * Time: 10:47:53 PM
 *
 * @author : xx & hd
 */
@Component("resizeTableAction")
public class ResizeTableAction extends AbstractAction {
    @Autowired
    private MatriculantTable matriculantTable;

    public ResizeTableAction() {
        super(tAUTORESIZE);
        putValue(Action.SHORT_DESCRIPTION, tAUTORESIZE_DESCRIPTION);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl R"));
    }

    @Secured("ROLE_VIEWER")
    @Override
    public void actionPerformed(ActionEvent e) {
        JCheckBoxMenuItem resizeMenuItem = (JCheckBoxMenuItem) e.getSource();

        if (resizeMenuItem.isSelected()) {
            matriculantTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        } else {
            matriculantTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        }
    }
}
