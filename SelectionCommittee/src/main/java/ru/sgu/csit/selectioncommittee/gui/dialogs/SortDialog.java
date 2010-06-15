package ru.sgu.csit.selectioncommittee.gui.dialogs;

import ru.sgu.csit.selectioncommittee.gui.MatriculantTable;
import ru.sgu.csit.selectioncommittee.gui.dialogs.panels.SortColumnPanel;
import ru.sgu.csit.selectioncommittee.gui.utils.GBConstraints;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.*;

/**
 * Date: Jun 15, 2010
 * Time: 5:26:58 PM
 *
 * @author xx & hd
 */
public class SortDialog extends JDialog {
    private Action sortAction = new SortAction();
    private Action closeAction = new CloseAction();

    private SortColumnPanel sortColumnPanel;

    public SortDialog(JFrame owner, MatriculantTable matriculantTable) {
        super(owner, "Поля для сортирровки", true);
        setSize(600, 300);
        setLayout(new GridBagLayout());

        add(createSelectSortColumnPanel(matriculantTable),
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

    private JPanel createSelectSortColumnPanel(MatriculantTable matriculantTable) {
        List<MatriculantTable.ColumnInfo> columnInfoList = matriculantTable.getColumns();

        List<String> columnList = new ArrayList<String>();
        for (MatriculantTable.ColumnInfo columnInfo : columnInfoList) {
            if (columnInfo.isVisible()) {
                columnList.add(columnInfo.getColumnName());
            }
        }
        sortColumnPanel = new SortColumnPanel(columnList);

//        List<String> columnNameList = matriculantTable.getSortColumnList(); //todo: set saving set column name
//        sortColumnPanel.setColumnNameList(columnNameList);

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
//            List<String> columnNames = sortColumnPanel.getColumnNameList();
            // todo: code for sorting
        }
    }

    private class CloseAction extends AbstractAction {
        private CloseAction() {
            putValue(Action.NAME, tCLOSE);
            putValue(Action.SHORT_DESCRIPTION, tCLOSE_DESCRIPTION);
        }

        public void actionPerformed(ActionEvent e) {
            SortDialog.this.setVisible(false);
        }
    }
}
