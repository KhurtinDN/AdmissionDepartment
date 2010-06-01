package ru.sgu.csit.selectioncommittee.gui;

import ru.sgu.csit.selectioncommittee.common.Matriculant;
import ru.sgu.csit.selectioncommittee.common.ReceiptExamine;
import ru.sgu.csit.selectioncommittee.common.Speciality;
import ru.sgu.csit.selectioncommittee.factory.DataAccessFactory;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
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
    private static List<String> columnNames = new ArrayList<String>();
    private static List<Integer> columnWidths = new ArrayList<Integer>();

    private static MatriculantTableModel matriculantTableModel = new MatriculantTableModel();

    static {
        regenerateColumnNames();
    }

    public MatriculantTable() {
        super(matriculantTableModel);
        setAutoCreateRowSorter(true);
        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        setColumnWidths();
    }

    private void setColumnWidths() {
        for (int i = 0; i < columnWidths.size(); ++i) {
            getColumnModel().getColumn(i).setPreferredWidth(columnWidths.get(i));
        }
    }

    private static void regenerateColumnNames() {
        columnNames.clear();
        columnWidths.clear();

        columnNames.add("ФИО");
        columnWidths.add(220);

        columnNames.add("Рег. №");
        columnWidths.add(70);

        for (int i = 0; i < DataAccessFactory.getSpecialities().size(); ++i) {
            columnNames.add(DataAccessFactory.getSpecialities().get(i).getName());
            columnWidths.add(50);
        }

        for (int i = 0; i < DataAccessFactory.getExamines().size(); ++i) {
            columnNames.add(DataAccessFactory.getExamines().get(i).getName());
            columnWidths.add(50);
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

            int index = columnIndex - 2;

            if (index >= 0 && index < DataAccessFactory.getSpecialities().size()) {
                for (Map.Entry<Integer, String> entry : matriculant.getSpeciality().entrySet()) {
                    if (entry.getValue().equals(DataAccessFactory.getSpecialities().get(index).getName())) {
                        return entry.getKey().toString();
                    }
                }
            }

            index = columnIndex - (DataAccessFactory.getSpecialities().size() + 2);
            if (index >= 0 && index < DataAccessFactory.getExamines().size()) {
                Integer value = matriculant.getBalls().get(DataAccessFactory.getExamines().get(index));
                
                if (value != null) {
                    return value.toString();
                }
            }

            index = DataAccessFactory.getExamines().size() + DataAccessFactory.getSpecialities().size() + 2;
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
}
