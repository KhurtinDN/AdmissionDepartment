package ru.sgu.csit.admissiondepartment.dao.impl;

import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import ru.sgu.csit.admissiondepartment.dao.GenericDAO;
import ru.sgu.csit.admissiondepartment.factory.LocalSessionFactory;

import javax.persistence.Table;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: May 5, 2010
 * Time: 12:46:53 PM
 *
 * @author xx & hd
 */
public class GenericDAOImpl<T, PK extends Serializable> implements GenericDAO<T, PK> {

    public GenericDAOImpl() {
    }

    protected Session getSession() {
        return LocalSessionFactory.getSessionFactory().getCurrentSession();
    }

    public Class getPersistentClass() {
        return (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    @SuppressWarnings("unchecked")
    public PK save(T newInstance) {
        PK primaryKey = null;
        Transaction transaction = null;

        try {
            transaction = getSession().beginTransaction();
            primaryKey = (PK) getSession().save(newInstance);
            transaction.commit();
        }
        catch (HibernateException he) {
            if (transaction != null) transaction.rollback();
            throw he;
        }
        finally {
            getSession().close();
        }

        return primaryKey;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T findById(PK id) {
        T object;

        try {
            object = findByCriteria(Restrictions.eq("id", id)).get(0);
        }
        catch (IllegalArgumentException ex) {
            object = null;
        }

        return object;
    }

    @SuppressWarnings("unchecked")
    protected List<T> findByCriteria(Criterion... criterion) {
        List<T> list = new ArrayList<T>();
        Transaction transaction = null;

        try {
            transaction = getSession().beginTransaction();
            Criteria criteria = getSession().createCriteria(getPersistentClass());

            for (Criterion c : criterion) {
                criteria.add(c);
            }
            list = criteria.list();
            transaction.commit();
        }
        catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        getSession().close();

        return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        Annotation annotation = getPersistentClass().getAnnotation(Table.class);
        String query;

        if (annotation != null && !((Table) annotation).name().isEmpty()) {
            query = "select * from " + ((Table) annotation).name();
        } else {
            query = "select * from " + getPersistentClass().getSimpleName();//.toString();
        }

        String[] parName = new String[0];
        Object[] par = new Object[0];

        return findByQuery(query, parName, par);
    }

    @SuppressWarnings("unchecked")
    protected List<T> findByQuery(String query, String[] parametersName, Object[] parameters) {
        List<T> list = new ArrayList<T>();
        Transaction transaction = null;

        try {
            transaction = getSession().beginTransaction();

            Query q = getSession().createSQLQuery(query).addEntity(getPersistentClass());

            if (parameters.length == parametersName.length && parameters.length > 0) {
                for (int i = 0; i < parametersName.length; i++) {
                    q.setParameter(parametersName[i], parameters[i]);
                }
            }
            list = q.list();
            transaction.commit();
        }
        catch (HibernateException he) {
            transaction.rollback();
            System.err.println(he);
        }
        getSession().close();

        return list;
    }

    @Override
    public void update(T transientObject) {
        Transaction transaction = null;

        try {
            transaction = getSession().beginTransaction();
            getSession().update(transientObject);
            transaction.commit();
        }
        catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw he;
        }
        finally {
            getSession().close();
        }
    }

    @Override
    public void saveOrUpdate(T transientObject) {
        Transaction transaction = null;

        try {
            transaction = getSession().beginTransaction();
            getSession().saveOrUpdate(transientObject);
            transaction.commit();
        }
        catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw he;
        }
        finally {
            getSession().close();
        }
    }

    @Override
    public void delete(T transientObject) {
        Transaction transaction = null;

        try {
            transaction = getSession().beginTransaction();
            getSession().delete(transientObject);
            transaction.commit();
        }
        catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw he;
        }
        finally {
            getSession().close();
        }
    }
}
