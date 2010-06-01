package ru.sgu.csit.selectioncommittee.dao;

import ru.sgu.csit.selectioncommittee.common.ReceiptExamine;

import java.util.List;

/**
 * Date: May 7, 2010
 * Time: 1:55:40 PM
 *
 * @author hd (KhurtinDN::a::gmail.com)
 */
public interface ReceiptExamineDAO extends GenericDAO<ReceiptExamine, Long> {
    List<ReceiptExamine> findByName(String name);
}
