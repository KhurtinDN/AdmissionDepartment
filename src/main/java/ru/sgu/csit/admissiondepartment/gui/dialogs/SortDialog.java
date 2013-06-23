package ru.sgu.csit.admissiondepartment.gui.dialogs;

import com.google.common.collect.Lists;
import ru.sgu.csit.admissiondepartment.gui.MatriculantTable;
import ru.sgu.csit.admissiondepartment.gui.actions.CloseAction;
import ru.sgu.csit.admissiondepartment.gui.dialogs.panels.SortColumnPanel;
import ru.sgu.csit.admissiondepartment.gui.utils.GBConstraints;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: Jun 15, 2010
 * Time: 5:26:58 PM
 *
 * @author xx & hd
 */
public class SortDialog extends JDialog {

    private Action sortAction = new SortAction();
    private Action closeAction = new CloseAction(this);

    private SortColumnPanel sortColumnPanel;

    private MatriculantTable matriculantTable;

    public SortDialog(JFrame owner, MatriculantTable matriculantTable) {
        super(owner, "Поля для сортирровки", true);
        this.matriculantTable = matriculantTable;
        setSize(600, 300);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        add(createSelectSortColumnPanel(),
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

    private JPanel createSelectSortColumnPanel() {
        List<MatriculantTable.ColumnInfo> columnInfoList = matriculantTable.getColumns();

        List<String> columnList = Lists.newArrayList();

        for (int i = 1, n = columnInfoList.size(); i < n; ++i) {
            MatriculantTable.ColumnInfo columnInfo = columnInfoList.get(i);
            if (columnInfo.isVisible()) {
                columnList.add(columnInfo.getColumnName());
            }
        }

        sortColumnPanel = new SortColumnPanel(columnList);

        return sortColumnPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        JLabel spacerLabel = new JLabel();
        spacerLabel.setPreferredSize(new Dimension(50, 0));
        buttonPanel.add(spacerLabel, new GBConstraints(0, 0, true));
        buttonPanel.add(new JButton(sortAction), new GBConstraints(1, 0));
        buttonPanel.add(new JButton(closeAction), new GBConstraints(2, 0));
        return buttonPanel;
    }

    private class SortAction extends AbstractAction {
        private SortAction() {
            putValue(Action.NAME, "Сортировать");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<String> columnNames = sortColumnPanel.getColumnNameList();
            List<Integer> columnIndexList = Lists.newArrayList();

            for (String columnName : columnNames) {
                for (int index = 0, n = matriculantTable.getColumns().size(); index < n; ++index) {
                    MatriculantTable.ColumnInfo columnInfo = matriculantTable.getColumns().get(index);
                    if (columnName.equals(columnInfo.getColumnName())) {
                        columnIndexList.add(index);
                        break;
                    }
                }
            }

            SortDialog.this.setVisible(false);

            matriculantTable.sort(columnIndexList);
            matriculantTable.repaint();
        }
    }
}
