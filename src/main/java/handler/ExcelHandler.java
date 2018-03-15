package handler;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Author:          Christoph Pommer
 * Description:     This class is responsible for writing the username and password into an excel file
 */
public class ExcelHandler {


    /**
     * Method creates the excel file
     * uses the apache poi library
     * votersWroteOutCount --> counts the voters and gives them an ID
     * the first for-loop creates the needed amount of sheets in the excel file
     * creates it with a header row to know which column is what
     * the second for-loop goes over the user login map
     * it writes the voterswrotecount (id), the username and the password down
     * the if is used to increase the sheet index when the row limit is reached
     * at the end the file gets wrote out and saved
     *
     * @param file       where the new excel file gets saved
     * @param map        map with all user logins
     * @param sheetCount count of needed sheets because excel can only have 1048000 million rows in one sheet
     * @throws IOException throws when the path or the file can't be created on the given path
     */
    public static void createExcelFile(String file, TreeMap<String, String> map, int sheetCount) throws IOException {
        Workbook w = new XSSFWorkbook();
        int votersWroteOutCount = 1;
        int sheetIndex = 0;
        int rowIndex = 1;
        LinkedList<Sheet> sheetList = new LinkedList<>();
        for (int i = 0; i < sheetCount; i++) {
            Sheet sheet = w.createSheet("Users" + i);
            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("ID");
            row.createCell(1).setCellValue("Username");
            row.createCell(2).setCellValue("Password");
            sheetList.add(sheet);
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            Row row = sheetList.get(sheetIndex).createRow(rowIndex);
            row.createCell(0).setCellValue(votersWroteOutCount);
            row.createCell(1).setCellValue(entry.getKey());
            row.createCell(2).setCellValue(entry.getValue());
            if (votersWroteOutCount % 1048575 == 0) {
                sheetIndex++;
                rowIndex = 1;
            }
            rowIndex++;
            votersWroteOutCount++;
        }
        FileOutputStream out = new FileOutputStream(file);
        w.write(out);
        out.close();
    }

}
