package ru.sgu.csit.admissiondepartment.gui.dialogs.panels;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import ru.sgu.csit.admissiondepartment.common.Speciality;
import ru.sgu.csit.admissiondepartment.factory.DataAccessFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Date: Jun 15, 2010
 * Time: 3:48:56 PM
 *
 * @author xx & hd
 */
public class CapacityOnSpecialitiesPanel extends SelectNumbersPanel {

    public CapacityOnSpecialitiesPanel() {
        super("Количество мест на каждой специальности", 0);

        List<String> specialityNames = Lists.transform(DataAccessFactory.getSpecialities(),
                new Function<Speciality, String>() {
                    @Override
                    public String apply(Speciality speciality) {
                        return speciality.getName();
                    }
                });

        createLabelList(specialityNames);
    }

    public void setCapacityOnSpecialities(Map<String, Integer> capacityMap) {
        setNumbers(capacityMap);
    }

    public Map<String, Integer> getCapacityOnSpecialities() {
        return getNumbers();
    }
}
