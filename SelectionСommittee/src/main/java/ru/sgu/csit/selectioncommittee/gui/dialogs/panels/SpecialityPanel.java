package ru.sgu.csit.selectioncommittee.gui.dialogs.panels;

import ru.sgu.csit.selectioncommittee.common.Speciality;
import ru.sgu.csit.selectioncommittee.factory.DataAccessFactory;
import ru.sgu.csit.selectioncommittee.gui.utils.GBConstraints;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

/**
 * Date: May 6, 2010
 * Time: 9:44:23 PM
 *
 * @author xx & hd
 */
public class SpecialityPanel extends JPanel {
    SpecialityManager specialityManager;

    public SpecialityPanel() {
        GridBagLayout gbLayout = new GridBagLayout();
        setLayout(gbLayout);
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Предпочитаемые специальности"));

        specialityManager = new SpecialityManager();

        for (Speciality speciality : DataAccessFactory.getSpecialities()) {
            specialityManager.add(speciality);
        }
    }

    public Map<Integer, String> getSpecialityMap() {
        return specialityManager.getSpecialityMap();
    }

    public void setSpecialityMap(Map<Integer, String> specialityMap) {
        specialityManager.setSelectedSpeciality(specialityMap);
    }

    private class SpecialityManager {
        private LinkedList<JToggleButton> selectedConstraintses;
        private LinkedList<JToggleButton> deselectedConstraintses;
        private int size;

        private SpecialityManager() {
            this.selectedConstraintses = new LinkedList<JToggleButton>();
            this.deselectedConstraintses = new LinkedList<JToggleButton>();
            this.size = 0;
        }

        public void add(Speciality speciality) {
            SpecialityAction action = new SpecialityAction(speciality);
            JToggleButton button = new JToggleButton(action);
            deselectedConstraintses.add(button);
            GBConstraints gbConstraints = getGbConstraints(size);
            SpecialityPanel.this.add(button, gbConstraints);
            size++;
        }

        public void select(JToggleButton button) {
            deselectedConstraintses.remove(button);
            selectedConstraintses.add(button);
            update();
        }

        public void deselect(JToggleButton button) {
            selectedConstraintses.remove(button);
            deselectedConstraintses.add(0, button);
            update();
        }

        private GBConstraints getGbConstraints(int row) {
            return new GBConstraints(0, row).setFill(GBConstraints.HORIZONTAL).setWeight(100, 0);
        }

        private void update() {
            SpecialityPanel.this.setVisible(false);
            SpecialityPanel.this.removeAll();
            int row = 0;
            for (JToggleButton button : selectedConstraintses) {
                SpecialityPanel.this.add(button, getGbConstraints(row));
                row++;
            }
            for (JToggleButton button : deselectedConstraintses) {
                SpecialityPanel.this.add(button, getGbConstraints(row));
                row++;
            }
            SpecialityPanel.this.setVisible(true);
        }

        public Map<Integer, String> getSpecialityMap() {
            Map<Integer, String> specialityMap = new TreeMap<Integer, String>();
            Integer i = 1;
            for (JToggleButton button : selectedConstraintses) {
                specialityMap.put(i, button.getText());
                i++;
            }
            return specialityMap;
        }

        public void setSelectedSpeciality(Map<Integer, String> specialityMap) {
            Map<Integer, String> specialityTreeMap = new TreeMap<Integer, String>();
            for (Map.Entry<Integer, String> entry : specialityMap.entrySet()) {
                specialityTreeMap.put(entry.getKey(), entry.getValue());
            }

            for (JToggleButton button : selectedConstraintses) {
                button.setSelected(false);
            }
            deselectedConstraintses.addAll(selectedConstraintses);
            selectedConstraintses.clear();

            List<JToggleButton> buttons = new ArrayList<JToggleButton>();
            for (Map.Entry<Integer, String> entry : specialityTreeMap.entrySet()) {
                String specialityName = entry.getValue();
                for (JToggleButton button : deselectedConstraintses) {
                    if (button.getAction().getValue(Action.NAME).equals(specialityName)) {
                        buttons.add(button);
                        break;
                    }
                }
            }

            for (JToggleButton button : buttons) {
                button.setSelected(true);
                deselectedConstraintses.remove(button);
                selectedConstraintses.add(button);
            }

            update();
        }
    }

    private class SpecialityAction extends AbstractAction {
        private SpecialityAction(Speciality speciality) {
            this.putValue(Action.NAME, speciality.getName());
        }

        public void actionPerformed(ActionEvent e) {
            JToggleButton button = (JToggleButton) e.getSource();
            if (button.isSelected()) {
                specialityManager.select(button);
            } else {
                specialityManager.deselect(button);
            }
        }
    }
}
