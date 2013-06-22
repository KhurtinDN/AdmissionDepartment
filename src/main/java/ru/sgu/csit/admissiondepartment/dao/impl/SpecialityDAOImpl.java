package ru.sgu.csit.admissiondepartment.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ru.sgu.csit.admissiondepartment.common.Speciality;
import ru.sgu.csit.admissiondepartment.dao.SpecialityDAO;

import java.util.List;

/**
 * Date: May 7, 2010
 * Time: 2:02:09 PM
 *
 * @author hd (KhurtinDN::a::gmail.com)
 */
@Repository("specialityDAO")
public class SpecialityDAOImpl extends GenericDAOImpl<Speciality, Long> implements SpecialityDAO {

    @Override
    public List<Speciality> findByName(String name) {
        return findByCriteria(Restrictions.eq("name", name));
    }
}
