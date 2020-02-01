package com.utils;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;

public class ExcelUtility {

    public static Object[][] readXLFile(String xlFileName, String sheetName) {

        Workbook wk = null;

        try {
            wk = new XSSFWorkbook(new File(System.getProperty("user.dir")+xlFileName));
        } catch (InvalidFormatException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Sheet sh = wk.getSheet(sheetName);

        Object[][] xldata = new Object[sh.getLastRowNum()][sh.getRow(0).getLastCellNum()];

        for(int i = 0; i < sh.getLastRowNum();i++) {
            for(int j=0;j<sh.getRow(0).getLastCellNum();j++) {
                xldata[i][j]=sh.getRow(i+1).getCell(j).toString();
            }
        }

        return xldata;

    }





}
