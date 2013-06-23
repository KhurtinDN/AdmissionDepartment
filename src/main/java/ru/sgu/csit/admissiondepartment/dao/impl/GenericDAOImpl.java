package ru.sgu.csit.admissiondepartment.dao.impl;

import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import ru.sgu.csit.admissiondepartment.common.PersistentItem;
import ru.sgu.csit.admissiondepartment.dao.GenericDAO;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Date: May 5, 2010
 * Time: 12:46:53 PM
 *
 * @author xx & hd
 */
public class GenericDAOImpl<T extends PersistentItem, PK extends Serializable> implements GenericDAO<T, PK> {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    @Override
    @SuppressWarnings("unchecked")
    public T findById(PK id) {
        return (T) getCurrentSession().get(getPersistentClass(), id);
    }

    @Transactional(readOnly = true)
    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return findByCriteria();
    }

    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public PK save(T entity) {
        return (PK) getCurrentSession().save(entity);
    }

    @Transactional
    @Override
    public void update(T entity) {
        getCurrentSession().update(entity);
    }

    @Transactional
    @Override
    public void saveOrUpdate(T entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

    @Transactional
    @Override
    public void delete(T entity) {
        getCurrentSession().delete(entity);
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    protected Class getPersistentClass() {
        return (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @SuppressWarnings("unchecked")
    protected List<T> findByCriteria(Criterion... criterions) {
        Criteria criteria = getCurrentSession().createCriteria(getPersistentClass());

        for (Criterion criterion : criterions) {
            criteria.add(criterion);
        }

        return criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }
}
