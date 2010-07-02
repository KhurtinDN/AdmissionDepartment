package ru.sgu.csit.selectioncommittee;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.sgu.csit.selectioncommittee.gui.MainFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Date: 03.05.2010
 * Time: 13:22:51
 *
 * @author xx & hd
 */
public class Main {
    public static void main(String[] args) {
        System.setProperty("sun.awt.exception.handler",
                "ru.sgu.csit.selectioncommittee.gui.utils.SecurityExceptionHandler");

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ApplicationContext.xml");
                MainFrame mainFrame = applicationContext.getBean(MainFrame.class);
                mainFrame.login();
            }
        });
    }
}
