package ru.sgu.csit.admissiondepartment.gui.dialogs.panels;

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

        List<String> examineNames = new ArrayList<String>();

        List<Speciality> specialityList = DataAccessFactory.getSpecialities();
        for (Speciality speciality : specialityList) {
            examineNames.add(speciality.getName());
        }

        createLabelList(examineNames);
    }

    public void setCapacityOnSpecialities(Map<String, Integer> capacityMap) {
        setNumbers(capacityMap);
    }

    public Map<String, Integer> getCapacityOnSpecialities() {
        return getNumbers();
    }
}
