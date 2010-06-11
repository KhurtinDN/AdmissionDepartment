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
    private static List<ColumnInfo> columns = new ArrayList<ColumnInfo>();
    private static List<String> columnNames = new ArrayList<String>();
    private static List<Integer> columnWidths = new ArrayList<Integer>();

    private static boolean highlighting = true;

    private static MatriculantTableModel matriculantTableModel = new MatriculantTableModel();

    static {
        regenerateColumnNames();
    }

    public MatriculantTable() {
        super(matriculantTableModel);
        setDefaultRenderer(Object.class, new MatriculantTableCellRenderer());
        setAutoCreateRowSorter(true);
        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        setColumnWidths();
    }

    private void setColumnWidths() {
        for (int i = 0; i < columnWidths.size(); ++i) {
            getColumnModel().getColumn(i).setPreferredWidth(columnWidths.get(i));
            columns.add(new ColumnInfo(getColumnModel().getColumn(i)));
        }
    }

    private static void regenerateColumnNames() {
        columnNames.clear();
        columnWidths.clear();

        columnNames.add("ФИО");
        columnWidths.add(220);

        columnNames.add("Рег. №");
        columnWidths.add(70);

        columnNames.add("Поступает");
        columnWidths.add(100);

        for (int i = 0; i < DataAccessFactory.getSpecialities().size(); ++i) {
            columnNames.add(DataAccessFactory.getSpecialities().get(i).getName());
            columnWidths.add(55);
        }

        columnNames.add("Балл");
        columnWidths.add(45);

        for (int i = 0; i < DataAccessFactory.getExamines().size(); ++i) {
            columnNames.add(DataAccessFactory.getExamines().get(i).getName());
            columnWidths.add(55);
        }

        columnNames.add("Телефон");
        columnWidths.add(200);

        columnNames.add("Дата");
        columnWidths.add(120);
    }

    public void refresh() {
        matriculantTableModel.fireTableStructureChanged();
        setAutoCreateRowSorter(true);
        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        setColumnWidths();
    }

    public static boolean isHighlighting() {
        return highlighting;
    }

    public static void setHighlighting(boolean highlighting) {
        MatriculantTable.highlighting = highlighting;
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

    private static class ColumnInfo {
        private TableColumn column;
        private boolean visible = true;

        public ColumnInfo() {
        }

        public ColumnInfo(TableColumn column) {
            this.column = column;
        }

        public TableColumn getColumn() {
            return column;
        }

        public void setColumn(TableColumn column) {
            this.column = column;
        }

        public boolean isVisible() {
            return visible;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }
    }
}
