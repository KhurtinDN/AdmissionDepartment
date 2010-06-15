package ru.sgu.csit.selectioncommittee.gui.dialogs;

import ru.sgu.csit.selectioncommittee.gui.dialogs.panels.CapacityOnSpecialitiesPanel;
import ru.sgu.csit.selectioncommittee.gui.utils.GBConstraints;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;

import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.*;

/**
 * Date: Jun 15, 2010
 * Time: 9:01:58 PM
 *
 * @author xx & hd
 */
public class ApportionMatriculantsDialog extends JDialog {
    private JCheckBox saveInDbCheckBox = new JCheckBox("Сохранить распределение в базе данных");
    private Action apportionAction = new ApportionAction();
    private Action closeAction = new CloseAction();

    private CapacityOnSpecialitiesPanel capacityOnSpecialitiesPanel;

    public ApportionMatriculantsDialog(JFrame owner) {
        super(owner, "Распредесение абитуриентов по специальностям", true);
        setSize(600, 300);
        setLayout(new GridBagLayout());

        add(createCapacityOnSpecialitiesPanel(),
                new GBConstraints(0, 0).setFill(GBConstraints.BOTH).setWeight(100, 100));
        add(createButtonPanel(), new GBConstraints(0, 1, true));

        pack();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        Dimension dialogSize = getSize();
        int x = (int) (screenSize.getWidth() / 2 - dialogSize.getWidth() / 2);
        int y = (int) (screenSize.getHeight() / 2 - dialogSize.getHeight() / 2);
        setLocation(x, y);

        JPanel content = (JPanel) getContentPane();
        content.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "CLOSE_DIALOG");
        content.getActionMap().put("CLOSE_DIALOG", closeAction);
    }

    private JPanel createCapacityOnSpecialitiesPanel() {
        capacityOnSpecialitiesPanel = new CapacityOnSpecialitiesPanel();
//        capacityOnSpecialitiesPanel.setCapacityOnSpecialities(null); // todo: set saving capacities
        return capacityOnSpecialitiesPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.add(saveInDbCheckBox, new GBConstraints(0, 0));
        buttonPanel.add(new JLabel(), new GBConstraints(1, 0, true));
        buttonPanel.add(new JButton(apportionAction), new GBConstraints(2, 0));
        buttonPanel.add(new JButton(closeAction), new GBConstraints(3, 0));
        return buttonPanel;
    }

    private class ApportionAction extends AbstractAction {
        private ApportionAction() {
            putValue(Action.NAME, "Распределить");
        }

        public void actionPerformed(ActionEvent e) {
//            Map<String, Integer> capacityOnSpecialitiesMap = capacityOnSpecialitiesPanel.getCapacityOnSpecialities();
            // todo: code for apportion
        }
    }

    private class CloseAction extends AbstractAction {
        private CloseAction() {
            putValue(Action.NAME, tCLOSE);
            putValue(Action.SHORT_DESCRIPTION, tCLOSE_DESCRIPTION);
        }

        public void actionPerformed(ActionEvent e) {
            ApportionMatriculantsDialog.this.setVisible(false);
        }
    }
}
