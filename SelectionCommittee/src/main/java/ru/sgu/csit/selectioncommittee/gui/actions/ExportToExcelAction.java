package ru.sgu.csit.selectioncommittee.gui.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import ru.sgu.csit.selectioncommittee.gui.dialogs.ExportToExcelDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.iEXCEL16;
import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.tEXPORT_TO_EXCEL;
import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.tEXPORT_TO_EXCEL_DESCRIPTION;

/**
 * Date: Jun 27, 2010
 * Time: 10:11:55 PM
 *
 * @author : xx & hd
 */
@Component("exportToExcelAction")
public class ExportToExcelAction extends AbstractAction {
    @Autowired
    private JFrame owner;
    
    private JDialog exportDialog;

    public ExportToExcelAction() {
        super(tEXPORT_TO_EXCEL, iEXCEL16);
        putValue(Action.SHORT_DESCRIPTION, tEXPORT_TO_EXCEL_DESCRIPTION);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F2"));
    }

    @Secured("ROLE_VIEWER")
    @Override
    public void actionPerformed(ActionEvent e) {
        if (exportDialog == null) {
            exportDialog = new ExportToExcelDialog(owner);
        }
        exportDialog.setVisible(true);
    }
}
