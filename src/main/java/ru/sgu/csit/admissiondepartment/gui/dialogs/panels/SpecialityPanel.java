package ru.sgu.csit.admissiondepartment.gui.dialogs.panels;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ru.sgu.csit.admissiondepartment.common.Speciality;
import ru.sgu.csit.admissiondepartment.factory.DataAccessFactory;

import java.util.*;

/**
 * Date: May 6, 2010
 * Time: 9:44:23 PM
 *
 * @author xx & hd
 */
public class SpecialityPanel extends PriorityListPanel {

    public SpecialityPanel() {
        super("Предпочитаемые специальности");

        List<String> specialityNames = Lists.newArrayList();
        for (Speciality speciality : DataAccessFactory.getSpecialities()) {
            specialityNames.add(speciality.getName());
        }
        createPriorityList(specialityNames);
    }

    public Map<Integer, String> getSpecialityMap() {
        Map<Integer, String> specialityMap = Maps.newTreeMap();

        int i = 1;

        for (String labelName : getPriorityList()) {
            specialityMap.put(i, labelName);
            i++;
        }
        return specialityMap;
    }

    public void setSpecialityMap(Map<Integer, String> specialityMap) {
        Map<Integer, String> map = new TreeMap<Integer, String>(specialityMap);
        List<String> specialityList = Lists.newArrayList(map.values());
        setPriorityList(specialityList);
    }
}
