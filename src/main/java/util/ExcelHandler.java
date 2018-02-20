package util;

import beans.User;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;
import java.util.TreeMap;

public class ExcelHandler {

    public static TreeMap<Integer,User> readExcelFile(File file) throws FileNotFoundException, IOException
    {
        TreeMap<Integer, User> map = new TreeMap<>();

        FileInputStream excelFile = new FileInputStream(file);
        Workbook w = new XSSFWorkbook(excelFile);
        Sheet sheet = w.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();
        int x=0;
        String[] str = new String[4];
        int counter;
        boolean isString = false;
        int row=0;
        iterator.next();
        while(iterator.hasNext())
        {
            Row currentRow = iterator.next();
            Iterator<Cell> cellIterator = currentRow.iterator();
            counter=0;
            while(cellIterator.hasNext())
            {
                Cell currCell = cellIterator.next();
                CellType type = currCell.getCellTypeEnum();

                if(type==CellType.NUMERIC)
                {
                    x= (int) currCell.getNumericCellValue();
                }

                if(type==CellType.STRING&&counter!=3)
                {
                    str[counter]=currCell.getStringCellValue();
                    isString=true;
                }
                if(isString){isString=false;counter++;}
            }
            row++;

            map.put(x, new User(str[0],str[1]));

        }
        return map;
    }






    public static void updateExcelFile(File file, TreeMap<Integer,User> map,String path) throws IOException, InvalidFormatException
    {

        Workbook w = new XSSFWorkbook(file);

        Sheet sheet = w.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();
        iterator.next();
        int cellIndex=0;
        int key = 0;
        while(iterator.hasNext())
        {
            Row currentRow = iterator.next();
            Iterator<Cell> cellIterator = currentRow.iterator();

            while(cellIterator.hasNext())
            {
                Cell currCell = cellIterator.next();
                CellType type = currCell.getCellTypeEnum();
                if(type==CellType.NUMERIC&&cellIndex==0)
                {
                    key=(int)currCell.getNumericCellValue();
                }
                cellIndex++;
            }
            Cell c1 = currentRow.createCell(3);
            c1.setCellValue(map.get(key).getUsername());
            Cell c2 = currentRow.createCell(4);
            c2.setCellValue(map.get(key).getPassword());
            cellIndex=0;
        }
        FileOutputStream out = new FileOutputStream(new File(path+"nmappe.xlsx"));
        w.write(out);
        out.close();


    }


}
