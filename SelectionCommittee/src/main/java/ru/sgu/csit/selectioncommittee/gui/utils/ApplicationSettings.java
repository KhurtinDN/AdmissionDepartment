package ru.sgu.csit.selectioncommittee.gui.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import static ru.sgu.csit.selectioncommittee.gui.utils.MessageUtil.*;

/**
 * Date: Jun 17, 2010
 * Time: 12:37:59 PM
 *
 * @author xx & hd
 */
public class ApplicationSettings {
    private static ApplicationSettings settings = null;

    private ApplicationSettings() {
    }

    public static ApplicationSettings getSettings() {
        if (settings == null) {
            settings = new ApplicationSettings();
        }
        return settings;
    }

    public String getExcelExecutor() {
        Properties properties = new Properties();
        String APPLICATION_PROPERTIES = "application.properties";

        boolean exist = true;
        try {
            properties.load(new FileInputStream(APPLICATION_PROPERTIES));
        } catch (IOException e) {
            exist = false;
        }

        String excelExecutor = null;
        if (exist) {
            excelExecutor = properties.getProperty("excelExecutor");
        }
        if (excelExecutor == null) {
            excelExecutor = "oocalc";
            properties.put("excelExecutor", excelExecutor);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(APPLICATION_PROPERTIES);
                properties.store(fileOutputStream, "Application settings");
                fileOutputStream.close();
            } catch (IOException e) {
                showErrorMessage("Нет прав создать файл настроек " + APPLICATION_PROPERTIES);
            }
        }

        return excelExecutor;
    }
}
