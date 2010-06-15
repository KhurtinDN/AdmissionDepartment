package ru.sgu.csit.selectioncommittee.gui.dialogs.panels;

import ru.sgu.csit.selectioncommittee.common.Speciality;
import ru.sgu.csit.selectioncommittee.factory.DataAccessFactory;

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

        List<String> specialityNames = new ArrayList<String>();
        for (Speciality speciality : DataAccessFactory.getSpecialities()) {
            specialityNames.add(speciality.getName());
        }
        createPriorityList(specialityNames);
    }

    public Map<Integer, String> getSpecialityMap() {
        Map<Integer, String> specialityMap = new TreeMap<Integer, String>();
        Integer i = 1;
        for (String labelName : getPriorityList()) {
            specialityMap.put(i, labelName);
            i++;
        }
        return specialityMap;
    }

    public void setSpecialityMap(Map<Integer, String> specialityMap) {
        Map<Integer, String> map = new TreeMap<Integer, String>(specialityMap);
        List<String> specialityList = new ArrayList<String>(map.values());
        setPriorityList(specialityList);
    }
}
