package ru.sgu.csit.selectioncommittee.gui.utils;

import java.io.File;
import java.io.IOException;

import static ru.sgu.csit.selectioncommittee.gui.utils.MessageUtil.showErrorMessage;

/**
 * Date: Jun 17, 2010
 * Time: 12:45:53 PM
 *
 * @author xx & hd
 */
public class SomeUtils {
    public static void openExcelViewer(File file) {
        String program = ApplicationSettings.getSettings().getExcelExecutor();
        try {
            String[] arguments = new String[]{program, file.getCanonicalPath()};
            Runtime.getRuntime().exec(arguments);
        } catch (IOException e1) {
            showErrorMessage("Не удалось запустить программу " + program);
        }
    }
}
