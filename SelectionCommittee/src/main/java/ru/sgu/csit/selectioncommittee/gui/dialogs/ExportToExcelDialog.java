package ru.sgu.csit.selectioncommittee.gui.dialogs;

import ru.sgu.csit.selectioncommittee.common.Matriculant;
import ru.sgu.csit.selectioncommittee.factory.DataAccessFactory;
import ru.sgu.csit.selectioncommittee.gui.MatriculantTable;
import ru.sgu.csit.selectioncommittee.gui.utils.GBConstraints;
import ru.sgu.csit.selectioncommittee.service.ArgumentNotExcelFileException;
import ru.sgu.csit.selectioncommittee.service.ExportToExcel;
import ru.sgu.csit.selectioncommittee.service.WritingException;

import static ru.sgu.csit.selectioncommittee.gui.utils.MessageUtil.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
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
    private MatriculantTable matriculantTable;
    private List<JCheckBox> checkBoxList;
    private JCheckBox needOpenDocumentCheckBox = new JCheckBox("Открыть документ после экспорта");

    public ExportToExcelDialog(JFrame owner, MatriculantTable matriculantTable) {
        super(owner, "Экспорт в excel", false);
        this.matriculantTable = matriculantTable;

        setLayout(new GridBagLayout());

        add(createSelectColumnPanel(), new GBConstraints(0, 0, true));
        add(createButtonPanel(), new GBConstraints(0, 1, true));

        pack();
    }

    private JPanel createSelectColumnPanel() {
        JPanel selectColumnPanelPanel = new JPanel(new GridBagLayout());

        checkBoxList = new ArrayList<JCheckBox>();
        for (int i = 0; i < matriculantTable.getColumnCount(); ++i) {
            JCheckBox checkBox = new JCheckBox(matriculantTable.getColumnName(i));
            checkBox.setActionCommand(matriculantTable.getColumnName(i));
            selectColumnPanelPanel.add(checkBox,
                    new GBConstraints(0, i).setAnchor(GBConstraints.NORTHWEST));
            checkBoxList.add(checkBox);
        }

        selectColumnPanelPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Выбор колонок для экспорта"));
        return selectColumnPanelPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.add(needOpenDocumentCheckBox, new GBConstraints(0, 0));
        buttonPanel.add(new JLabel(), new GBConstraints(1, 0, true));
        buttonPanel.add(new JButton(new ExportToExcelAction()), new GBConstraints(2, 0));
        buttonPanel.add(new JButton(new CloseAction()), new GBConstraints(3, 0));
        return buttonPanel;
    }

    private class ExportToExcelAction extends AbstractAction {
        private ExportToExcelAction() {
            putValue(Action.NAME, tEXPORT_TO_EXCEL);
            putValue(Action.SHORT_DESCRIPTION, tEXPORT_TO_EXCEL_DESCRIPTION);
        }

        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel файлы (*.xls, *.xlsx)", "xls", "xlsx"));
            fileChooser.setDialogTitle("Сохранение абитуриентов в Excel файл");

            if (fileChooser.showSaveDialog(ExportToExcelDialog.this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                List<String> headerList = new ArrayList<String>();
                for (JCheckBox checkBox : checkBoxList) {
                    if (checkBox.isSelected()) {
                        headerList.add(checkBox.getActionCommand());
                    }
                }

                List<List<String>> contentLists = createContentLists(headerList); // todo: get true cells

                ExportToExcel exportToExcel = new ExportToExcel(headerList, contentLists);
                try {
                    exportToExcel.write(file);
                } catch (ArgumentNotExcelFileException e1) {
                    showErrorMessage("Файл должен иметь расширение *.xls или *.xlsx");
                } catch (FileNotFoundException e1) {
                    showErrorMessage("Файл " + file.getPath() + " не найден.");
                } catch (WritingException e1) {
                    showErrorMessage("При экспорте произошла ошибка записи.");
                }
            }
        }

        private List<List<String>> createContentLists(List<String> headerList) {
            List<List<String>> contentLists = new ArrayList<List<String>>();

            List<Matriculant> matriculantList = DataAccessFactory.getMatriculants();

            for (int i = 0, n = matriculantList.size(); i < n; ++i) {
                List<String> row = new ArrayList<String>();
                for (int j = 0, m = headerList.size(); j < m; ++j) {
                    row.add("cell[" + i + "," + j + "]");
                }
                contentLists.add(row);
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
