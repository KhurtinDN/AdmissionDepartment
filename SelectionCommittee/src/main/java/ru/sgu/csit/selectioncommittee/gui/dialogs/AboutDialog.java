package ru.sgu.csit.selectioncommittee.gui.dialogs;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;

import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.*;

/**
 * Date: May 4, 2010
 * Time: 11:03:26 PM
 *
 * @author xx & hd
 */
public class AboutDialog extends JDialog {

    public AboutDialog(JFrame owner) {
        super(owner, tTITLE_OF_ABOUT, true);
        setSize(600, 300);
        setLayout(new GridLayout());
        JLabel picture = new JLabel(iAPP48);
        JLabel text = new JLabel(tTITLE_OF_ABOUT);
        add(picture);
        add(text);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        Dimension dialogSize = getSize();
        int x = (int) (screenSize.getWidth() / 2 - dialogSize.getWidth() / 2);
        int y = (int) (screenSize.getHeight() / 2 - dialogSize.getHeight() / 2);
        setLocation(x, y);

        Action closeAction = new CloseAction();
        JPanel content = (JPanel) getContentPane();
        content.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "CLOSE_DIALOG");
        content.getActionMap().put("CLOSE_DIALOG", closeAction);
    }

    private class CloseAction extends AbstractAction {
        private CloseAction() {
            putValue(Action.NAME, tCLOSE);
            putValue(Action.SHORT_DESCRIPTION, tCLOSE_DESCRIPTION);
        }

        public void actionPerformed(ActionEvent e) {
            AboutDialog.this.setVisible(false);
        }
    }
}
