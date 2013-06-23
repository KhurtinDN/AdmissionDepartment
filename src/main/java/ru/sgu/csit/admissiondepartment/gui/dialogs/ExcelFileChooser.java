package ru.sgu.csit.admissiondepartment.gui.dialogs;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * Date: Jun 16, 2010
 * Time: 2:06:58 PM
 *
 * @author xx & hd
 */
public class ExcelFileChooser extends JFileChooser {

    public ExcelFileChooser() {
        UIManager.put("FileChooser.openButtonText", "Открыть");
        UIManager.put("FileChooser.saveButtonText", "Сохранить");
        UIManager.put("FileChooser.cancelButtonText", "Отменить");
        UIManager.put("FileChooser.openButtonToolTipText", "Открыть");
        UIManager.put("FileChooser.saveButtonToolTipText", "Сохранить");
        UIManager.put("FileChooser.cancelButtonToolTipText", "Отменить");
        UIManager.put("FileChooser.lookInLabelText", "Открыть в:");
        UIManager.put("FileChooser.saveInLabelText", "Сохранить в:");
        UIManager.put("FileChooser.fileNameLabelText", "Имя файла:");
        UIManager.put("FileChooser.filesOfTypeLabelText", "Типы файлов:");
        UIManager.put("FileChooser.upFolderToolTipText", "На уровень выше");
        UIManager.put("FileChooser.homeFolderToolTipText", "В начало");
        UIManager.put("FileChooser.newFolderToolTipText", "Создать папку");
        UIManager.put("FileChooser.listViewButtonToolTipText", "Список");
        UIManager.put("FileChooser.detailsViewButtonToolTipText", "Детально");
        UIManager.put("FileChooser.directoryOpenButtonText", "Открыть");
        UIManager.put("FileChooser.directoryOpenButtonToolTipText", "Открыть папку");
        UIManager.put("FileChooser.acceptAllFileFilterText", "Все файлы");

        setFileFilter(new FileNameExtensionFilter("Excel файлы (*.xls, *.xlsx)", "xls", "xlsx"));
        setDialogTitle("Сохранение абитуриентов в Excel файл");
    }

    @Override
    public File getSelectedFile() {
        File file = super.getSelectedFile();

        if (file == null) {
            return null;
        }

        String fileName = file.getPath().toLowerCase();

        if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
            return file;
        }

        return new File(fileName + ".xls");
    }
}
