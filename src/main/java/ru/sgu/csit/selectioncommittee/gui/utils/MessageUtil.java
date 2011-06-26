package ru.sgu.csit.selectioncommittee.gui.utils;

import javax.swing.*;

import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.tCONFIRM;
import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.tNO;
import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.tYES;

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

    public static boolean showConfirmDialog(String message) {
        Object[] options = {tYES, tNO};
        return JOptionPane.showOptionDialog(null, message, tCONFIRM, JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[1]) == JOptionPane.OK_OPTION;
    }
}
