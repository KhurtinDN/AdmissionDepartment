package ru.sgu.csit.admissiondepartment;

import ru.sgu.csit.admissiondepartment.gui.dialogs.LoginDialog;
import ru.sgu.csit.admissiondepartment.system.ApplicationContextHolder;

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
                "ru.sgu.csit.admissiondepartment.security.SecurityExceptionHandler");

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LoginDialog loginDialog = ApplicationContextHolder.getBean(LoginDialog.class);
                loginDialog.setVisible(true);
            }
        });
    }
}
