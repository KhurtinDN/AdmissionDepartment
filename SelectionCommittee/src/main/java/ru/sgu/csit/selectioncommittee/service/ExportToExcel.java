package ru.sgu.csit.selectioncommittee.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Date: 03.06.2010
 * Time: 8:23:22
 *
 * @author : xx & hd
 */
public class ExportToExcel {

    public ExportToExcel() {
    }

    public void write(File file, String title, List<String> headerList, List<List<String>> contentLists)
            throws ArgumentNotExcelFileException, FileNotFoundException, WritingException {
        Workbook workbook;
        String fileName = file.getName();
        if (fileName.endsWith(".xls")) {
            workbook = new HSSFWorkbook();
        } else if (fileName.endsWith(".xlsx")) {
            workbook = new XSSFWorkbook();
        } else {
            throw new ArgumentNotExcelFileException();
        }

        Sheet sheet = workbook.createSheet("Абитуриенты");
        sheet.setHorizontallyCenter(true);
        sheet.setAutobreaks(true);
        sheet.setFitToPage(true);

        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setFitWidth((short) 1);
        printSetup.setFitHeight((short) 9999);

        writeHeader(sheet, title, headerList);
        writeContent(sheet, contentLists);

        fillStyleForAllCell(sheet, contentLists.size(), headerList.size());
        resizeColumnWidth(sheet, headerList, contentLists);

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try {
            workbook.write(fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            throw new WritingException();
        }
    }

    private void writeHeader(Sheet sheet, String title, List<String> headerList) {
        int m = headerList.size();
        if (m > 0) {
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, m - 1));
        }

        Row titleRow = sheet.createRow(0);
        titleRow.setHeight((short) (20 * 20));
        setValue(titleRow, 0, title);

        Row headerRow = sheet.createRow(1);
        headerRow.setHeight((short) (15 * 20));

        for (int i = 0; i < m; ++i) {
            String head = headerList.get(i);
            setValue(headerRow, i, head);
        }

        sheet.createFreezePane(0, 2);
    }

    private void writeContent(Sheet sheet, List<List<String>> contentLists) {
        for (int i = 0, n = contentLists.size(); i < n; ++i) {
            List<String> stringList = contentLists.get(i);
            Row row = sheet.createRow(i + 2);
            for (int j = 0, m = stringList.size(); j < m; ++j) {
                setValue(row, j, stringList.get(j));
            }
        }
    }

    private void fillStyleForAllCell(Sheet sheet, int n, int m) {
        Row titleRow = sheet.getRow(0);
        setTitleCellStyle(sheet, titleRow.getCell(0));

        Row headerRow = sheet.getRow(1);
        for (int j = 0; j < m; ++j) {
            setHeaderCellStyle(sheet, headerRow.getCell(j));
        }

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                // set style for cell
            }
        }
    }

    private void resizeColumnWidth(Sheet sheet, List<String> headerList, List<List<String>> contentLists) {
        for (int j = 0, m = headerList.size(); j < m; ++j) {
            int width = headerList.get(j).length();
            for (int i = 0, n = contentLists.size(); i < n; ++i) {
                width = Math.max(width, contentLists.get(i).get(j).length());
            }
            sheet.setColumnWidth(j, (width + 2) * 256);
        }
    }

    private void setValue(Row row, int columnIndex, String value) {
        Cell cell = row.createCell(columnIndex);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue(value);
    }

    private void setTitleCellStyle(Sheet sheet, Cell cell) {
        Font font = sheet.getWorkbook().createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short)12);

        CellStyle headerCellStyle = sheet.getWorkbook().createCellStyle();
        headerCellStyle.setFont(font);
        headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        headerCellStyle.setWrapText(true);

        cell.setCellStyle(headerCellStyle);
    }

    private void setHeaderCellStyle(Sheet sheet, Cell cell) {
        Font boldFont = sheet.getWorkbook().createFont();
        boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

        CellStyle headerCellStyle = sheet.getWorkbook().createCellStyle();
        headerCellStyle.setFont(boldFont);
        headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        headerCellStyle.setWrapText(true);

        cell.setCellStyle(headerCellStyle);
    }
}
