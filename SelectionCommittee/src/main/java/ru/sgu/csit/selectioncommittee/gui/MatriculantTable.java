package ru.sgu.csit.selectioncommittee.gui;

import ru.sgu.csit.selectioncommittee.common.Matriculant;
import ru.sgu.csit.selectioncommittee.common.ReceiptExamine;
import ru.sgu.csit.selectioncommittee.common.Speciality;
import ru.sgu.csit.selectioncommittee.factory.DataAccessFactory;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.tSHOWCOLUMN_DESCRIPTION;

/**
 * Date: May 5, 2010
 * Time: 6:41:22 PM
 *
 * @author xx & hd
 */
public class MatriculantTable extends JTable {
    private List<ColumnInfo> columns = new ArrayList<ColumnInfo>();
    private static List<String> columnNames = new ArrayList<String>();
    private static List<Integer> columnWidths = new ArrayList<Integer>();
    private static List<Boolean> columnVisible = new ArrayList<Boolean>();
    private static List<Integer> rowIndexes = new ArrayList<Integer>();
    private static int specialityIndex = -1;

    private static boolean highlighting = true;
    private List<JCheckBoxMenuItem> headerPopupMenu = new ArrayList<JCheckBoxMenuItem>();
    private static int startSpecialityIndex;
    private static int startExaminesIndex;

    private static MatriculantTableModel matriculantTableModel = new MatriculantTableModel();

    public MatriculantTable() {
        super(matriculantTableModel);
        setDefaultRenderer(Object.class, new MatriculantTableCellRenderer());
        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        setColumns();
        getTableHeader().setComponentPopupMenu(createColumnPopupMenu());
    }

    private JPopupMenu createColumnPopupMenu() {
        JPopupMenu jPopupMenu = new JPopupMenu();

        for (ColumnInfo column : columns) {
            JCheckBoxMenuItem columnMenuItem = new JCheckBoxMenuItem(new ShowColumnAction(column.getColumnName()));

            columnMenuItem.setSelected(column.isVisible());
            jPopupMenu.add(columnMenuItem);
            headerPopupMenu.add(columnMenuItem);
        }
        return jPopupMenu;
    }

    private void setColumns() {
        if (columns != null) {
            columns = new ArrayList<ColumnInfo>();
        }
        for (int i = 0; i < columnWidths.size(); ++i) {
            getColumnModel().getColumn(i).setPreferredWidth(columnWidths.get(i));
            columns.add(new ColumnInfo(getColumnModel().getColumn(i), columnNames.get(i),
                    columnVisible.get(i)));
        }
        for (ColumnInfo column : columns) {
            if (!column.isVisible()) {
                removeColumn(column.getColumn());
            }
        }
    }

    public void removeColumn(int column) {
        removeColumn(columns.get(column).getColumn());
        columns.get(column).setVisible(false);
    }

    public void addColumn(int column) {
        addColumn(columns.get(column).getColumn());
        columns.get(column).setVisible(true);
        moveColumn(column);
    }

    public void moveColumn(int column) {
        int sourceIndex = convertColumnIndexToView(column);
        int targetIndex = 0;

        for (int i = 0; i < column; ++i) {
            if (columns.get(i).isVisible()) {
                targetIndex++;
            }
        }
        moveColumn(sourceIndex, targetIndex);
    }

    public void hideGeneratedColumns() {
        for (int i = startSpecialityIndex; i < startSpecialityIndex + DataAccessFactory.getSpecialities().size(); ++i) {
            removeColumn(i);
        }
        for (int i = startExaminesIndex; i < startExaminesIndex + DataAccessFactory.getExamines().size(); ++i) {
            removeColumn(i);
        }
        synchronizePopupWithColumns();
    }

    public void synchronizePopupWithColumns() {
        for (int i = 0; i < columns.size(); ++i) {
            headerPopupMenu.get(i).setSelected(columns.get(i).isVisible());
        }
    }

    private static void regenerateColumnData() {
        columnNames.clear();
        columnWidths.clear();
        columnVisible.clear();

        columnNames.add("№");
        columnWidths.add(25);
        columnVisible.add(Boolean.TRUE);

        columnNames.add("ФИО");
        columnWidths.add(220);
        columnVisible.add(Boolean.TRUE);

        columnNames.add("Рег. №");
        columnWidths.add(70);
        columnVisible.add(Boolean.TRUE);

        columnNames.add("Поступает");
        columnWidths.add(100);
        columnVisible.add(Boolean.FALSE);

        startSpecialityIndex = 3;
        for (int i = 0; i < DataAccessFactory.getSpecialities().size(); ++i) {
            columnNames.add(DataAccessFactory.getSpecialities().get(i).getName());
            columnWidths.add(60);
            columnVisible.add(Boolean.FALSE);
        }

        columnNames.add("Балл");
        columnWidths.add(45);
        columnVisible.add(Boolean.TRUE);

        columnNames.add("Специальность");
        columnWidths.add(95);
        columnVisible.add(Boolean.TRUE);

        columnNames.add("Зачислен на");
        columnWidths.add(95);
        columnVisible.add(Boolean.TRUE);

        startExaminesIndex = 2 + DataAccessFactory.getSpecialities().size() + startSpecialityIndex;
        for (int i = 0; i < DataAccessFactory.getExamines().size(); ++i) {
            columnNames.add(DataAccessFactory.getExamines().get(i).getName());
            columnWidths.add(60);
            columnVisible.add(Boolean.TRUE);
        }

        columnNames.add("Телефон");
        columnWidths.add(150);
        columnVisible.add(Boolean.FALSE);

        columnNames.add("Дата");
        columnWidths.add(110);
        columnVisible.add(Boolean.TRUE);
    }

    public void refresh() {
        matriculantTableModel.fireTableStructureChanged();
        setColumns();
    }

    public static boolean isHighlighting() {
        return highlighting;
    }

    public static void setHighlighting(boolean highlighting) {
        MatriculantTable.highlighting = highlighting;
    }

    public List<ColumnInfo> getColumns() {
        return columns;
    }

    private int findSpecialityIndex(final Speciality speciality) {
        int index = -1;

        if (speciality != null) {
            for (int i = 0; i < DataAccessFactory.getSpecialities().size(); ++i) {
                if (DataAccessFactory.getSpecialities().get(i).getName().equals(speciality.getName())) {
                    index = i;
                }
            }
        }
        return index;
    }

    public void sortBy(Speciality speciality) {
        matriculantTableModel.restoreIndexes();
        if (speciality != null) {
            int index = findSpecialityIndex(speciality);

            matriculantTableModel.sortBy(speciality, index);
            matriculantTableModel.setRowIndexesFromMatriculantIndexesBy(index);
        }
    }

    public void ApportionBySpec(List<Integer> counts) {
        matriculantTableModel.ApportionBySpec(counts);
    }

    public static void restoreRowIndexes() {
        rowIndexes.clear();
        specialityIndex = -1;
        for (int i = 0; i < DataAccessFactory.getMatriculants().size(); ++i) {
            rowIndexes.add(i);
        }
    }

    public int convertViewRowIndexToMatriculants(int selectedIndex) {
        return rowIndexes.get(this.convertRowIndexToModel(selectedIndex));
    }

    private static class MatriculantTableModel extends AbstractTableModel {
        private static DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd hh:mm");
        private List<List<Integer>> matriculantIndexes = new ArrayList<List<Integer>>();

        {
            restoreIndexes();
            MatriculantTable.regenerateColumnData();
        }

        public void restoreIndexes() {
            MatriculantTable.restoreRowIndexes();
            restoreMatriculantIndexes();
        }

        public void restoreMatriculantIndexes() {
            matriculantIndexes.clear();
            for (int i = 0; i < DataAccessFactory.getSpecialities().size(); ++i) {
                matriculantIndexes.add(new LinkedList<Integer>());
                for (int j = 0; j < DataAccessFactory.getMatriculants().size(); ++j) {
                    matriculantIndexes.get(i).add(j);
                }
            }
        }

        public void sortBy(final Speciality speciality, int index) {
            Collections.sort(matriculantIndexes.get(index), new Comparator<Integer>() {
                private List<Matriculant> matriculants = DataAccessFactory.getMatriculants();

                public int compare(Integer o1, Integer o2) {
                    Matriculant firstMatriculant = matriculants.get(o1);
                    Matriculant secondMatriculant = matriculants.get(o2);

                    if (firstMatriculant.isNoExamine()) {
                        if (secondMatriculant.isNoExamine()) {
                            return 0;//firstMatriculant.getName().compareTo(secondMatriculant.getName());
                        } else {
                            return -1;
                        }
                    } else if (secondMatriculant.isNoExamine()) {
                        return 1;
                    } else {
                        Integer firstBalls = firstMatriculant.calculateTotalBallsForSpeciality(speciality.getName());
                        Integer secondBalls = secondMatriculant.calculateTotalBallsForSpeciality(speciality.getName());
                        System.out.println(firstBalls + ", " + secondBalls);
                        if (firstBalls < secondBalls) { //|| (firstBalls == null && secondBalls > 0)) {
                            return 1;
                        } else if (firstBalls > secondBalls) {
                            return -1;
                        } else {
                            if (firstBalls > 0) {
                                return sortByExamsPriority(firstMatriculant, secondMatriculant, speciality.getExams(), 0);
                            } else {
                                return 0;
                            }
                        }
                    }
                }

                private int sortByExamsPriority(Matriculant first, Matriculant second, List<ReceiptExamine> exams, int level) {
                    if (level == exams.size()) {
                        return 0;
                    }
                    if (first.getBalls().get(exams.get(level).getName()) < second.getBalls().get(exams.get(level).getName())) {
                        return 1;
                    } else if (first.getBalls().get(exams.get(level).getName()) > second.getBalls().get(exams.get(level).getName())) {
                        return -1;
                    } else {
                        return sortByExamsPriority(first, second, exams, level + 1);
                    }
                }
            });
        }

        public void setRowIndexesFromMatriculantIndexesBy(int index) {
            rowIndexes = matriculantIndexes.get(index);
            specialityIndex = index;
        }

        public void ApportionBySpec(List<Integer> counts) {
            System.out.println("//=== Start apportion.");
            restoreMatriculantIndexes();
            for (int i = 0; i < DataAccessFactory.getSpecialities().size(); ++i) {
                sortBy(DataAccessFactory.getSpecialities().get(i), i);
            }
            for (Matriculant matriculant : DataAccessFactory.getMatriculants()) {
                if (!"".equals(matriculant.getEntranceSpecialityName())) {
                    matriculant.setEntranceSpecialityName("");
                    DataAccessFactory.getMatriculantDAO().update(matriculant);
                }
            }
            DataAccessFactory.reloadMatriculants();

            boolean theContinue = true;
            int curSpecPriority = 1;

            while (curSpecPriority <= DataAccessFactory.getSpecialities().size()) {
                theContinue = false;

                for (int specIndex = 0; specIndex < DataAccessFactory.getSpecialities().size(); ++specIndex) {
                    Speciality speciality = DataAccessFactory.getSpecialities().get(specIndex);
                    int count = counts.get(specIndex);
                    Iterator<Integer> iter = matriculantIndexes.get(specIndex).iterator();
                    boolean updatedMatriculants = false;

                    while (count > 0 && iter.hasNext()) {
                        Integer element = iter.next();
                        Matriculant matriculant = DataAccessFactory.getMatriculants().get(element);
                        boolean updated = false;

                        if ("".equals(matriculant.getEntranceSpecialityName())) {
                                if (speciality.getName().equals(matriculant.getSpeciality().get(curSpecPriority))) {
                                    matriculant.setEntranceSpecialityName(speciality.getName());
                                    updated = true;
                                } else {
                                    if (matriculant.getSpeciality().containsValue(speciality.getName())) {
                                        --count;
                                    }
                                }
                        }
                        if (speciality.getName().equals(matriculant.getEntranceSpecialityName())) {
                            --count;
                        }
                        if (updated) {
                            updatedMatriculants = true;
                            DataAccessFactory.getMatriculantDAO().update(matriculant);
                        }
                    }
                    if (updatedMatriculants) {
                        //DataAccessFactory.reloadMatriculants();
                        theContinue = true;
                    }
                }
                if (!theContinue) {
                    curSpecPriority++;
                }
            }
            DataAccessFactory.reloadMatriculants();
            System.out.println("//=== Apportion finished!");
        }

        @Override
        public int getRowCount() {
            return rowIndexes.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.size();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Matriculant matriculant = DataAccessFactory.getMatriculants().get(rowIndexes.get(rowIndex));

            if (columnIndex == 0) {
                return rowIndex + 1;
            }
            if (columnIndex == 1) {
                return matriculant.getName();
            }
            if (columnIndex == 2) {
                return matriculant.getReceiptNumber();
            }

            if (columnIndex == 3
                    && matriculant.getEntranceCategory() != null) {
                switch (matriculant.getEntranceCategory()) {
                    case EXAMINE:
                        return "По экзаменам\n";
                    case NO_EXAMINE:
                        return "Без экзаменов";
                    case OUT_EXAMINE_OTHER:
                        return "Вне конкурса";
                    case OUT_EXAMINE_ORPHAN:
                        return "Вне конкурса";
                    case OUT_EXAMINE_INVALID:
                        return "Вне конкурса";
                }
            }

            int index = columnIndex - 4;

            if (index >= 0 && index < DataAccessFactory.getSpecialities().size()) {
                for (Map.Entry<Integer, String> entry : matriculant.getSpeciality().entrySet()) {
                    if (entry.getValue().equals(DataAccessFactory.getSpecialities().get(index).getName())) {
                        return entry.getKey().toString();
                    }
                }
            }

            index = DataAccessFactory.getSpecialities().size() + 4;
            if (columnIndex == index) {
                String mainSpecName = (specialityIndex > -1
                        ? DataAccessFactory.getSpecialities().get(specialityIndex).getName()
                        : matriculant.getSpeciality().get(1));
                Integer result = matriculant.calculateTotalBallsForSpeciality(mainSpecName);

                if (result != null) {
                    return String.valueOf(result);
                }
            }

            index++;
            if (columnIndex == index) {
                return (specialityIndex > -1
                        ? DataAccessFactory.getSpecialities().get(specialityIndex).getName()
                        : matriculant.getSpeciality().get(1));
            }

            index++;
            if (columnIndex == index) {
                return matriculant.getEntranceSpecialityName();
            }

            index = columnIndex - (index + 1);
            if (index >= 0 && index < DataAccessFactory.getExamines().size()) {
                Integer value = matriculant.getBalls().get(DataAccessFactory.getExamines().get(index).getName());

                if (value != null) {
                    return value.toString();
                }
            }

            index = DataAccessFactory.getExamines().size() + DataAccessFactory.getSpecialities().size() + 7;
            if (columnIndex == index) {
                return matriculant.getPhoneNumbers();
            }

            if (columnIndex == index + 1) {
                return dateFormat.format(matriculant.getFilingDate());
            }

            return "";
        }

        @Override
        public String getColumnName(int column) {
            return columnNames.get(column);
        }
    }

    private static class MatriculantTableCellRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table,
                                                       Object value,
                                                       boolean isSelected,
                                                       boolean hasFocus,
                                                       int row,
                                                       int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            Matriculant matriculant = DataAccessFactory.getMatriculants().get(rowIndexes.get(table.convertRowIndexToModel(row)));

            if (table.convertColumnIndexToModel(column) == DataAccessFactory.getSpecialities().size() + 4) {
                cell.setFont(cell.getFont().deriveFont(Font.BOLD));
            }

            if (MatriculantTable.isHighlighting()) {
                if (!isSelected) {
                    if (!matriculant.completeAllDocuments()) {
                        if (matriculant.getDocuments() != null && matriculant.getDocuments().isOriginalAttestat()) {
                            cell.setBackground(new Color(240, 230, 190));
                        } else {
                            cell.setBackground(new Color(210, 210, 245));
                        }
//                        cell.setFont(cell.getFont().deriveFont(Font.PLAIN));
///                       cell.setBackground(new Color(150, 150, 150));//Color(180, 250, 200));
///                       cell.setForeground(new Color(250, 250, 250));
                    } else if (matriculant.getDocuments() != null && matriculant.getDocuments().isTookDocuments()) {
                        cell.setBackground(new Color(255, 210, 210));
                    } else {
///                    cell.setBackground(Color.WHITE);
///                    cell.setForeground(Color.BLACK);
//                    cell.setFont(cell.getFont().deriveFont(Font.BOLD));
                        cell.setBackground(new Color(210, 245, 200));
                    }
                } else {
                    if (table.convertColumnIndexToModel(column) == 1) {
                        cell.setFont(cell.getFont().deriveFont(Font.BOLD));
                    }
                    if (!matriculant.completeAllDocuments()) {
                        if (matriculant.getDocuments() != null && matriculant.getDocuments().isOriginalAttestat()) {
                            cell.setBackground(new Color(225, 215, 165));
                        } else {
                            cell.setBackground(new Color(195, 195, 245));
                        }
                    } else if (matriculant.getDocuments() != null && matriculant.getDocuments().isTookDocuments()) {
                        cell.setBackground(new Color(245, 195, 195));
                    } else {
                        cell.setBackground(new Color(180, 240, 170));
                    }
                }
            } else {
                if (!isSelected) {
                    cell.setBackground(Color.WHITE);
                    cell.setForeground(Color.BLACK);
                }
            }
            return cell;
        }
    }

    public static class ColumnInfo {
        private TableColumn column;
        private String columnName;
        private boolean visible = true;

        public ColumnInfo() {
        }

        public ColumnInfo(TableColumn column, String name, boolean visible) {
            this.column = column;
            this.columnName = name;
            this.visible = visible;
        }

        public TableColumn getColumn() {
            return column;
        }

        public void setColumn(TableColumn column) {
            this.column = column;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public boolean isVisible() {
            return visible;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }
    }

    private class ShowColumnAction extends AbstractAction {
        private ShowColumnAction(String name) {
            putValue(Action.NAME, name);
            putValue(Action.SHORT_DESCRIPTION, tSHOWCOLUMN_DESCRIPTION);
        }

        public void actionPerformed(ActionEvent e) {
            JCheckBoxMenuItem columnMenuItem = (JCheckBoxMenuItem) e.getSource();

            for (int i = 0; i < columns.size(); ++i) {
                ColumnInfo column = columns.get(i);

                if (column.getColumnName().equals(columnMenuItem.getText())) {
                    if (columnMenuItem.isSelected()) {
                        addColumn(i);
                    } else {
                        removeColumn(i);
                    }
                    repaint();

                    return;
                }
            }
        }
    }
}
