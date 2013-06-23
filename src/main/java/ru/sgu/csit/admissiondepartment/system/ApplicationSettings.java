package ru.sgu.csit.admissiondepartment.system;

import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import static ru.sgu.csit.admissiondepartment.gui.utils.MessageUtil.*;

/**
 * Date: Jun 17, 2010
 * Time: 12:37:59 PM
 *
 * @author xx & hd
 */
@Component
public class ApplicationSettings {

    private static final String APPLICATION_PROPERTIES = "application.properties";

    public static final String NEED_OPEN_DOCUMENT_IN_EXPORT_TO_EXCEL_DIALOG = "ExportToExcelDialog.needOpenDocumentCheckBox.selected";

    private Properties properties;

    private boolean changedFlag;

    public ApplicationSettings() {
        try {
            loadSettings();
        } catch (IOException e) {
            // settings not found
        }
    }

    public void loadSettings() throws IOException {
        properties = new Properties();
        properties.load(new FileInputStream(APPLICATION_PROPERTIES));
    }

    public void saveSettings() {
        if (changedFlag) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(APPLICATION_PROPERTIES);
                properties.store(fileOutputStream, "Application settings");
                fileOutputStream.close();
            } catch (IOException e) {
                showErrorMessage("При создании файла настроек " + APPLICATION_PROPERTIES + " возникли проблемы.");
                System.err.println(e);
            }
        }
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public void set(String key, String value) {
        changedFlag = true;
        properties.put(key, value);
    }

    public boolean getOpenDocumentInExportToExcelDialog() {
        String value = get(NEED_OPEN_DOCUMENT_IN_EXPORT_TO_EXCEL_DIALOG);
        return value == null || Boolean.parseBoolean(value);
    }

    public void setOpenDocumentInExportToExcelDialog(boolean value) {
        set(NEED_OPEN_DOCUMENT_IN_EXPORT_TO_EXCEL_DIALOG, Boolean.toString(value));
    }
}
