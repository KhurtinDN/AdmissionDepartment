package ru.sgu.csit.selectioncommittee.gui.dialogs;

import ru.sgu.csit.selectioncommittee.gui.MatriculantTable;
import ru.sgu.csit.selectioncommittee.gui.utils.GBConstraints;
import ru.sgu.csit.selectioncommittee.gui.utils.SomeUtils;
import ru.sgu.csit.selectioncommittee.service.ArgumentNotExcelFileException;
import ru.sgu.csit.selectioncommittee.service.ExportToExcel;
import ru.sgu.csit.selectioncommittee.service.WritingException;

import static ru.sgu.csit.selectioncommittee.gui.utils.MessageUtil.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.*;
import java.util.List;

import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.*;

/**
 * Date: 03.06.2010
 * Time: 8:28:36
 *
 * @author : xx & hd
 */
public class ExportToExcelDialog extends JDialog {
    private Action closeAction = new CloseAction();

    private MatriculantTable matriculantTable = new MatriculantTable();

    private JCheckBox needOpenDocumentCheckBox = new JCheckBox("Открыть документ после экспорта");

    public ExportToExcelDialog(JFrame owner) {
        super(owner, "Экспорт в excel", false);

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
        JPanel selectColumnPanelPanel = new JPanel(new GridBagLayout());
//        matriculantTable.hideGeneratedColumns();
        selectColumnPanelPanel.add(new JScrollPane(matriculantTable),
                new GBConstraints(0, 0).setFill(GBConstraints.BOTH).setWeight(100, 100));
        selectColumnPanelPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Выбор колонок для экспорта"));
        return selectColumnPanelPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.add(needOpenDocumentCheckBox, new GBConstraints(0, 0));
        buttonPanel.add(new JLabel(), new GBConstraints(1, 0, true));
        buttonPanel.add(new JButton(new ExportToExcelAction()), new GBConstraints(2, 0));
        buttonPanel.add(new JButton(closeAction), new GBConstraints(3, 0));
        return buttonPanel;
    }

    private class ExportToExcelAction extends AbstractAction {
        private ExportToExcelAction() {
            putValue(Action.NAME, tEXPORT_TO_EXCEL);
            putValue(Action.SHORT_DESCRIPTION, tEXPORT_TO_EXCEL_DESCRIPTION);
        }

        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new ExcelFileChooser();

            if (fileChooser.showSaveDialog(ExportToExcelDialog.this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                List<String> headerList = createHeaderList();

                List<List<String>> contentLists = createContentLists();

                ExportToExcel exportToExcel = new ExportToExcel();
                try {
                    exportToExcel.writeMatriculants(file, "Абитуриенты", headerList, contentLists);
                } catch (ArgumentNotExcelFileException e1) {
                    showErrorMessage("Файл должен иметь расширение *.xls или *.xlsx");
                } catch (FileNotFoundException e1) {
                    showErrorMessage("Файл " + file.getPath() + " не найден.");
                } catch (WritingException e1) {
                    showErrorMessage("При экспорте произошла ошибка записи.");
                }

                if (needOpenDocumentCheckBox.isSelected()) {
                    SomeUtils.openExcelViewer(file);
                }
            }
            setVisible(false);
        }

        private List<String> createHeaderList() {
            List<String> headerList = new ArrayList<String>();
            for (int i = 0, n = matriculantTable.getColumnCount(); i < n; ++i) {
                String columnName = matriculantTable.getColumnName(i);
                headerList.add(columnName);
            }
            return headerList;
        }

        private List<List<String>> createContentLists() {
            List<List<String>> contentLists = new ArrayList<List<String>>();

            for (int i = 0, n = matriculantTable.getRowCount(); i < n; ++i) {
                List<String> rowContentList = new ArrayList<String>();
                for (int j = 0, m = matriculantTable.getColumnCount(); j < m; ++j) {
                    String cellValue;
                    if (matriculantTable.getValueAt(i, j) == null) {
                        cellValue = "";
                    } else {
                        cellValue = matriculantTable.getValueAt(i, j).toString();
                    }
                    rowContentList.add(cellValue);
                }
                contentLists.add(rowContentList);
            }

            return contentLists;
        }
    }

    private class CloseAction extends AbstractAction {
        private CloseAction() {
            putValue(Action.NAME, tCLOSE);
            putValue(Action.SHORT_DESCRIPTION, tCLOSE_DESCRIPTION);
        }

        public void actionPerformed(ActionEvent e) {
            ExportToExcelDialog.this.setVisible(false);
        }
    }
}
