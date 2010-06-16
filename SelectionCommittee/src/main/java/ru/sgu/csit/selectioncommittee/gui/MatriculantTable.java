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
    private static List<ColumnType> columnTypes = new ArrayList<ColumnType>();
    private static List<Boolean> columnVisible = new ArrayList<Boolean>();
    private static List<Integer> rowIndexes = new ArrayList<Integer>();
    private static List<Integer> viewRowIndexes = new ArrayList<Integer>();
    private static int specialityIndex = -1;

    private static boolean highlighting = true;
    private List<JCheckBoxMenuItem> headerPopupMenu = new ArrayList<JCheckBoxMenuItem>();
    private static int startSpecialityIndex;
    private static int startExaminesIndex;

    private static boolean showNotEntrance = true;
    private static Map<String, Boolean> showEntrances = null;
    //private static int countHide = 0;

    private static MatriculantTableModel matriculantTableModel = new MatriculantTableModel();

    public MatriculantTable() {
        super(matriculantTableModel);
        setDefaultRenderer(Object.class, new MatriculantTableCellRenderer());
        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        setColumnsWidth();
        setColumns();
        getTableHeader().setComponentPopupMenu(createColumnPopupMenu());
    }

    private static void setDefaultShowEntrances() {
        if (showEntrances == null) {
            showEntrances = new HashMap<String, Boolean>();
            for (Speciality speciality : DataAccessFactory.getSpecialities()) {
                showEntrances.put(speciality.getName(), Boolean.TRUE);
            }
        }
    }

    public static void recalculateViewRows() {
        //countHide = 0;
        viewRowIndexes.clear();
        for (Integer index = 0; index < rowIndexes.size(); ++index) {
            Matriculant matriculant = DataAccessFactory.getMatriculants().get(rowIndexes.get(index));

            if (!showNotEntrance && "".equals(matriculant.getEntranceSpecialityName())) {
                //++countHide;
            } else if (showEntrances.get(matriculant.getEntranceSpecialityName()) == Boolean.FALSE) {
                //++countHide;
            } else {
                viewRowIndexes.add(index);
            }
        }
    }

    public static void deleteFromViewIndex(int index) {
        if (specialityIndex > -1) {
            MatriculantTableModel.matriculantIndexes.get(specialityIndex).remove(index);
        }
        rowIndexes.remove(viewRowIndexes.get(index));
        viewRowIndexes.remove(index);
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
//        if (columns != null) {
//            for (int i = 0; i < columns.size(); ++i) {
//                if (!columns.get(i).isVisible()) {
//                    addColumn(i);
//                }
//            }
//            columns = new ArrayList<ColumnInfo>();
//        }
        if (columns == null || columns.isEmpty()) {
            columns = new ArrayList<ColumnInfo>();
            for (int i = 0; i < columnWidths.size(); ++i) {
                columns.add(new ColumnInfo(getColumnModel().getColumn(i), columnNames.get(i), columnTypes.get(i),
                        columnVisible.get(i)));
            }
        } else {
            for (int i = 0; i < columnWidths.size(); ++i) {
                columns.get(i).setColumn(getColumnModel().getColumn(i));
            }
        }
        /*for (ColumnInfo column : columns) {
                if (!column.isVisible()) {
                    removeColumn(column.getColumn());
                }
                System.out.println(column.getColumnName() + ": " + column.isVisible());
            }
            */
        for (int i = 0; i < columns.size(); ++i) {
            if (!columns.get(i).isVisible()) {
                removeColumn(columns.get(i).getColumn());
            }
            System.out.println(columns.get(i).getColumnName() + ": " + columns.get(i).isVisible());
        }
    }

     private void setColumnsWidth() {
        for (int i = 0; i < columnWidths.size(); ++i) {
            getColumnModel().getColumn(i).setPreferredWidth(columnWidths.get(i));
        }
    }

    public void removeColumn(int column) {
        removeColumn(columns.get(column).getColumn());
        columns.get(column).setVisible(false);
        columnVisible.set(column, Boolean.FALSE);
    }

    public void addColumn(int column) {
        addColumn(columns.get(column).getColumn());
        columns.get(column).setVisible(true);
        columnVisible.set(column, Boolean.TRUE);
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
        columnTypes.clear();
        columnVisible.clear();

        columnNames.add("№");
        columnWidths.add(25);
        columnTypes.add(ColumnType.NUMERIC);
        columnVisible.add(Boolean.TRUE);

        columnNames.add("ФИО");
        columnWidths.add(220);
        columnTypes.add(ColumnType.STRING);
        columnVisible.add(Boolean.TRUE);

        columnNames.add("Рег. №");
        columnWidths.add(70);
        columnTypes.add(ColumnType.NUMERIC);
        columnVisible.add(Boolean.TRUE);

        columnNames.add("Поступает");
        columnWidths.add(100);
        columnTypes.add(ColumnType.STRING);
        columnVisible.add(Boolean.TRUE);

        startSpecialityIndex = 3;
        for (int i = 0; i < DataAccessFactory.getSpecialities().size(); ++i) {
            columnNames.add(DataAccessFactory.getSpecialities().get(i).getName());
            columnWidths.add(60);
            columnTypes.add(ColumnType.NUMERIC);
            columnVisible.add(Boolean.FALSE);
        }

        columnNames.add("Балл");
        columnWidths.add(45);
        columnTypes.add(ColumnType.NUMERIC);
        columnVisible.add(Boolean.TRUE);

        columnNames.add("Специальность");
        columnWidths.add(95);
        columnTypes.add(ColumnType.STRING);
        columnVisible.add(Boolean.TRUE);

        columnNames.add("Зачислен на");
        columnWidths.add(95);
        columnTypes.add(ColumnType.STRING);
        columnVisible.add(Boolean.TRUE);

        startExaminesIndex = 2 + DataAccessFactory.getSpecialities().size() + startSpecialityIndex;
        for (int i = 0; i < DataAccessFactory.getExamines().size(); ++i) {
            columnNames.add(DataAccessFactory.getExamines().get(i).getName());
            columnWidths.add(60);
            columnTypes.add(ColumnType.NUMERIC);
            columnVisible.add(Boolean.TRUE);
        }

        columnNames.add("Телефон");
        columnWidths.add(150);
        columnTypes.add(ColumnType.STRING);
        columnVisible.add(Boolean.FALSE);

        columnNames.add("Дата");
        columnWidths.add(110);
        columnTypes.add(ColumnType.STRING);
        columnVisible.add(Boolean.TRUE);
    }

    public void refresh() {
        //matriculantTableModel.restoreIndexes();
        matriculantTableModel.fireTableStructureChanged();
        setColumnsWidth();
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

    public static boolean isShowNotEntrance() {
        return showNotEntrance;
    }

    public static void setShowNotEntrance(boolean showNotEntrance) {
        MatriculantTable.showNotEntrance = showNotEntrance;
    }

    public static Map<String, Boolean> getShowEntrances() {
        return showEntrances;
    }

    public static void setShowEntrances(Map<String, Boolean> showEntrances) {
        MatriculantTable.showEntrances = showEntrances;
    }

    public static Boolean isShowEntrance(String key) {
        return showEntrances.get(key);
    }

    public static void setShowEntrance(String key, Boolean value) {
        MatriculantTable.showEntrances.put(key, value);
    }

    public void sort(final List<Integer> columnIndexes) {
        if (columnIndexes != null && columnIndexes.size() > 0) {
            matriculantTableModel.sort(columnIndexes);
        }
    }

    public void sortBy(Speciality speciality) {
        matriculantTableModel.restoreIndexes();
        if (speciality != null) {
            int index = findSpecialityIndex(speciality);

            matriculantTableModel.sortBy(speciality, index);
            matriculantTableModel.setRowIndexesFromMatriculantIndexesBy(index);
            recalculateViewRows();
        }
    }

    public void apportionBySpec(List<Integer> counts) {
        matriculantTableModel.apportionBySpec(counts);
    }

    public static void resetRowIndexes() {
        specialityIndex = -1;
        restoreRowIndexes();
    }

    public static void restoreRowIndexes() {
        rowIndexes.clear();
        if (specialityIndex > -1) {
            matriculantTableModel.sortBy(DataAccessFactory.getSpecialities().get(specialityIndex), specialityIndex);
            matriculantTableModel.setRowIndexesFromMatriculantIndexesBy(specialityIndex);
        } else {
            specialityIndex = -1;
            for (int i = 0; i < DataAccessFactory.getMatriculants().size(); ++i) {
                rowIndexes.add(i);
            }
        }
        recalculateViewRows();
    }

    public int convertViewRowIndexToMatriculants(int selectedIndex) {
        return /*rowIndexes*/rowIndexes.get(viewRowIndexes.get(this.convertRowIndexToModel(selectedIndex)));
    }

    private static class MatriculantTableModel extends AbstractTableModel {
        private static DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd hh:mm");
        static List<List<Integer>> matriculantIndexes = new ArrayList<List<Integer>>();

        public MatriculantTableModel() {
            MatriculantTable.setDefaultShowEntrances();
            restoreIndexes();
            MatriculantTable.regenerateColumnData();
        }

        public void restoreIndexes() {
            restoreMatriculantIndexes();
            MatriculantTable.restoreRowIndexes();
        }

        public void restoreMatriculantIndexes() {
            matriculantIndexes.clear();
            for (int i = 0; i < DataAccessFactory.getSpecialities().size(); ++i) {
                matriculantIndexes.add(new ArrayList<Integer>());
                for (int j = 0; j < DataAccessFactory.getMatriculants().size(); ++j) {
                    matriculantIndexes.get(i).add(j);
                }
            }
        }

        public void sort(final List<Integer> columnIndexes) {
            Collections.sort(viewRowIndexes, new Comparator<Integer>() {
                public int compare(Integer o1, Integer o2) {
                    return compareByColumnsPriority(viewRowIndexes.indexOf(o1), viewRowIndexes.indexOf(o2), 0);
                }

                private int compareByColumnsPriority(Integer firstIndex, Integer secondIndex, int columnIndex) {
                    if (columnIndex == columnIndexes.size()) {
                        return 0;
                    }

                    int cmp;
                    String arg1 = getValueAt(firstIndex, columnIndexes.get(columnIndex)).toString();
                    String arg2 = getValueAt(secondIndex, columnIndexes.get(columnIndex)).toString();

                    switch (columnTypes.get(columnIndexes.get(columnIndex))) {
                        case NUMERIC:
                            cmp = Integer.valueOf(arg1).compareTo(Integer.valueOf(arg2));
                            if (cmp == 0) {
                                return compareByColumnsPriority(firstIndex, secondIndex, columnIndex + 1);
                            } else {
                                return cmp;
                            }

                        case STRING:
                        default:
                            cmp = arg1.compareTo(arg2);
                            if (cmp == 0) {
                                return compareByColumnsPriority(firstIndex, secondIndex, columnIndex + 1);
                            } else {
                                return cmp;
                            }
                    }
                }
            });
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

                        if (firstBalls < secondBalls) { //|| (firstBalls == null && secondBalls > 0)) {
                            return 1;
                        } else if (firstBalls > secondBalls) {
                            return -1;
                        } else {
                            if (firstBalls > 0) {
                                return compareByExamsPriority(firstMatriculant, secondMatriculant, speciality.getExams(), 0);
                            } else {
                                return 0;
                            }
                        }
                    }
                }

                private int compareByExamsPriority(Matriculant first, Matriculant second, List<ReceiptExamine> exams, int level) {
                    if (level == exams.size()) {
                        return 0;
                    }
                    if (first.getBalls().get(exams.get(level).getName()) < second.getBalls().get(exams.get(level).getName())) {
                        return 1;
                    } else if (first.getBalls().get(exams.get(level).getName()) > second.getBalls().get(exams.get(level).getName())) {
                        return -1;
                    } else {
                        return compareByExamsPriority(first, second, exams, level + 1);
                    }
                }
            });
        }

        public void setRowIndexesFromMatriculantIndexesBy(int index) {
            rowIndexes = matriculantIndexes.get(index);
            specialityIndex = index;
        }

        public void apportionBySpec(List<Integer> counts) {
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
            //DataAccessFactory.reloadMatriculants();

            boolean theContinue = true;
            int currentSpecPriority = 1;

            while (currentSpecPriority <= DataAccessFactory.getSpecialities().size()) {
                theContinue = false;

                for (int specIndex = 0; specIndex < DataAccessFactory.getSpecialities().size(); ++specIndex) {
                    Speciality speciality = DataAccessFactory.getSpecialities().get(specIndex);
                    int count = counts.get(specIndex);
                    Iterator<Integer> iter = matriculantIndexes.get(specIndex).iterator();
                    boolean updatedMatriculants = false;

                    while (count > 0 && iter.hasNext()) {
                        Integer element = iter.next();
                        Matriculant matriculant = DataAccessFactory.getMatriculants().get(element);

                        if ("".equals(matriculant.getEntranceSpecialityName())) {
                                if (speciality.getName().equals(matriculant.getSpeciality().get(currentSpecPriority))) {
                                    matriculant.setEntranceSpecialityName(speciality.getName());
                                    DataAccessFactory.getMatriculantDAO().update(matriculant);
                                    updatedMatriculants = true;
                                } else {
                                    if (matriculant.getSpeciality().containsValue(speciality.getName())) {
                                        --count;
                                    }
                                }
                        }
                        if (speciality.getName().equals(matriculant.getEntranceSpecialityName())) {
                            --count;
                        }
                    }
                    if (updatedMatriculants) {
                        //DataAccessFactory.reloadMatriculants();
                        theContinue = true;
                        if (currentSpecPriority != 1) {
                            currentSpecPriority = 1;
                            break;
                        }
                    }
                }
                if (!theContinue) {
                    currentSpecPriority++;
                }
            }
            DataAccessFactory.reloadMatriculants();

            System.out.println("//=== Apportion finished!");
        }

        @Override
        public int getRowCount() {
            return viewRowIndexes.size();//rowIndexes.size() - MatriculantTable.countHide;
        }

        @Override
        public int getColumnCount() {
            return columnNames.size();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Matriculant matriculant = DataAccessFactory.getMatriculants().get(
                    rowIndexes.get(viewRowIndexes.get(rowIndex)));

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
                String value = matriculant.getSpeciality().get(1);

                if (specialityIndex > -1) {
                    value += " (" + DataAccessFactory.getSpecialities().get(specialityIndex).getName() + ")";
                }
                return value;
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
            Matriculant matriculant = DataAccessFactory.getMatriculants().get(
                    rowIndexes.get(viewRowIndexes.get(table.convertRowIndexToModel(row))));

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
        private ColumnType columnType;
        private boolean visible = true;

        public ColumnInfo() {
        }

        public ColumnInfo(TableColumn column, String name, ColumnType type, boolean visible) {
            this.column = column;
            this.columnName = name;
            this.columnType = type;
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

    private enum ColumnType {
        NUMERIC, STRING
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
                    //refresh();
                    repaint();

                    return;
                }
            }
        }
    }
}
