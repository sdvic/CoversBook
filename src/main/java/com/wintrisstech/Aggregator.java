package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 210607
 *******************************************************************/
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
    public XSSFWorkbook buildSportDataUpdate(XSSFWorkbook sportDataWorkbook, String dataEventID, int eventIndex)
    {
        eventIndex += 3;
        boolean isUdatePresent = true;
        if (sportDataWorkbook.getSheet("Update") == null)
        {
            out.println("Update sheet does not exist...creating new Update sheet in aportDataWorkbook");
            boolean isUupdatePresent = false;
            sportDataWorkbook.createSheet("Update");
        }
        //sportDataWorkbook.createSheet("Update");
        updateSheet = sportDataWorkbook.getSheet("Update");
        updateSheet.createRow(eventIndex);
        updateSheet.setColumnWidth(1, 25 * 256);
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
        out.println("................................ "+ (eventIndex -1) + " " + dataEventID + " " + homeTeam + " " + awayTeam + " " + thisMatchupDate + " " + atsHome + " " + atsAway + " " + ouOver + " " + ouUnder);
        updateSheet.getRow(eventIndex).createCell(0);
        updateSheet.getRow(eventIndex).getCell(0).setCellStyle(myStyle);
        updateSheet.getRow(eventIndex).getCell(0).setCellValue(awayTeam + " @ " + homeTeam);
        updateSheet.getRow(eventIndex).createCell(1);
        updateSheet.getRow(eventIndex).getCell(1).setCellStyle(myStyle);
        updateSheet.getRow(eventIndex).getCell(1).setCellValue(thisMatchupDate);
        updateSheet.getRow(eventIndex).createCell(59);
        updateSheet.getRow(eventIndex).getCell(59).setCellStyle(myStyle);
        updateSheet.getRow(eventIndex).getCell(59).setCellValue(atsHome);
        updateSheet.getRow(eventIndex).createCell(61);
        updateSheet.getRow(eventIndex).getCell(61).setCellStyle(myStyle);
        updateSheet.getRow(eventIndex).getCell(61).setCellValue(atsAway);
        updateSheet.getRow(eventIndex).createCell(64);
        updateSheet.getRow(eventIndex).getCell(64).setCellStyle(myStyle);
        updateSheet.getRow(eventIndex).getCell(64).setCellValue(ouOver);
        updateSheet.getRow(eventIndex).createCell(66);
        updateSheet.getRow(eventIndex).getCell(66).setCellStyle(myStyle);
        updateSheet.getRow(eventIndex).getCell(66).setCellValue(ouUnder);
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
