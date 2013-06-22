package ru.sgu.csit.admissiondepartment.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ru.sgu.csit.admissiondepartment.common.ReceiptExamine;
import ru.sgu.csit.admissiondepartment.dao.ReceiptExamineDAO;

import java.util.List;

/**
 * Date: May 7, 2010
 * Time: 1:53:43 PM
 *
 * @author hd (KhurtinDN::a::gmail.com)
 */
@Repository("receiptExamineDAO")
public class ReceiptExamineDAOImpl extends GenericDAOImpl<ReceiptExamine, Long> implements ReceiptExamineDAO {

    @Override
    public List<ReceiptExamine> findByName(String name) {
        return findByCriteria(Restrictions.eq("name", name));        
    }
}
