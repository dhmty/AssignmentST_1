/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MIP;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
        
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author dhmty
 */
public class importExcel {

    public static final int COLUMN_INDEX_SHIFT_DATE = 0;
    public static final int COLUMN_INDEX_SHIFT_ID_ST = 1;
    public static final int COLUMN_INDEX_SHIFT_ID = 2;
    public static final int COLUMN_INDEX_SHIFT_NAME = 4;
    public static final int COLUMN_INDEX_SHIFT_HEADCOUNT = 5;
    public static final int COLUMN_INDEX_SHIFT_TIME = 7;
    public static final int COLUMN_INDEX_SHORTS_ID = 3;
    public static final int COLUMN_INDEX_SHORTS_TIME = 6;
    public static final int COLUMN_INDEX_SHORTS_GIOBD = 13;
    public static final int COLUMN_INDEX_SHORTS_GIOKT = 15;
    public static final int COLUMN_INDEX_SHORTS_PHUTBD = 14;
    public static final int COLUMN_INDEX_SHORTS_PHUTKT = 16;
    public static final int COLUMN_INDEX_JOB_NAME = 8;
    public static final int COLUMN_INDEX_JOB_LOAI = 9;
    public static final int COLUMN_INDEX_JOB_TINHCHAT = 10;
    public static final int COLUMN_INDEX_JOB_SONGUOI = 11;
    public static final int COLUMN_INDEX_JOB_SOPHUT = 12;
    
    public static void main(String args[]) throws IOException {
        final String excelFilePath = "data_Import/TemplateImport_CalAssignmentData_Fresher.xlsx";
        final List<shiftModel> listShift =readExcel(excelFilePath);
        
        System.out.println(listShift.size());
        for (shiftModel sh : listShift) {
            System.out.println(sh.toString());
            for (shortShift ss:sh.listSS.dsShortList){
                System.out.println(ss.toString());
                ss.listJob.show();
            }
        }
    }
    public static List<shiftModel> readExcel(String excelFilePath) throws IOException {
        List<shiftModel> listShift = new ArrayList<>();
        List<shortShift> listSS = new ArrayList<>();
        List<job> listJob = new ArrayList<>();
        
        int sl=0;
        // Get file
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
 
        // Get workbook
        Workbook workbook = getWorkbook(inputStream, excelFilePath);
 
        // Get sheet
        Sheet sheet = workbook.getSheetAt(0);
 
        // Get all rows
        Iterator<Row> iterator = sheet.iterator();
   
        // kiểm tra để thêm vào listShift
        
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            if (nextRow.getRowNum() == 0) {
                // Ignore header
                continue;
                
            }

            shiftModel sm = new shiftModel();
            shortShift ss=new shortShift();
            job jb=new job();
          
                // Get all cells
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            while (cellIterator.hasNext()) {
                //Read cell
                    Cell cell = cellIterator.next();
                    Object cellValue = getCellValue(cell);
                    if (cellValue == null || cellValue.toString().isEmpty()) {
                            continue;
                    }
                    // Set value for book object
                    int columnIndex = cell.getColumnIndex();
                    switch (columnIndex) {
                        case COLUMN_INDEX_SHIFT_DATE -> {
                                 sm.setDate((Date) getCellValue(cell));
                                 ss.setDate((Date) getCellValue(cell));
                                 jb.setDate((Date) getCellValue(cell));
                                
                        }
                        case COLUMN_INDEX_SHIFT_ID_ST -> {
                                 sm.setMaST(new BigDecimal((double) cellValue).intValue());
                                 ss.setMaST(new BigDecimal((double) cellValue).intValue());
                                 jb.setMaST(new BigDecimal((double) cellValue).intValue());
                        }
                        case COLUMN_INDEX_SHIFT_ID -> {
                                 sm.setMaCL(new BigDecimal((double) cellValue).intValue());
                                 ss.setMaCL(new BigDecimal((double) cellValue).intValue());
                                 jb.setMaCL(new BigDecimal((double) cellValue).intValue());
                        }
                        case COLUMN_INDEX_SHIFT_NAME -> {
                                 sm.setTenCa(String.valueOf(getCellValue(cell))); 
                        }
                        case COLUMN_INDEX_SHIFT_HEADCOUNT -> {
                                 sm.setHeadCount(new BigDecimal((double) cellValue).intValue());
                        }
                        case COLUMN_INDEX_SHIFT_TIME ->{
                                 sm.setTgian(new BigDecimal((double) cellValue).intValue());
                        } 

                       //  xử lý các ca nhỏ trong ca lớn -> chạy vòng -> có hàm ktra không trùng
                        case COLUMN_INDEX_SHORTS_ID ->{
                                ss.setMaCN(new BigDecimal((double) cellValue).intValue());
                                jb.setMaCN(new BigDecimal((double) cellValue).intValue());
                        }
                        case COLUMN_INDEX_SHORTS_TIME ->{
                                ss.setTgian(new BigDecimal((double) cellValue).intValue());
                        }
                        case COLUMN_INDEX_SHORTS_GIOBD ->{
                                ss.setGio_bd(new BigDecimal((double) cellValue).intValue());
                        }
                        case COLUMN_INDEX_SHORTS_PHUTBD ->{
                                ss.setPhut_bd(new BigDecimal((double) cellValue).intValue());
                        }
                        case COLUMN_INDEX_SHORTS_GIOKT ->{
                                ss.setGio_kt(new BigDecimal((double) cellValue).intValue());
                        }
                        case COLUMN_INDEX_SHORTS_PHUTKT ->{
                                ss.setPhut_kt(new BigDecimal((double) cellValue).intValue());
                        }
                       //  xử lý các công việc trong ca nhỏ -> chạy vòng -> có hàm ktra không trùng
                        case COLUMN_INDEX_JOB_NAME ->{
                                jb.setTen(String.valueOf(getCellValue(cell)));
                        }
                        case COLUMN_INDEX_JOB_LOAI ->{
                                jb.setLoai(new BigDecimal((double) cellValue).intValue());
                        }
                        case COLUMN_INDEX_JOB_TINHCHAT ->{
                                jb.setTinhChat(new BigDecimal((double) cellValue).intValue());
                        }
                        case COLUMN_INDEX_JOB_SONGUOI ->{
                                jb.setSoNg(new BigDecimal((double) cellValue).intValue());
                        }
                        case COLUMN_INDEX_JOB_SOPHUT ->{
                                jb.setSoPh(new BigDecimal((double) cellValue).intValue());
                        }
                        default -> {
                                }
                    }

            }
                   listJob.add(jb);
                   listSS.add(ss);
                   listShift.add(sm);
                   sl++;
        }
        // thu gọn các ca trong lshift
        for (int i=0; i<listShift.size()-1;i++){
            for (int j=i+1; j<listShift.size();j++){
               if (listShift.get(i).getDate().equals(listShift.get(j).getDate()) && listShift.get(i).getMaST()==listShift.get(j).getMaST() && listShift.get(i).getMaCL()==listShift.get(j).getMaCL()){
                    listShift.remove(listShift.get(j));
                    j--;
               }
            }
        }
       //thu gonj cac ca nho
        for (int i=0; i<listSS.size()-1;i++){
            for (int j=i+1; j<listSS.size();j++){
               if (listSS.get(i).getDate().equals(listSS.get(j).getDate()) && listSS.get(i).getMaST()==listSS.get(j).getMaST() && listSS.get(i).getMaCL()==listSS.get(j).getMaCL()&& listSS.get(i).getMaCN()==listSS.get(j).getMaCN()){
                    listSS.remove(listSS.get(j));
                    j--;
               }
            }
        }
//        listJob.forEach(jb -> {
//            System.out.println(jb.toString());
//        });
//        
        
        // tong hop lai
        // bỏ công việc vào các ca nhỏ
        listSS.forEach(ss->{
             listJob.forEach(jb->{
                 if (ss.getDate().equals(jb.getDate()) && ss.getMaST()==jb.getMaST() && ss.getMaCL()==jb.getMaCL() && ss.getMaCN()==jb.getMaCN()){
                    ss.listJob.dsJob.add(jb);
                 }
             });
        });
        // rải các ca nhỏ vào ca lớn tương ứng
       for (int i=0; i<listShift.size();i++){
          for (int j=0; j<listSS.size();j++){
               if (listShift.get(i).getDate().equals(listSS.get(j).getDate()) && listShift.get(i).getMaST()==listSS.get(j).getMaST() && listShift.get(i).getMaCL()==listSS.get(j).getMaCL()){
                    listShift.get(i).listSS.dsShortList.add(listSS.get(j));
               }
            }
      }
        workbook.close();
        inputStream.close();
        System.out.println(sl);
        return listShift;
    }
 
    // Get Workbook
    private static Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
        Workbook workbook = null;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }
 
        return workbook;
    }
 
    // Get cell value
    private static Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        Object cellValue = null;
        switch (cellType) {
        case BOOLEAN:
            cellValue = cell.getBooleanCellValue();
            break;
        case FORMULA:
            Workbook workbook = cell.getSheet().getWorkbook();
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            cellValue = evaluator.evaluate(cell).getNumberValue();
            break;
        case NUMERIC:
            if (DateUtil.isCellDateFormatted(cell)) {
			cellValue = cell.getDateCellValue();
            } else {
			cellValue = cell.getNumericCellValue();
                    }
            break;
        case STRING:
            cellValue = cell.getStringCellValue();
            break;
        case _NONE:
        case BLANK:
        case ERROR:
            break;
        default:
            break;
        }
 
        return cellValue;
    }
}

