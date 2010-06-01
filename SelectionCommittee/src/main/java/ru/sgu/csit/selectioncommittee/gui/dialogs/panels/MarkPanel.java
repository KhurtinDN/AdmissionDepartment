package ru.sgu.csit.selectioncommittee.gui.dialogs.panels;

import ru.sgu.csit.selectioncommittee.common.ReceiptExamine;
import ru.sgu.csit.selectioncommittee.factory.DataAccessFactory;
import ru.sgu.csit.selectioncommittee.gui.utils.GBConstraints;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Date: May 6, 2010
 * Time: 10:17:09 PM
 *
 * @author xx & hd
 */
public class MarkPanel extends JPanel {
    private Map<String, JSpinner> spinners;

    public MarkPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Баллы по ЕГЭ"));

        spinners = new HashMap<String, JSpinner>();

        List<ReceiptExamine> examineNames = DataAccessFactory.getExamines();

        JPanel panel = new JPanel(new GridBagLayout());

        int i = 0;
        for (ReceiptExamine examine : examineNames) {
            JSpinner markSpinner = new JSpinner();
            Dimension dimension = markSpinner.getPreferredSize();
            markSpinner.setPreferredSize(new Dimension(dimension.width + 50, dimension.height));
            spinners.put(examine.getName(), markSpinner);

            panel.add(new JLabel(examine.getName()), new GBConstraints(0, i));
            panel.add(markSpinner, new GBConstraints(1, i).setInsets(2, 10, 2, 10));
            i++;
        }

        add(panel, new GBConstraints(0, 0).setInsets(0).setWeight(100, 0));
    }

    public void setMarks(Map<String, Integer> specialityMap) {
        for (Map.Entry<String, JSpinner> innerEntry : spinners.entrySet()) {
            Integer mark = 0;
            for (Map.Entry<String, Integer> entry : specialityMap.entrySet()) {
                String specialityName = entry.getKey();
                if (innerEntry.getKey().equals(specialityName)) {
                    mark = entry.getValue();
                    break;
                }
            }
            innerEntry.getValue().getModel().setValue(mark);
        }
    }

    public Map<String, Integer> getMarks() {
        Map<String, Integer> marks = new HashMap<String, Integer>();

        for (Map.Entry<String, JSpinner> entry : spinners.entrySet()) {
            String examineName = entry.getKey();
            Integer mark = (Integer) entry.getValue().getValue();
            marks.put(examineName, mark);
        }

        return marks;
    }
}
