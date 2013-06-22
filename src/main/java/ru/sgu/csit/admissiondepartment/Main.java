package ru.sgu.csit.admissiondepartment;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.sgu.csit.admissiondepartment.gui.MainFrame;

import javax.swing.*;

/**
 * Date: 03.05.2010
 * Time: 13:22:51
 *
 * @author xx & hd
 */
public class Main {
    public static void main(String[] args) {
        System.setProperty("sun.awt.exception.handler",
                "ru.sgu.csit.admissiondepartment.gui.utils.SecurityExceptionHandler");

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ApplicationContext.xml");
                MainFrame mainFrame = applicationContext.getBean(MainFrame.class);
                mainFrame.login();
            }
        });
    }
}
