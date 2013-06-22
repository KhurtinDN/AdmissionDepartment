package ru.sgu.csit.admissiondepartment.gui;

import ru.sgu.csit.admissiondepartment.common.Matriculant;
import ru.sgu.csit.admissiondepartment.common.ReceiptExamine;
import ru.sgu.csit.admissiondepartment.common.Speciality;
import ru.sgu.csit.admissiondepartment.factory.DataAccessFactory;
import ru.sgu.csit.admissiondepartment.gui.actions.ShowColumnAction;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Date: May 5, 2010
 * Time: 6:41:22 PM
 *
 * @author xx & hd
 */
@org.springframework.stereotype.Component("mainTable")
public class MatriculantTable extends JTable {
    private static List<String> columnNames = new ArrayList<String>();
    private List<ColumnInfo> columns = new ArrayList<ColumnInfo>();
    private static List<Integer> rowIndexes = new ArrayList<Integer>();
    private static List<Integer> viewRowIndexes = new ArrayList<Integer>();
    private static int specialityIndex = -1;

    private static boolean highlighting = true;

    private static boolean showNotEntrance = true;
    private static Map<String, Boolean> showEntrances = null;

    private static MatriculantTableModel matriculantTableModel = new MatriculantTableModel();

    public static void recreateColumnNames() {
        columnNames.clear();

        columnNames.add("№");
        columnNames.add("ФИО");
        columnNames.add("Рег. №");
        columnNames.add("Поступает");

//        int startSpecialityIndex = 3;
        for (int i = 0; i < DataAccessFactory.getSpecialities().size(); ++i) {
            columnNames.add(DataAccessFactory.getSpecialities().get(i).getName());
        }

        columnNames.add("Балл");
        columnNames.add("Специальность");
        columnNames.add("Зачислен на");

//        int startExaminesIndex = 2 + DataAccessFactory.getSpecialities().size() + startSpecialityIndex;
        for (int i = 0; i < DataAccessFactory.getExamines().size(); ++i) {
            columnNames.add(DataAccessFactory.getExamines().get(i).getName());
        }

        columnNames.add("Телефон");
        columnNames.add("Дата");
    }

    public MatriculantTable() {
        super(matriculantTableModel);
        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        reload();
        setDefaultRenderer(Object.class, new MatriculantTableCellRenderer());
    }

    private static void setDefaultShowEntrances() {
        showEntrances = new HashMap<String, Boolean>();
        for (Speciality speciality : DataAccessFactory.getSpecialities()) {
            showEntrances.put(speciality.getName(), Boolean.TRUE);
        }
    }

    public static void recalculateViewRows() {
        viewRowIndexes.clear();
        for (Integer index = 0; index < rowIndexes.size(); ++index) {
            Matriculant matriculant = DataAccessFactory.getMatriculants().get(rowIndexes.get(index));

            if (!showNotEntrance && (matriculant.getEntranceSpecialityName() == null
                    || "".equals(matriculant.getEntranceSpecialityName()))) {
                //++countHide;
            } else if (showEntrances.get(matriculant.getEntranceSpecialityName()) == Boolean.FALSE) {
                //++countHide;
            } else {
                viewRowIndexes.add(index);
            }
        }
    }

    public void deleteFromViewIndex(int index) {
        /*if (specialityIndex > -1) {
            MatriculantTableModel.matriculantIndexes.get(specialityIndex).remove(
                    MatriculantTableModel.matriculantIndexes.get(specialityIndex).size() - 1); //viewRowIndexes.get(index));
            MatriculantTableModel.setRowIndexesFromMatriculantIndexesBy(specialityIndex);
        } else {
            rowIndexes.remove(rowIndexes.size() - 1);//viewRowIndexes.get(index));
        }
        restoreRowIndexes();*/
        matriculantTableModel.restoreIndexes();
    }

    private JPopupMenu createColumnPopupMenu(String excludeItemName) {
        JPopupMenu jPopupMenu = new JPopupMenu();

        //jPopupMenu.add(new SelectColumnDialogAction(this));
        for (ColumnInfo column : columns) {
            JCheckBoxMenuItem columnMenuItem = new JCheckBoxMenuItem(
                    new ShowColumnAction(column.getColumnName(), this));

            columnMenuItem.setSelected(column.isVisible());
            if (column.getColumnName().equals(excludeItemName)) {
                columnMenuItem.setEnabled(false);
            }
            jPopupMenu.add(columnMenuItem);
        }
        return jPopupMenu;
    }

    public TableColumnModel recreateColumnModel() {
        DefaultTableColumnModel columnModel = new DefaultTableColumnModel();
//        TableCellRenderer cellRenderer = new MatriculantTableCellRenderer();

        for (int i = 0; i < columns.size(); ++i) {
            //if (columns.get(i).isVisible()) {
            TableColumn column = new TableColumn();

            column.setModelIndex(i);
            column.setHeaderValue(columns.get(i).getColumnName());
            column.setPreferredWidth(columns.get(i).getColumnWidth());
            //column.setCellRenderer(cellRenderer);

            columnModel.addColumn(column);
            columns.get(i).setColumn(column);
            //}
        }
        return columnModel;
    }

    public List<ColumnInfo> getColumns() {
        return columns;
    }

    private void regenerateColumnData() {
        recreateColumnNames();
        columns.clear();

        columns.add(new ColumnInfo(null, "№", 25,
                ColumnType.NUMERIC, SortOrder.ASC, true));
        columns.add(new ColumnInfo(null, "ФИО", 220,
                ColumnType.STRING, SortOrder.ASC, true));
        columns.add(new ColumnInfo(null, "Рег. №", 70,
                ColumnType.NUMERIC, SortOrder.ASC, true));
        columns.add(new ColumnInfo(null, "Поступает", 100,
                ColumnType.STRING, SortOrder.ASC, true));

//        int startSpecialityIndex = 3;
        for (int i = 0; i < DataAccessFactory.getSpecialities().size(); ++i) {
            columns.add(new ColumnInfo(null, DataAccessFactory.getSpecialities().get(i).getName(), 60,
                    ColumnType.STRING, SortOrder.ASC, false));
        }

        columns.add(new ColumnInfo(null, "Балл", 45,
                ColumnType.NUMERIC, SortOrder.DESC, true));
        columns.add(new ColumnInfo(null, "Специальность", 95,
                ColumnType.STRING, SortOrder.DESC, true));
        columns.add(new ColumnInfo(null, "Зачислен на", 95,
                ColumnType.STRING, SortOrder.DESC, true));

//        int startExaminesIndex = 2 + DataAccessFactory.getSpecialities().size() + startSpecialityIndex;
        for (int i = 0; i < DataAccessFactory.getExamines().size(); ++i) {
            columns.add(new ColumnInfo(null, DataAccessFactory.getExamines().get(i).getName(), 60,
                    ColumnType.NUMERIC, SortOrder.DESC, true));
        }

        columns.add(new ColumnInfo(null, "Телефон", 150,
                ColumnType.STRING, SortOrder.ASC, false));
        columns.add(new ColumnInfo(null, "Дата", 110,
                ColumnType.STRING, SortOrder.ASC, true));
    }

    public void removeColumn(int column) {
        removeColumn(getColumns().get(column).getColumn());
        getColumns().get(column).setVisible(false);
    }

    public void addColumn(int column) {
        getColumns().get(column).setVisible(true);
        addColumn(getColumns().get(column).getColumn());
        moveColumn(column);
    }

    public void moveColumn(int column) {
        int sourceIndex = convertColumnIndexToView(column);
        int targetIndex = 0;

        for (int i = 0; i < column; ++i) {
            if (getColumns().get(i).isVisible()) {
                targetIndex++;
            }
        }
        moveColumn(sourceIndex, targetIndex);
    }

    public void refresh() {
        int selectedRow = this.getSelectedRow();
        int selectedColumn = this.getSelectedColumn();
        matriculantTableModel.fireTableDataChanged();
        repaint();
        this.changeSelection(selectedRow, selectedColumn, false, false);
    }

    public void reload() {
        regenerateColumnData();
        setDefaultShowEntrances();
        getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent event) {
                if (SwingUtilities.isRightMouseButton(event)) {
                    JTableHeader tableHeader = (JTableHeader) event.getSource();
                    Point point = event.getPoint();
                    JPopupMenu popupMenu = createColumnPopupMenu(tableHeader.getColumnModel().getColumn(
                            tableHeader.columnAtPoint(point)).getHeaderValue().toString());

                    popupMenu.show(getTableHeader(), point.x, point.y);
                }
            }
        });
//        getTableHeader().setComponentPopupMenu(createColumnPopupMenu());
        setColumnModel(recreateColumnModel());
        for (int i = 0; i < getColumns().size(); ++i) {
            if (!getColumns().get(i).isVisible()) {
                removeColumn(getColumns().get(i).getColumn());
            }
        }
    }

    public static boolean isHighlighting() {
        return highlighting;
    }

    public static void setHighlighting(boolean highlighting) {
        MatriculantTable.highlighting = highlighting;
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
            matriculantTableModel.sort(columnIndexes, columns);
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

    public int convertViewColumnIndexToModel(int viewColumnIndex) {
        return /*matriculantTableModel.getVisibleColumns().get(*/convertColumnIndexToModel(viewColumnIndex)/*)*/;
    }


    //============= MatriculantTableModel ==============

    private static class MatriculantTableModel extends AbstractTableModel {
        private static DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd hh:mm");
        static List<List<Integer>> matriculantIndexes = new ArrayList<List<Integer>>();

        public MatriculantTableModel() {
            MatriculantTable.setDefaultShowEntrances();
            restoreIndexes();
//            regenerateColumnData();
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
                    if (DataAccessFactory.getMatriculants().get(j).onSpeciality(i)) {
                        matriculantIndexes.get(i).add(j);
                    }
                }
            }
        }

        public void sort(final List<Integer> columnIndexes, final List<ColumnInfo> columns) {
            Collections.sort(viewRowIndexes, new Comparator<Integer>() {
                public int compare(Integer o1, Integer o2) {
                    return compareByColumnsPriority(viewRowIndexes.indexOf(o1), viewRowIndexes.indexOf(o2), 0);
                }

                private int compareByColumnsPriority(Integer firstIndex, Integer secondIndex, int columnIndex) {
                    if (columnIndex == columnIndexes.size()) {
                        return 0;
                    }

                    int k = 1;
                    int cmp;
                    String arg1 = getValueAt(firstIndex, columnIndexes.get(columnIndex)).toString();
                    String arg2 = getValueAt(secondIndex, columnIndexes.get(columnIndex)).toString();

                    switch (columns.get(columnIndexes.get(columnIndex)).getColumnSortOrder()) {
                        case DESC:
                            k = -1;
                    }

                    // Обработка случая пустого значения в полях
                    if (arg1 == null || arg1.isEmpty()) {
                        if (arg2 == null || arg2.isEmpty()) {
                            return compareByColumnsPriority(firstIndex, secondIndex, columnIndex + 1);
                        } else {
                            return k;
                        }
                    } else {
                        if (arg2 == null || arg2.isEmpty()) {
                            return -k;
                        }
                    }

                    switch (columns.get(columnIndexes.get(columnIndex)).getColumnType()) {
                        case NUMERIC:
                            cmp = Integer.valueOf(arg1).compareTo(Integer.valueOf(arg2));
                            if (cmp == 0) {
                                return compareByColumnsPriority(firstIndex, secondIndex, columnIndex + 1);
                            } else {
                                return cmp * k;
                            }
                        case STRING:
                        default:
                            cmp = arg1.compareTo(arg2);
                            if (cmp == 0) {
                                return compareByColumnsPriority(firstIndex, secondIndex, columnIndex + 1);
                            } else {
                                return cmp * k;
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

        public static void setRowIndexesFromMatriculantIndexesBy(int index) {
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

            boolean theContinue;
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
            return columnNames.size();//visibleColumns.size();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
//            columnIndex = visibleColumns.get(columnIndex);

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
                        return "По экзаменам";
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
                String result = matriculant.getSpeciality().get(1);

                if (specialityIndex > -1) {
                    //result += " (" + DataAccessFactory.getSpecialities().get(specialityIndex).getName() + ")";
                }
                return result;
            }

            index++;
            if (columnIndex == index) {
                String result = matriculant.getEntranceSpecialityName();

                if (result != null) {
                    return matriculant.getEntranceSpecialityName();
                }
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

            if (((MatriculantTable) table).convertViewColumnIndexToModel(column) ==
                    DataAccessFactory.getSpecialities().size() + 4) {
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

            if (isSelected) {
                if (((MatriculantTable) table).convertViewColumnIndexToModel(column) == 1) {
                    cell.setFont(cell.getFont().deriveFont(Font.BOLD));
                }
            }

            return cell;
        }
    }

    public static class ColumnInfo {
        private TableColumn column;
        private String columnName;
        private int columnWidth;
        private ColumnType columnType;
        private SortOrder columnSortOrder;
        private boolean visible = true;

        public ColumnInfo() {
        }

        public ColumnInfo(TableColumn column, String name, int width, ColumnType type, SortOrder sortOrder, boolean visible) {
            this.column = column;
            this.columnName = name;
            this.columnWidth = width;
            this.columnType = type;
            this.columnSortOrder = sortOrder;
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

        public int getColumnWidth() {
            return columnWidth;
        }

        public void setColumnWidth(int columnWidth) {
            this.columnWidth = columnWidth;
        }

        public ColumnType getColumnType() {
            return columnType;
        }

        public void setColumnType(ColumnType columnType) {
            this.columnType = columnType;
        }

        public SortOrder getColumnSortOrder() {
            return columnSortOrder;
        }

        public void setColumnSortOrder(SortOrder columnSortOrder) {
            this.columnSortOrder = columnSortOrder;
        }
    }

    private enum ColumnType {
        NUMERIC, STRING
    }

    private enum SortOrder {
        ASC, DESC
    }
}
