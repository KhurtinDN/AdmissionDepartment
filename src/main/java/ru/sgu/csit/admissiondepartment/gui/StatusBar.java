package ru.sgu.csit.admissiondepartment.gui;

import ru.sgu.csit.admissiondepartment.gui.utils.GBConstraints;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.LinkedList;

/**
 * Date: Jun 17, 2010
 * Time: 11:03:32 AM
 *
 * @author xx & hd
 */
public class StatusBar extends JPanel {
    private LinkedList<JLabel> labels;
    public StatusBar() {
        labels = new LinkedList<JLabel>();

        setLayout(new GridBagLayout());
        setBorder(new EtchedBorder());
//        setBorder(new BevelBorder(BevelBorder.LOWERED));
    }

    public void addLabel(JLabel label) {
        labels.add(label);
        update();
    }

    private void update() {
        setVisible(false);

        removeAll();

        int i = 0;
        for (JLabel label : labels) {
            add(label, new GBConstraints(i, 0));
            i++;
        }
        add(new JLabel(), new GBConstraints(i, 0, true));

        setVisible(true);
    }
}
