package ru.sgu.csit.selectioncommittee.gui.actions;

import org.springframework.stereotype.Component;
import ru.sgu.csit.selectioncommittee.gui.utils.ApplicationSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.iEXIT16;
import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.tEXIT;
import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.tEXIT_DESCRIPTION;

/**
 * Date: Jun 27, 2010
 * Time: 9:57:38 PM
 *
 * @author xx & hd
 */
@Component("exitAction")
public class ExitAction extends AbstractAction {
    public ExitAction() {
        super(tEXIT, iEXIT16);
        putValue(Action.SHORT_DESCRIPTION, tEXIT_DESCRIPTION);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F12"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        if (confirm(tCONFIRM_CLOSE_APP)) {
        ApplicationSettings.getSettings().saveSettings();
        System.exit(0);
//        }
    }
}
