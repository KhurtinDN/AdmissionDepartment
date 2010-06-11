package ru.sgu.csit.selectioncommittee.gui;

import ru.sgu.csit.selectioncommittee.common.Matriculant;
import ru.sgu.csit.selectioncommittee.factory.DataAccessFactory;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private static boolean highlighting = true;

    private static MatriculantTableModel matriculantTableModel = new MatriculantTableModel();

    static {
        regenerateColumnData();
    }

    public MatriculantTable() {
        super(matriculantTableModel);
        setDefaultRenderer(Object.class, new MatriculantTableCellRenderer());
        setAutoCreateRowSorter(true);
        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        setColumns();
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

    private static void regenerateColumnData() {
        columnNames.clear();
        columnWidths.clear();
        columnVisible.clear();

        columnNames.add("ФИО");
        columnWidths.add(220);
        columnVisible.add(Boolean.TRUE);

        columnNames.add("Рег. №");
        columnWidths.add(70);
        columnVisible.add(Boolean.TRUE);

        columnNames.add("Поступает");
        columnWidths.add(100);
        columnVisible.add(Boolean.FALSE);

        for (int i = 0; i < DataAccessFactory.getSpecialities().size(); ++i) {
            columnNames.add(DataAccessFactory.getSpecialities().get(i).getName());
            columnWidths.add(60);
            columnVisible.add(Boolean.TRUE);
        }

        columnNames.add("Балл");
        columnWidths.add(45);
        columnVisible.add(Boolean.TRUE);

        for (int i = 0; i < DataAccessFactory.getExamines().size(); ++i) {
            columnNames.add(DataAccessFactory.getExamines().get(i).getName());
            columnWidths.add(60);
            columnVisible.add(Boolean.TRUE);
        }

        columnNames.add("Телефон");
        columnWidths.add(200);
        columnVisible.add(Boolean.FALSE);

        columnNames.add("Дата");
        columnWidths.add(120);
        columnVisible.add(Boolean.TRUE);
    }

    public void refresh() {
        matriculantTableModel.fireTableStructureChanged();
        setAutoCreateRowSorter(true);
        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
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

    private static class MatriculantTableModel extends AbstractTableModel {
        private static DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd hh:mm");

        @Override
        public int getRowCount() {
            return DataAccessFactory.getMatriculants().size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.size();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Matriculant matriculant = DataAccessFactory.getMatriculants().get(rowIndex);

            if (columnIndex == 0) {
                return matriculant.getName();
            }
            if (columnIndex == 1) {
                return matriculant.getReceiptNumber();
            }

            if (columnIndex == 2
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

            int index = columnIndex - 3;

            if (index >= 0 && index < DataAccessFactory.getSpecialities().size()) {
                for (Map.Entry<Integer, String> entry : matriculant.getSpeciality().entrySet()) {
                    if (entry.getValue().equals(DataAccessFactory.getSpecialities().get(index).getName())) {
                        return entry.getKey().toString();
                    }
                }
            }

            index = DataAccessFactory.getSpecialities().size() + 3;
            if (columnIndex == index) {
                String mainSpecName = matriculant.getSpeciality().get(1);
                Integer result = matriculant.calculateTotalBallsForSpeciality(mainSpecName);

                if (result != null) {
                    return String.valueOf(result);
                }
            }

            index = columnIndex - (index + 1);
            if (index >= 0 && index < DataAccessFactory.getExamines().size()) {
                Integer value = matriculant.getBalls().get(DataAccessFactory.getExamines().get(index).getName());

                if (value != null) {
                    return value.toString();
                }
            }

            index = DataAccessFactory.getExamines().size() + DataAccessFactory.getSpecialities().size() + 4;
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
            Matriculant matriculant = DataAccessFactory.getMatriculants().get(table.convertRowIndexToModel(row));

            if (table.convertColumnIndexToModel(column) == DataAccessFactory.getSpecialities().size() + 3) {
                cell.setFont(cell.getFont().deriveFont(Font.BOLD));
            }

            if (MatriculantTable.isHighlighting()) {
                if (!isSelected) {
                    if (!matriculant.completeAllDocuments()) {
                        cell.setBackground(new Color(255, 245, 210));
//                        cell.setFont(cell.getFont().deriveFont(Font.PLAIN));
///                       cell.setBackground(new Color(150, 150, 150));//Color(180, 250, 200));
///                       cell.setForeground(new Color(250, 250, 250));
                    } else if (matriculant.getDocuments() != null && matriculant.getDocuments().isTookDocuments()) {
                        cell.setBackground(new Color(255, 210, 210));
                    } else {
///                    cell.setBackground(Color.WHITE);
///                    cell.setForeground(Color.BLACK);
//                    cell.setFont(cell.getFont().deriveFont(Font.BOLD));
                        cell.setBackground(new Color(210, 255, 210));
                    }
                } else {
                    if (!matriculant.completeAllDocuments()) {
                        cell.setBackground(new Color(245, 225, 180));
                    } else if (matriculant.getDocuments() != null && matriculant.getDocuments().isTookDocuments()) {
                        cell.setBackground(new Color(245, 180, 180));
                    } else {
                        cell.setBackground(new Color(180, 245, 180));
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

        public ColumnInfo(TableColumn column) {
            this.column = column;
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
}
