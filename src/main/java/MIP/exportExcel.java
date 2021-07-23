/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MIP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author dhmty
 */
public class exportExcel {

    public static final int COLUMN_INDEX_SHIFT_DATE = 0;
    public static final int COLUMN_INDEX_SHIFT_ID_ST = 1;
    public static final int COLUMN_INDEX_SHIFT_NAME = 2;
    public static final int COLUMN_INDEX_SHIFT_NS = 3;
    public static final int COLUMN_INDEX_SHORTS_NAME = 4;
    public static final int COLUMN_INDEX_SHORTS_GIOBD = 7;
    public static final int COLUMN_INDEX_SHORTS_PHUTBD = 8;
    public static final int COLUMN_INDEX_JOB_NAME = 5;
    public static final int COLUMN_INDEX_JOB_SOPHUT = 6;
    private static CellStyle cellStyleFormatNumber = null;
    
//    public static void main(String[] args) throws IOException {
//        final String excelFilePath = "data_Export/TemplateExport_CalAssignmentData_Fresher.xlsx";
//        final List<shiftModel> listShift =readExcel(excelFilePath);
//       // writeExcel(listShift, excelFilePath);
//    }
 
    public static void writeExcel(List<tmpObj> listObj,String excelFilePath) throws IOException{
        // Create Workbook
        Workbook workbook = getWorkbook(excelFilePath);
        // Create sheet
        Sheet sheet = workbook.createSheet("Phan Cong"); // Create sheet with sheet name
        
        int rowIndex=0;
        // Write header
        writeHeader(sheet, rowIndex);
        rowIndex++;
        
        // Write data
        for (tmpObj tmp:listObj){
            Row row = sheet.createRow(rowIndex);
            writeData(tmp,row);
            rowIndex++;
        }
 
        // Auto resize column witdth
        int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
        autosizeColumn(sheet, numberOfColumn);
 
        // Create file excel
        createOutputFile(workbook, excelFilePath);
        System.out.println("Done!!!");
    }
 
    // Create workbook
    private static Workbook getWorkbook(String excelFilePath) throws IOException {
        Workbook workbook = null;
 
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }
 
        return workbook;
    }
 
    // Write header with format
    private static void writeHeader(Sheet sheet, int rowIndex) {
        // create CellStyle
        CellStyle cellStyle = createStyleForHeader(sheet);
         
        // Create row
        Row row = sheet.createRow(rowIndex);
         
        // Create cells
        Cell cell = row.createCell(COLUMN_INDEX_SHIFT_DATE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Date");
 
        cell = row.createCell(COLUMN_INDEX_SHIFT_ID_ST);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("StoreID");
 
        cell = row.createCell(COLUMN_INDEX_SHIFT_NAME);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("ShiftGroupName");
 
        cell = row.createCell(COLUMN_INDEX_SHIFT_NS);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("UserName");
 
        cell = row.createCell(COLUMN_INDEX_SHORTS_NAME);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("TimeFrameName");
        
        cell = row.createCell(COLUMN_INDEX_JOB_NAME);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("JobName");
 
        cell = row.createCell(COLUMN_INDEX_JOB_SOPHUT);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("TotalMinute");
 
        cell = row.createCell(COLUMN_INDEX_SHORTS_GIOBD);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("HourStart");
        
        cell = row.createCell(COLUMN_INDEX_SHORTS_PHUTBD);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("MinuteStart");
    }
 
    // Write data
    private static void writeData(tmpObj tmp, Row row) {
        if (cellStyleFormatNumber == null) {
            // Format number
            short format = (short)BuiltinFormats.getBuiltinFormat("#,##0");
            // DataFormat df = workbook.createDataFormat();
            // short format = df.getFormat("#,##0");
             
            //Create CellStyle
            Workbook workbook = row.getSheet().getWorkbook();
            cellStyleFormatNumber = workbook.createCellStyle();
            cellStyleFormatNumber.setDataFormat(format);
        }
         
        Cell cell = row.createCell(COLUMN_INDEX_SHIFT_DATE);
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        String date=f.format(tmp.getDate());
        cell.setCellValue(date);
 
        cell = row.createCell(COLUMN_INDEX_SHIFT_ID_ST);
        cell.setCellValue(tmp.getMaST());
 
        cell = row.createCell(COLUMN_INDEX_SHIFT_NAME);
        cell.setCellValue("Ca "+tmp.getMaCL());
 
        cell = row.createCell(COLUMN_INDEX_SHIFT_NS);
        cell.setCellValue("NS "+tmp.getIdNV());
 
        cell = row.createCell(COLUMN_INDEX_SHORTS_NAME);
        cell.setCellValue("Ca Nhỏ "+tmp.getMaCN());
 
        cell = row.createCell(COLUMN_INDEX_JOB_NAME);
        cell.setCellValue(tmp.getTenCV());
 
        cell = row.createCell(COLUMN_INDEX_JOB_SOPHUT);
        cell.setCellValue(tmp.getTgian());
 
        cell = row.createCell(COLUMN_INDEX_SHORTS_GIOBD);
        cell.setCellValue(tmp.getGioBD());
 
        cell = row.createCell(COLUMN_INDEX_SHORTS_PHUTBD);
        cell.setCellValue(tmp.getPhutBD());
         
    }
 
    // Create CellStyle for header
    private static CellStyle createStyleForHeader(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman"); 
        font.setBold(true);
        font.setFontHeightInPoints((short) 13); // font size
        font.setColor(IndexedColors.WHITE.getIndex()); // text color
 
        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        return cellStyle;
    }
     
    // Auto resize column width
    private static void autosizeColumn(Sheet sheet, int lastColumn) {
        for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }
     
    // Create output file
    private static void createOutputFile(Workbook workbook, String excelFilePath) throws IOException {
        try (OutputStream os = new FileOutputStream(excelFilePath)) {
            workbook.write(os);
        }
    }
 
}
class tmpObj{
    private Date date;
    private int maCL;
    private int maST;
    private int maCN;
    private int idNV;
    private String tenCV;
    private double tgian;
    private int gioBD;
    private int phutBD;

    public tmpObj() {
    }

    public tmpObj(Date date, int maCL, int maST, int maCN, int idNV, String tenCV, double tgian, int gioBD, int phutBD) {
        this.date = date;
        this.maCL = maCL;
        this.maST = maST;
        this.maCN = maCN;
        this.idNV = idNV;
        this.tenCV = tenCV;
        this.tgian = tgian;
        this.gioBD = gioBD;
        this.phutBD = phutBD;
    }

    public int getIdNV() {
        return idNV;
    }

    public void setIdNV(int idNV) {
        this.idNV = idNV;
    }
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMaCL() {
        return maCL;
    }

    public void setMaCL(int maCL) {
        this.maCL = maCL;
    }

    public int getMaST() {
        return maST;
    }

    public void setMaST(int maST) {
        this.maST = maST;
    }

    public int getMaCN() {
        return maCN;
    }

    public void setMaCN(int maCN) {
        this.maCN = maCN;
    }

    public String getTenCV() {
        return tenCV;
    }

    public void setTenCV(String tenCV) {
        this.tenCV = tenCV;
    }

    public double getTgian() {
        return tgian;
    }

    public void setTgian(double tgian) {
        this.tgian = tgian;
    }

    public int getGioBD() {
        return gioBD;
    }

    public void setGioBD(int gioBD) {
        this.gioBD = gioBD;
    }

    public int getPhutBD() {
        return phutBD;
    }

    public void setPhutBD(int phutBD) {
        this.phutBD = phutBD;
    }
    
    
}