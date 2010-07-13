package ru.sgu.csit.selectioncommittee.gui.actions;

import ru.sgu.csit.selectioncommittee.gui.MatriculantTable;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.tSHOWCOLUMN_DESCRIPTION;

/**
 * Created by IntelliJ IDEA.
 * User: Администратор
 * Date: 13.07.2010
 * Time: 12:58:39
 * To change this template use File | Settings | File Templates.
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
