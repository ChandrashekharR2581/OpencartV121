package utilities;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * ExcelUtility - simplified utility to read/write .xlsx files using Apache POI.
 * Methods match the snips you provided:
 *  - getRowCount
 *  - getCellCount
 *  - getCellData
 *  - setCellData
 *  - fillGreenColor
 *  - fillRedColor
 *
 * Note: add apache-poi and poi-ooxml to your pom.
 */
public class ExcelUtility {

    private String path;

    // transient objects used inside methods (not shared between threads)
    public ExcelUtility(String path) {
        this.path = path;
    }

    public int getRowCount(String sheetName) throws IOException {
        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                return 0;
            }
            return sheet.getLastRowNum(); // last row index (0-based). Matches snippet behaviour.
        }
    }

    public int getCellCount(String sheetName, int rownum) throws IOException {
        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                return 0;
            }
            Row row = sheet.getRow(rownum);
            if (row == null) {
                return 0;
            }
            return row.getLastCellNum(); // returns cell count as short (index+1) or -1 if no cells
        }
    }

    public String getCellData(String sheetName, int rownum, int colnum) throws IOException {
        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                return "";
            }
            Row row = sheet.getRow(rownum);
            if (row == null) {
                return "";
            }
            Cell cell = row.getCell(colnum);
            if (cell == null) {
                return "";
            }

            DataFormatter formatter = new DataFormatter();
            try {
                return formatter.formatCellValue(cell);
            } catch (Exception e) {
                return "";
            }
        }
    }

    public void setCellData(String sheetName, int rownum, int colnum, String data) throws IOException {
        File file = new File(path);

        // If file doesn't exist, create new workbook and write initial file
        if (!file.exists()) {
            try (XSSFWorkbook wb = new XSSFWorkbook();
                 FileOutputStream fo = new FileOutputStream(path)) {
                wb.createSheet(sheetName); // will create sheet; further logic will reopen and write correct cell
                wb.write(fo);
            }
        }

        // Now open file for read/write
        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                sheet = workbook.createSheet(sheetName);
            }

            Row row = sheet.getRow(rownum);
            if (row == null) {
                row = sheet.createRow(rownum);
            }

            Cell cell = row.getCell(colnum);
            if (cell == null) {
                cell = row.createCell(colnum);
            }
            cell.setCellValue(data);

            // write changes
            try (FileOutputStream fo = new FileOutputStream(path)) {
                workbook.write(fo);
            }
        }
    }

    public void fillGreenColor(String sheetName, int rownum, int colnum) throws IOException {
        applyFillColor(sheetName, rownum, colnum, IndexedColors.GREEN);
    }

    public void fillRedColor(String sheetName, int rownum, int colnum) throws IOException {
        applyFillColor(sheetName, rownum, colnum, IndexedColors.RED);
    }

    // helper - applies fill color using workbook cell style
    private void applyFillColor(String sheetName, int rownum, int colnum, IndexedColors color) throws IOException {
        File file = new File(path);

        // Ensure file exists
        if (!file.exists()) {
            try (XSSFWorkbook wb = new XSSFWorkbook();
                 FileOutputStream fo = new FileOutputStream(path)) {
                wb.createSheet(sheetName);
                wb.write(fo);
            }
        }

        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                sheet = workbook.createSheet(sheetName);
            }

            Row row = sheet.getRow(rownum);
            if (row == null) {
                row = sheet.createRow(rownum);
            }

            Cell cell = row.getCell(colnum);
            if (cell == null) {
                cell = row.createCell(colnum);
            }

            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(color.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            cell.setCellStyle(style);

            try (FileOutputStream fo = new FileOutputStream(path)) {
                workbook.write(fo);
            }
        }
    }
}
