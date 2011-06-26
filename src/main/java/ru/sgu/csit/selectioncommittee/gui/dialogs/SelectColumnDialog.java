package ru.sgu.csit.selectioncommittee.gui.dialogs;

import ru.sgu.csit.selectioncommittee.gui.MatriculantTable;
import ru.sgu.csit.selectioncommittee.gui.actions.ShowColumnAction;
import ru.sgu.csit.selectioncommittee.gui.utils.GBConstraints;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.tCLOSE;
import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.tCLOSE_DESCRIPTION;

/**
 * Date: Jun 23, 2010
 * Time: 11:36:32 AM
 *
 * @author xx & hd
 */
public class SelectColumnDialog extends JDialog {
    private Action applyAction = new ApplyAction();
    private Action closeAction = new CloseAction();

    private MatriculantTable matriculantTable;

    private List<JCheckBox> columnCheckBoxList;

    public SelectColumnDialog(Window owner, MatriculantTable matriculantTable) {
        super(owner, "Выбор колонок для отображения");
        this.matriculantTable = matriculantTable;

        setLayout(new GridBagLayout());

        add(createSelectColumnPanel(), new GBConstraints(0, 0).setFill(GBConstraints.BOTH).setWeight(100, 100));
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

    private JPanel createSelectColumnPanel() {
        JPanel columnPanel = new JPanel(new GridBagLayout());

        List<MatriculantTable.ColumnInfo> columnInfoList = matriculantTable.getColumns();
        columnCheckBoxList = new ArrayList<JCheckBox>();

        for (int column = 0, n = columnInfoList.size(); column < n; ++column) {
            MatriculantTable.ColumnInfo columnInfo = columnInfoList.get(column);

            JCheckBox columnCheckBox = new JCheckBox(
                    new ShowColumnAction(columnInfo.getColumnName(), matriculantTable));//columnInfo.getColumnName());
            columnCheckBox.getModel().setActionCommand("" + column);
            columnCheckBox.setSelected(columnInfo.isVisible());   // todo: need check in table (not TableModel)
            columnCheckBoxList.add(columnCheckBox);
            columnPanel.add(columnCheckBox, new GBConstraints(0, column));
        }

        columnPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Выбор колонок"));
        return columnPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        JLabel spacerLabel = new JLabel();
        spacerLabel.setPreferredSize(new Dimension(150, 0));
        buttonPanel.add(spacerLabel, new GBConstraints(0, 0, true));
        buttonPanel.add(new JButton(applyAction), new GBConstraints(1, 0));
        buttonPanel.add(new JButton(closeAction), new GBConstraints(2, 0));
        return buttonPanel;
    }

    private class ApplyAction extends AbstractAction {
        private ApplyAction() {
            putValue(Action.NAME, "Применить");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Integer> columnIndexList = new ArrayList<Integer>();
            for (JCheckBox columnCheckBox : columnCheckBoxList) {
                Integer columnIndex = new Integer(columnCheckBox.getActionCommand());
                columnIndexList.add(columnIndex);
            }

//            matriculantTable.applyVisibleColumn(columnIndexList);

            SelectColumnDialog.this.setVisible(false);
        }
    }

    private class CloseAction extends AbstractAction {
        private CloseAction() {
            putValue(Action.NAME, tCLOSE);
            putValue(Action.SHORT_DESCRIPTION, tCLOSE_DESCRIPTION);
        }

        public void actionPerformed(ActionEvent e) {
            SelectColumnDialog.this.setVisible(false);
        }
    }
}
