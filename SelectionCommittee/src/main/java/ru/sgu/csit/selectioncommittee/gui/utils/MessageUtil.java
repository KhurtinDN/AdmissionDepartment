package ru.sgu.csit.selectioncommittee.gui.utils;

import javax.swing.*;

/**
 * Date: 03.06.2010
 * Time: 10:39:05
 *
 * @author : xx & hd
 */
public class MessageUtil {
    public static void showInformationMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Информация", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Предупреждение", JOptionPane.WARNING_MESSAGE);
    }

    public static void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }
}
