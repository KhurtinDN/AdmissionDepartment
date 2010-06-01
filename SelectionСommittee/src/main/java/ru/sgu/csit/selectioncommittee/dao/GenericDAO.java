package ru.sgu.csit.selectioncommittee.dao;

import ru.sgu.csit.selectioncommittee.common.Matriculant;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Date: May 5, 2010
 * Time: 12:46:30 PM
 *
 * @author xx & hd
 */
public interface GenericDAO<T, PK extends Serializable> {
    PK create(T newInstance);
    T findById(PK id);
    List<T> findAll();
    void update(T transientObject);
    void delete(T transientObject);
}
