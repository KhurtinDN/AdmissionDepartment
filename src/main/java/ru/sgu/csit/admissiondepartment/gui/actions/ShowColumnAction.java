package ru.sgu.csit.admissiondepartment.gui.actions;

import ru.sgu.csit.admissiondepartment.gui.MatriculantTable;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.tSHOWCOLUMN_DESCRIPTION;

/**
 * Date: Jun 27, 2010
 * Time: 10:47:53 PM
 *
 * @author : xx & hd
 */
public class ShowColumnAction extends AbstractAction {

    private MatriculantTable matriculantTable;

    public ShowColumnAction(String name, MatriculantTable matriculantTable) {
        this.matriculantTable = matriculantTable;
        putValue(Action.NAME, name);
        putValue(Action.SHORT_DESCRIPTION, tSHOWCOLUMN_DESCRIPTION);
    }

    public void actionPerformed(ActionEvent e) {
        JCheckBoxMenuItem columnItem = (JCheckBoxMenuItem) e.getSource();

        for (int i = 0; i < matriculantTable.getColumns().size(); ++i) {
            MatriculantTable.ColumnInfo column = matriculantTable.getColumns().get(i);

            if (column.getColumnName().equals(columnItem.getText())) {
                if (columnItem.isSelected()) {
                    matriculantTable.addColumn(i);
                } else {
                    matriculantTable.removeColumn(i);
                }
                matriculantTable.repaint();

                break;
            }
        }
    }
}
