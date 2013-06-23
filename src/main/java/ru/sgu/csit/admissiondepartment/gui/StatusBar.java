package ru.sgu.csit.admissiondepartment.gui;

import com.google.common.collect.Lists;
import ru.sgu.csit.admissiondepartment.gui.utils.GBConstraints;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.List;

/**
 * Date: Jun 17, 2010
 * Time: 11:03:32 AM
 *
 * @author xx & hd
 */
public class StatusBar extends JPanel {

    private final List<JLabel> labels = Lists.newArrayList();

    public StatusBar() {
        setLayout(new GridBagLayout());
        setBorder(new EtchedBorder());
    }

    public void addLabel(JLabel label) {
        labels.add(label);
        update();
    }

    private void update() {
        setVisible(false);

        removeAll();

        for (int i = 0; i < labels.size(); ++i) {
            add(labels.get(i), new GBConstraints(i, 0));
        }

        add(new JLabel(), new GBConstraints(labels.size(), 0, true));

        setVisible(true);
    }
}
