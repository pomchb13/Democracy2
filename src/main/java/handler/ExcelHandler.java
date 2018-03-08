package handler;

import beans.User;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class ExcelHandler {

    /**
     * @param file
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Deprecated
    public static TreeMap<Integer, User> readExcelFile(File file) throws IOException {
        TreeMap<Integer, User> map = new TreeMap<>();

        FileInputStream excelFile = new FileInputStream(file);
        Workbook w = new XSSFWorkbook(excelFile);
        Sheet sheet = w.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();
        int x = 0;
        String[] str = new String[4];
        int counter;
        boolean isString = false;
        iterator.next();
        while (iterator.hasNext()) {
            Row currentRow = iterator.next();
            Iterator<Cell> cellIterator = currentRow.iterator();
            counter = 0;
            while (cellIterator.hasNext()) {
                Cell currCell = cellIterator.next();
                CellType type = currCell.getCellTypeEnum();

                if (type == CellType.NUMERIC) {
                    x = (int) currCell.getNumericCellValue();
                }

                if (type == CellType.STRING && counter != 3) {
                    str[counter] = currCell.getStringCellValue();
                    isString = true;
                }
                if (isString) {
                    isString = false;
                    counter++;
                }
            }


            map.put(x, new User(str[0], str[1]));
        }
        return map;
    }

    @Deprecated
    public static void updateExcelFile(File file, TreeMap<Integer, User> map, String path) throws IOException, InvalidFormatException {
        Workbook w = new XSSFWorkbook(file);

        Sheet sheet = w.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();
        iterator.next();
        int cellIndex = 0;
        int key = 0;
        while (iterator.hasNext()) {
            Row currentRow = iterator.next();
            Iterator<Cell> cellIterator = currentRow.iterator();

            while (cellIterator.hasNext()) {
                Cell currCell = cellIterator.next();
                CellType type = currCell.getCellTypeEnum();
                if (type == CellType.NUMERIC && cellIndex == 0) {
                    key = (int) currCell.getNumericCellValue();
                }
                cellIndex++;
            }
            Cell c1 = currentRow.createCell(3);
            c1.setCellValue(map.get(key).getUsername());
            Cell c2 = currentRow.createCell(4);
            c2.setCellValue(map.get(key).getPassword());
            cellIndex = 0;
        }
        FileOutputStream out = new FileOutputStream(new File(path));
        w.write(out);
        out.close();
    }


    public static void createExcelFile(String file, TreeMap<String,String> map,int anzSheets) throws IOException {
        Workbook w = new XSSFWorkbook();
        System.out.println("path: "+file);
        int anzvotersWroteout=1;
        int sheetIndex=0;
        int rowIndex=1;
        LinkedList<Sheet> sheetList = new LinkedList<>();
        System.out.println(anzSheets);
        for(int i=0;i<anzSheets;i++)
        {
            Sheet sheet = w.createSheet("Users"+i);
            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("ID");
            row.createCell(1).setCellValue("Username");
            row.createCell(2).setCellValue("Password");

            sheetList.add(sheet);

        }

        System.out.println("map size: "+map.size());
        for(Map.Entry<String,String> entry:map.entrySet())
        {
            Row row = sheetList.get(sheetIndex).createRow(rowIndex);
            row.createCell(0).setCellValue(anzvotersWroteout);
            row.createCell(1).setCellValue(entry.getKey());
            row.createCell(2).setCellValue(entry.getValue());
            if(anzvotersWroteout%1048575==0)
            {
                System.out.println("asdf");
              sheetIndex++;
              rowIndex=1;
            }
            rowIndex++;
            System.out.println("anzvoter: "+anzvotersWroteout);
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
