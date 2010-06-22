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
    private final String APPLICATION_PROPERTIES = "application.properties";

    private static ApplicationSettings settings = null;
    private Properties properties = null;

    private ApplicationSettings() {
        try {
            loadSettings();
        } catch (IOException e) {
            // settings not found
        }
    }

    private void loadSettings() throws IOException {
        properties = new Properties();
        properties.load(new FileInputStream(APPLICATION_PROPERTIES));
    }

    private void saveSettings() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(APPLICATION_PROPERTIES);
            properties.store(fileOutputStream, "Application settings");
            fileOutputStream.close();
        } catch (IOException e) {
            showErrorMessage("При создании файла настроек " + APPLICATION_PROPERTIES + " возникли проблемы.");
            System.err.println(e);
        }
    }

    public static ApplicationSettings getSettings() {
        if (settings == null) {
            settings = new ApplicationSettings();
        }
        return settings;
    }

    public String getExcelExecutor() {
        String excelExecutor = properties.getProperty("excelExecutor");
        if (excelExecutor == null) {
            excelExecutor = "oocalc";
            properties.put("excelExecutor", excelExecutor);
            saveSettings();
        }
        return excelExecutor;
    }
}
