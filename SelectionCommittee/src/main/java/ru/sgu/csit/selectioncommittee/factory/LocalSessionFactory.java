package ru.sgu.csit.selectioncommittee.factory;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * Date: Apr 19, 2010
 * Time: 1:41:16 PM
 *
 * @author xx & hd
 */

public class LocalSessionFactory {
    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new AnnotationConfiguration()
                    //.addPackage("ru.sgu.csit.selectioncommittee.common")
                    .addAnnotatedClass(ru.sgu.csit.selectioncommittee.common.Matriculant.class)
                    //.addAnnotatedClass(ru.sgu.csit.selectioncommittee.common.Matriculant.class)                    
                    .configure().buildSessionFactory();
        } catch (Throwable e) {
            System.err.println("The session factory can't be created:" + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
