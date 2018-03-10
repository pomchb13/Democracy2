package handler;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class ExcelHandler {


    public static void createExcelFile(String file, TreeMap<String,String> map,int anzSheets) throws IOException {
        Workbook w = new XSSFWorkbook();
        int anzvotersWroteout=1;
        int sheetIndex=0;
        int rowIndex=1;
        LinkedList<Sheet> sheetList = new LinkedList<>();
        for(int i=0;i<anzSheets;i++)
        {
            Sheet sheet = w.createSheet("Users"+i);
            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("ID");
            row.createCell(1).setCellValue("Username");
            row.createCell(2).setCellValue("Password");

            sheetList.add(sheet);

        }

        for(Map.Entry<String,String> entry:map.entrySet())
        {
            Row row = sheetList.get(sheetIndex).createRow(rowIndex);
            row.createCell(0).setCellValue(anzvotersWroteout);
            row.createCell(1).setCellValue(entry.getKey());
            row.createCell(2).setCellValue(entry.getValue());
            if(anzvotersWroteout%1048575==0)
            {
              sheetIndex++;
              rowIndex=1;
            }
            rowIndex++;
            anzvotersWroteout++;
        }


    /*
            Workbook w = new XSSFWorkbook();
            Sheet sheet = w.createSheet("Users");
            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("ID");
            row.createCell(1).setCellValue("Username");
            row.createCell(2).setCellValue("Password");
            int rowIndex = 1;

            for(Map.Entry<String,String> entry:map.entrySet())
            {
            Row r = sheet.createRow(rowIndex);
            r.createCell(0).setCellValue(rowIndex);
            r.createCell(1).setCellValue(entry.getKey());
            r.createCell(2).setCellValue(entry.getValue());
            rowIndex++;

            */
        FileOutputStream out = new FileOutputStream(file);
        w.write(out);
        out.close();
    }

}
