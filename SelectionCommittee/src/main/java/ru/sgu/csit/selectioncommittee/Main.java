package ru.sgu.csit.selectioncommittee;


import ru.sgu.csit.selectioncommittee.gui.MainFrame;

import javax.swing.*;

/**
 * Date: 03.05.2010
 * Time: 13:22:51
 *
 * @author xx & hd
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setDefaultCloseOperation(MainFrame.DO_NOTHING_ON_CLOSE);
            }
        });
    }
}
