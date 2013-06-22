package ru.sgu.csit.admissiondepartment.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ru.sgu.csit.admissiondepartment.common.Matriculant;
import ru.sgu.csit.admissiondepartment.dao.MatriculantDAO;

import java.util.Date;
import java.util.List;

/**
 * Date: May 5, 2010
 * Time: 2:35:42 PM
 *
 * @author xx & hd
 */
@Repository("matriculantDAO")
public class MatriculantDAOImpl extends GenericDAOImpl<Matriculant, Long> implements MatriculantDAO {
    @Override
    public List<Matriculant> findByDate(Date startDate, Date endDate) {
        return findByCriteria(Restrictions.between("filingDate", startDate, endDate));
    }

    @Override
    public List<Matriculant> findByName(String name) {
        return findByCriteria(Restrictions.eq("name", name));
    }
}
