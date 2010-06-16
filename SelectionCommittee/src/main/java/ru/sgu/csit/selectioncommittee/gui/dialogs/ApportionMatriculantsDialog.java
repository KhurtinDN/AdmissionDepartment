package ru.sgu.csit.selectioncommittee.gui.dialogs;

import ru.sgu.csit.selectioncommittee.common.EntranceCategory;
import ru.sgu.csit.selectioncommittee.common.Matriculant;
import ru.sgu.csit.selectioncommittee.common.Speciality;
import ru.sgu.csit.selectioncommittee.factory.DataAccessFactory;
import ru.sgu.csit.selectioncommittee.gui.MatriculantTable;
import ru.sgu.csit.selectioncommittee.gui.dialogs.panels.CapacityOnSpecialitiesPanel;
import ru.sgu.csit.selectioncommittee.gui.utils.GBConstraints;
import ru.sgu.csit.selectioncommittee.service.ArgumentNotExcelFileException;
import ru.sgu.csit.selectioncommittee.service.ExportToExcel;
import ru.sgu.csit.selectioncommittee.service.WritingException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

import static ru.sgu.csit.selectioncommittee.gui.utils.MessageUtil.showErrorMessage;
import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.*;

/**
 * Date: Jun 15, 2010
 * Time: 9:01:58 PM
 *
 * @author xx & hd
 */
public class ApportionMatriculantsDialog extends JDialog {
    private JCheckBox saveInXlsCheckBox = new JCheckBox("Сохранить в excel файл");
    private Action apportionAction = new ApportionAction();
    private Action closeAction = new CloseAction();

    private MatriculantTable matriculantTable = null;

    private CapacityOnSpecialitiesPanel capacityOnSpecialitiesPanel;

    public ApportionMatriculantsDialog(JFrame owner, MatriculantTable matriculantTable) {
        super(owner, "Распределение абитуриентов по специальностям", true);
        this.matriculantTable = matriculantTable;
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
        buttonPanel.add(saveInXlsCheckBox, new GBConstraints(0, 0));
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
            List<Integer> counts = new ArrayList<Integer>();

            List<Speciality> specialityList = DataAccessFactory.getSpecialities();

            Set<Map.Entry<String, Integer>>
                    entrySet = capacityOnSpecialitiesPanel.getCapacityOnSpecialities().entrySet();

            for (Speciality speciality : specialityList) {
                for (Map.Entry<String, Integer> entry : entrySet) {
                    if (entry.getKey().equals(speciality.getName())) {
                        counts.add(entry.getValue());
                        break;
                    }
                }
            }

            matriculantTable.apportionBySpec(counts);
            matriculantTable.repaint();

            if (saveInXlsCheckBox.isSelected()) {
                JFileChooser fileChooser = new ExcelFileChooser();

                if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();

                    List<String> headerList = createHeaderList();
                    Map<String, List<List<String>>> contentMap = createContentMap();

                    ExportToExcel exportToExcel = new ExportToExcel();
                    try {
                        exportToExcel.writeSpecialities(file, "Абитуриенты", headerList, contentMap);
                    } catch (ArgumentNotExcelFileException e1) {
                        showErrorMessage("Файл должен иметь расширение *.xls или *.xlsx");
                    } catch (FileNotFoundException e1) {
                        showErrorMessage("Файл " + file.getPath() + " не найден.");
                    } catch (WritingException e1) {
                        showErrorMessage("При экспорте произошла ошибка записи.");
                    }
                }
            }

            ApportionMatriculantsDialog.this.setVisible(false);
        }

        private Map<String, List<List<String>>> createContentMap() {
            Map<String, List<List<String>>> contentMap = new LinkedHashMap<String, List<List<String>>>();

            for (Speciality speciality : DataAccessFactory.getSpecialities()) {
                String specialityName = speciality.getName();
                List<List<String>> contentLists = createContentLists(specialityName);
                if (contentLists.size() > 0) {
                    contentMap.put(specialityName, contentLists);
                }
            }

            return contentMap;
        }

        private List<List<String>> createContentLists(String specialityName) {
            List<List<String>> contentLists = new ArrayList<List<String>>();

            List<MatriculantRecord> matriculants = new ArrayList<MatriculantRecord>();
            for (Matriculant matriculant : DataAccessFactory.getMatriculants()) {
                if (specialityName.equals(matriculant.getEntranceSpecialityName())) {
                    matriculants.add(new MatriculantRecord(matriculant));
                }
            }
            Collections.sort(matriculants);

            for (int i = 0, n = matriculants.size(); i < n; ++i) {
                List<String> matriculantRow = new ArrayList<String>();
                matriculantRow.addAll(matriculants.get(i).getDataList(i + 1));
                contentLists.add(matriculantRow);
            }

            return contentLists;
        }

        private List<String> createHeaderList() {
            return Arrays.asList("№", "ФИО", "Тип поступления", "суммарный балл");
        }

        private class MatriculantRecord implements Comparable<MatriculantRecord> {
            private String name;
            private EntranceCategory entranceCategory;
            private Integer genericMark;

            public MatriculantRecord(Matriculant matriculant) {
                this.name = matriculant.getName();
                this.entranceCategory = matriculant.getEntranceCategory();
                this.genericMark = matriculant.calculateTotalBallsForSpeciality(matriculant.getEntranceSpecialityName());
            }

            private List<String> getDataList(Integer number) {
                return new ArrayList<String>(Arrays.asList(number.toString(), name,
                        getEntranceCategoryName(entranceCategory), genericMark.toString()));
            }

            private String getEntranceCategoryName(EntranceCategory entranceCategory) {
                switch (entranceCategory) {
                    case OUT_EXAMINE_ORPHAN:
                        return "Без экзаменов (сирота)";
                    case OUT_EXAMINE_INVALID:
                        return "Без экзаменов (инвалид)";
                    case OUT_EXAMINE_OTHER:
                        return "Без экзаменов (другое)";
                    case EXAMINE:
                        return "По экзаменам";
                    case NO_EXAMINE:
                        return "Без экзаменов";
                    default:
                        return "";
                }
            }

            @Override
            public int compareTo(MatriculantRecord other) {
                return this.name.compareTo(other.name);
            }
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
