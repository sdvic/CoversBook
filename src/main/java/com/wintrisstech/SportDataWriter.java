package com.wintrisstech;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.OutputStream;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 21606
 * write new NFL Covers data to the large SportData Excel sheet
 *******************************************************************/
public class SportDataWriter
{
    private String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop path */
    private XSSFWorkbook sportDataWorkbook;
    private XSSFWorkbook workbook;
    private OutputStream os;
    public void writeSportData(XSSFWorkbook sportDataWorkbook)
    {
        try
        {
            os = new FileOutputStream(deskTopPath + "/SportData.xlsx");
            sportDataWorkbook.write(os);
            os.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
