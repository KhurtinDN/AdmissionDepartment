package ru.sgu.csit.admissiondepartment.dao;

import ru.sgu.csit.admissiondepartment.common.Speciality;

import java.util.List;

/**
 * Date: May 7, 2010
 * Time: 2:00:07 PM
 *
 * @author hd (KhurtinDN::a::gmail.com)
 */
public interface SpecialityDAO extends GenericDAO<Speciality, Long> {
    List<Speciality> findByName(String name);
}
