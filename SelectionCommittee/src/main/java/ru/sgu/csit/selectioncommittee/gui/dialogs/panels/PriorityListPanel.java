package ru.sgu.csit.selectioncommittee.gui.dialogs.panels;

import ru.sgu.csit.selectioncommittee.gui.utils.GBConstraints;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

/**
 * Date: Jun 15, 2010
 * Time: 4:20:51 PM
 *
 * @author xx & hd
 */
public class PriorityListPanel extends JPanel {
    PriorityManager priorityManager;

    public PriorityListPanel(String title) {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title));
    }

    public void createPriorityList(List<String> labelNames) {
        priorityManager = new PriorityManager();

        for (String labelName : labelNames) {
            priorityManager.add(labelName);
        }
    }

    public List<String> getPriorityList() {
        return priorityManager.getPriorityList();
    }

    public void setPriorityList(List<String> priorityList) {
        priorityManager.setPriorityList(priorityList);
    }

    private class PriorityManager {
        private LinkedList<JToggleButton> selectedConstraintses;
        private LinkedList<JToggleButton> deselectedConstraintses;
        private int size;

        private PriorityManager() {
            this.selectedConstraintses = new LinkedList<JToggleButton>();
            this.deselectedConstraintses = new LinkedList<JToggleButton>();
            this.size = 0;
        }

        public void add(String labelName) {
            SelectItemAction action = new SelectItemAction(labelName);
            JToggleButton button = new JToggleButton(action);
            deselectedConstraintses.add(button);
            PriorityListPanel.this.add(button, getGbConstraints(size));
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
            PriorityListPanel.this.setVisible(false);
            PriorityListPanel.this.removeAll();
            int row = 0;
            for (JToggleButton button : selectedConstraintses) {
                PriorityListPanel.this.add(button, getGbConstraints(row));
                row++;
            }
            for (JToggleButton button : deselectedConstraintses) {
                PriorityListPanel.this.add(button, getGbConstraints(row));
                row++;
            }
            PriorityListPanel.this.setVisible(true);
        }

        public List<String> getPriorityList() {
            List<String> priorityList = new ArrayList<String>();
            for (JToggleButton button : selectedConstraintses) {
                priorityList.add(button.getAction().getValue(Action.NAME).toString());
            }
            return priorityList;
        }

        public void setPriorityList(List<String> priorityList) {
            for (JToggleButton button : selectedConstraintses) {
                button.setSelected(false);
            }
            deselectedConstraintses.addAll(selectedConstraintses);
            selectedConstraintses.clear();

            List<JToggleButton> buttons = new ArrayList<JToggleButton>();
            for (String labelName : priorityList) {
                for (JToggleButton button : deselectedConstraintses) {
                    if (button.getAction().getValue(Action.NAME).equals(labelName)) {
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

    private class SelectItemAction extends AbstractAction {
        private SelectItemAction(String name) {
            this.putValue(Action.NAME, name);
        }

        public void actionPerformed(ActionEvent e) {
            JToggleButton button = (JToggleButton) e.getSource();
            if (button.isSelected()) {
                priorityManager.select(button);
            } else {
                priorityManager.deselect(button);
            }
        }
    }
}
