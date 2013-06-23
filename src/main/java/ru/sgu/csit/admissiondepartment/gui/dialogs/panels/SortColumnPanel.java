package ru.sgu.csit.admissiondepartment.gui.dialogs.panels;

import java.util.List;

/**
 * Date: Jun 15, 2010
 * Time: 4:53:21 PM
 *
 * @author xx & hd
 */
public class SortColumnPanel extends PriorityListPanel {

    public SortColumnPanel(List<String> labelNames) {
        super("Поля для сортировки");

        createPriorityList(labelNames);
    }

    public List<String> getColumnNameList() {
        return getPriorityList();
    }

    public void setColumnNameList(List<String> columnNameList) {
        setPriorityList(columnNameList);
    }
}
