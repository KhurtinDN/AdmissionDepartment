package ru.sgu.csit.selectioncommittee.dao.impl;

import org.hibernate.criterion.Restrictions;
import ru.sgu.csit.selectioncommittee.common.Matriculant;
import ru.sgu.csit.selectioncommittee.dao.MatriculantDAO;

import java.util.Date;
import java.util.List;

/**
 * Date: May 5, 2010
 * Time: 2:35:42 PM
 *
 * @author xx & hd
 */
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
