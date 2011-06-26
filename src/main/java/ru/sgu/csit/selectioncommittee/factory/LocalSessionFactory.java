package ru.sgu.csit.selectioncommittee.factory;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import java.util.Properties;

/**
 * Date: Apr 19, 2010
 * Time: 1:41:16 PM
 *
 * @author xx & hd
 */

public class LocalSessionFactory {
    private static SessionFactory sessionFactory;

    public static boolean createNewSessionFactory(Properties properties) {
        AnnotationConfiguration annotationConfiguration = new AnnotationConfiguration().configure();
        annotationConfiguration.mergeProperties(properties);

        try {
            sessionFactory = annotationConfiguration.buildSessionFactory();
        } catch (Throwable e) {
            return false;
        }

        return true;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
