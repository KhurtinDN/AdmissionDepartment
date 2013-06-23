package ru.sgu.csit.admissiondepartment.system;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextHolder {

    private static final ApplicationContext appContext = new ClassPathXmlApplicationContext("ApplicationContext.xml");

    private ApplicationContextHolder() {}

    public static ApplicationContext get() {
        return appContext;
    }

    public static <T> T getBean(Class<T> aClass) {
        return appContext.getBean(aClass);
    }
}
