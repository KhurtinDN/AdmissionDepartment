package ru.sgu.csit.admissiondepartment.gui.dialogs.panels;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import ru.sgu.csit.admissiondepartment.common.ReceiptExamine;
import ru.sgu.csit.admissiondepartment.factory.DataAccessFactory;

import java.util.*;
import java.util.List;

/**
 * Date: May 6, 2010
 * Time: 10:17:09 PM
 *
 * @author xx & hd
 */
public class MarkPanel extends SelectNumbersPanel {
    public MarkPanel() {
        super("Баллы по ЕГЭ", 0, 100);

        List<String> examineNames = Lists.transform(DataAccessFactory.getExamines(),
                new Function<ReceiptExamine, String>() {
                    @Override
                    public String apply(ReceiptExamine receiptExamine) {
                        return receiptExamine.getName();
                    }
                });

        List<ReceiptExamine> receiptExamineList = DataAccessFactory.getExamines();
        for (ReceiptExamine receiptExamine : receiptExamineList) {
            examineNames.add(receiptExamine.getName());
        }

        createLabelList(examineNames);
    }

    public void setMarks(Map<String, Integer> specialityMap) {
        setNumbers(specialityMap);
    }

    public Map<String, Integer> getMarks() {
        return getNumbers();
    }
}
