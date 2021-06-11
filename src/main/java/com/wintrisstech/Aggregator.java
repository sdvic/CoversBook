package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 210610
 *******************************************************************/
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.HashMap;

import static java.lang.System.out;
public class Aggregator
{
    private String ouUnder;
    private String home;
    private String away;
    private String ouOver;
    private String dataEventID;
    private String homeTeam;
    private String awayTeam;
    private int gameCount = 7;
    private final int rowOffset = 3;
    private String thisMatchupDate;
    private int numberOfGamesThisWeek;
    private String nflSeasonYear;
    private Document thisWeekGameDoc;
    private Elements thisWeekElements;
    private Document nflRandomDoc;
    private String gameDate;
    private HashMap<String, String> weekList = new HashMap<>();
    private HashMap<String, String> thisWeekHomeTeamsMap = new HashMap<>();
    private HashMap<String, String> thisWeekAwayTeamsMap = new HashMap<>();
    private HashMap<String, String> thisWeekGameDatesMap = new HashMap<>();
    private HashMap<String, String> atsHomesMap = new HashMap<>();
    private HashMap<String, String> atsAwaysMap = new HashMap<>();
    private Sheet updateSheet;
    private XSSFWorkbook sportDataWorkBook = new XSSFWorkbook();
    private XSSFWorkbook updatedSportDataWorkBook;
    private WebSiteReader websiteReader;
    private SportDataReader sportDataReader;
    private XSSFSheet sportDataUpdateSheet = null;
    byte[] rgb = new byte[]{(byte) 255, (byte) 0, (byte) 0};
    private String atsHome;
    private String atsAway;
    private HashMap<String, String> ouOversMap;
    private HashMap<String, String> ouUndersMap;
    private int masterIndex;
    public XSSFWorkbook buildSportDataUpdate(XSSFWorkbook sportDataWorkbook, String dataEventID, int weekIndex, int eventIndex)
    {
        this.masterIndex = weekIndex + eventIndex + 3;
        if (sportDataWorkbook.getSheet("Update") == null)
        {
            out.println("Update sheet does not exist...creating new Update sheet in aportDataWorkbook");
            sportDataWorkbook.createSheet("Update");
        }
        updateSheet = sportDataWorkbook.getSheet("Update");
        updateSheet.createRow(masterIndex);
        updateSheet.setColumnWidth(0, 20 * 256);
        updateSheet.setColumnWidth(1, 13 * 256);
        CellStyle myStyle = sportDataWorkbook.createCellStyle();
        Font myFont = sportDataWorkbook.createFont();
        myFont.setBold(true);
        myStyle.setFont(myFont);
        XSSFFont xssfFont = (XSSFFont) myFont;
        xssfFont.setColor(new XSSFColor(rgb, null));
        homeTeam = thisWeekHomeTeamsMap.get(dataEventID);
        awayTeam = thisWeekAwayTeamsMap.get(dataEventID);
        thisMatchupDate = thisWeekGameDatesMap.get(dataEventID);
        atsHome = atsHomesMap.get(dataEventID);
        atsAway = atsAwaysMap.get(dataEventID);
        ouOver = ouOversMap.get(dataEventID);
        ouUnder = ouUndersMap.get(dataEventID);
        out.println("................................ "+ masterIndex + " " + dataEventID + " " + homeTeam + " " + awayTeam + " " + thisMatchupDate + " " + atsHome + " " + atsAway + " " + ouOver + " " + ouUnder);
        updateSheet.getRow(masterIndex).createCell(0);
        updateSheet.getRow(masterIndex).getCell(0).setCellStyle(myStyle);
        updateSheet.getRow(masterIndex).getCell(0).setCellValue(awayTeam + " @ " + homeTeam);
        updateSheet.getRow(masterIndex).createCell(1);
        updateSheet.getRow(masterIndex).getCell(1).setCellStyle(myStyle);
        updateSheet.getRow(masterIndex).getCell(1).setCellValue(thisMatchupDate);
        updateSheet.getRow(masterIndex).createCell(59);
        updateSheet.getRow(masterIndex).getCell(59).setCellStyle(myStyle);
        updateSheet.getRow(masterIndex).getCell(59).setCellValue(atsHome);
        updateSheet.getRow(masterIndex).createCell(61);
        updateSheet.getRow(masterIndex).getCell(61).setCellStyle(myStyle);
        updateSheet.getRow(masterIndex).getCell(61).setCellValue(atsAway);
        updateSheet.getRow(masterIndex).createCell(64);
        updateSheet.getRow(masterIndex).getCell(64).setCellStyle(myStyle);
        updateSheet.getRow(masterIndex).getCell(64).setCellValue(ouOver);
        updateSheet.getRow(masterIndex).createCell(66);
        updateSheet.getRow(masterIndex).getCell(66).setCellStyle(myStyle);
        updateSheet.getRow(masterIndex).getCell(66).setCellValue(ouUnder);
        XSSFRow srcRow = sportDataWorkbook.getSheet("Sheet1").getRow(0);//copyRowFrom() workaround
        XSSFRow destRow = (XSSFRow) updateSheet.createRow(1);
        destRow.copyRowFrom(srcRow, new CellCopyPolicy());
        srcRow = sportDataWorkbook.getSheet("Sheet1").getRow(1);
        destRow = (XSSFRow) updateSheet.createRow(2); //this works
        destRow.copyRowFrom(srcRow, new CellCopyPolicy());
        srcRow = sportDataWorkbook.getSheet("Sheet1").getRow(2);
        destRow = (XSSFRow) updateSheet.createRow(3); //this works
        destRow.copyRowFrom(srcRow, new CellCopyPolicy());
        updateSheet.createRow(0);
        updateSheet.getRow(0).createCell(0);
        updateSheet.getRow(0).getCell(0).setCellValue("Updated Sport Data Sheet");
        return sportDataWorkbook;
    }
    public void setThisWeekHomeTeamsMap(HashMap<String, String> thisWeekHomeTeamsMap){this.thisWeekHomeTeamsMap = thisWeekHomeTeamsMap;}
    public void setThisWeekAwayTeamsMap(HashMap<String, String> thisWeekAwayTeamsMap){this.thisWeekAwayTeamsMap = thisWeekAwayTeamsMap;}
    public void setThisWeekGameDatesMap(HashMap<String, String> thisWeekGameDatesMap) {this.thisWeekGameDatesMap = thisWeekGameDatesMap;}
    public void setAtsHomesMap(HashMap<String, String> atsHomes)
    {
        this.atsHomesMap = atsHomes;
    }
    public void setAtsAwaysMap(HashMap<String, String> atsAwayMap)
    {
        this.atsAwaysMap = atsAwayMap;
    }
    public void setOuOversMap(HashMap<String, String> ouOversMap)
    {
        this.ouOversMap = ouOversMap;
    }
    public void setOuUndersMap(HashMap<String, String> ouUndersMap)
    {
        this.ouUndersMap = ouUndersMap;
    }
}
