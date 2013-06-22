package ru.sgu.csit.admissiondepartment.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Date: May 5, 2010
 * Time: 12:46:30 PM
 *
 * @author xx & hd
 */
public interface GenericDAO<T, PK extends Serializable> {
    PK save(T newInstance);
    T findById(PK id);
    List<T> findAll();
    void update(T transientObject);
    void saveOrUpdate(T transientObject);
    void delete(T transientObject);
}
