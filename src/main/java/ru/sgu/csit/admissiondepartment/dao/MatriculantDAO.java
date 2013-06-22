package ru.sgu.csit.admissiondepartment.dao;

import ru.sgu.csit.admissiondepartment.common.Matriculant;

import java.util.Date;
import java.util.List;

/**
 * Date: May 5, 2010
 * Time: 2:27:26 PM
 *
 * @author xx & hd
 */
public interface MatriculantDAO extends GenericDAO<Matriculant, Long>  {
    List<Matriculant> findByDate(Date startDate, Date endDate);
    List<Matriculant> findByName(String name);
}
