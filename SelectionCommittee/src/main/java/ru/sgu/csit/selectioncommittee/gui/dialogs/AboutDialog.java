package ru.sgu.csit.selectioncommittee.gui.dialogs;

import javax.swing.*;

import java.awt.*;

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
    }
}
