package ru.sgu.csit.selectioncommittee;


import ru.sgu.csit.selectioncommittee.gui.dialogs.LoginDialog;

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
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginDialog loginDialog = new LoginDialog(null);
                loginDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                loginDialog.setVisible(true);
            }
        });
    }
}
