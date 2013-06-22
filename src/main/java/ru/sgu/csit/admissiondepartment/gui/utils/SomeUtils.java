package ru.sgu.csit.admissiondepartment.gui.utils;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static ru.sgu.csit.admissiondepartment.gui.utils.MessageUtil.showErrorMessage;

/**
 * Date: Jun 17, 2010
 * Time: 12:45:53 PM
 *
 * @author xx & hd
 */
public class SomeUtils {
    public static void openExcelViewer(File file) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                showErrorMessage("Произошла ошибка во время открытия файла " + file.getName() + "\n" + e.getMessage());
            }
        } else {
            showErrorMessage("Desktop API is not supported on the current platform");
        }
    }
}
