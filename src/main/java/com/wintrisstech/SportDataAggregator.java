package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 210401
 *******************************************************************/
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import static org.jsoup.helper.HttpConnection.connect;
public class SportDataAggregator
{
    private String under;
    private String home;
    private String away;
    private String over;
    private String dataEventID;
    private String homeTeam;
    private String awayTeam;
    private int gameIndex;
    private int gameCount;
    private final int rowOffset = 3;
    private String thisGameDate;
    private XSSFWorkbook sportDataWorkBook;
    public String version;
    private int numberOfGamesThisWeek;
    private String nflSeasonYear;
    Document thisWeekGameDoc;
    Elements nflRandomElements;
    Document nflRandomDoc;
    String gameWeek;
    String gameDate;
    private final HashMap<String, String> weekList = new HashMap<>();
    private Sheet sportDataSheet;
    public void aggregateSportsData(Object[] nflDocsAndElements, Workbook sportDataWorkBook, HashMap weekList) throws IOException//nflDocsAndElements has both nflDocument[0] and nflElements[1]
    {
        sportDataSheet = sportDataWorkBook.getSheetAt(0);
        nflRandomDoc = (Document) nflDocsAndElements[0];
        nflRandomElements = (Elements) nflDocsAndElements[1];
        numberOfGamesThisWeek = weekList.size();
        for (gameIndex = 0; gameIndex < 3; gameIndex++)
        {
            System.out.println("(4) Start aggregating Covers info, game " + (gameIndex) + " of " + numberOfGamesThisWeek);//game number this week counter...int j is zero based
            thisGameDate = gameDate;//This game date
            dataEventID = nflRandomElements.get(gameIndex).attr("data-event-id");//two team event number on particular date...int i is 1 based TODO get data event ID
            System.out.println("dataEventID => " + dataEventID);
            homeTeam = nflRandomElements.get(gameIndex).attr("data-home-team-fullname-search");
            awayTeam = nflRandomElements.get(gameIndex).attr("data-away-team-fullname-search");
            System.out.println("home-team => " + homeTeam);
            System.out.println("away-team => " + awayTeam);
            Document silver = connect("https://contests.covers.com/Consensus/MatchupConsensusDetails?externalId=%2fsport%2ffootball%2fcompetition%3a80950").get();
            Elements rightConsensus = silver.getElementsByClass("covers-CoversConsensusDetailsTable-finalWagersright");
            Elements leftConsensus = silver.getElementsByClass("covers-CoversConsensusDetailsTable-finalWagersleft");
            away = leftConsensus.get(0).text();
            over = leftConsensus.get(1).text();
            under = rightConsensus.get(1).text();
            home = rightConsensus.get(0).text();
            byte[] rgb = new byte[]{(byte) 255, (byte) 0, (byte) 0};
            CellStyle myStyle = sportDataWorkBook.createCellStyle();
            Font myFont = sportDataWorkBook.createFont();
            myFont.setBold(true);
            myStyle.setFont(myFont);
            XSSFFont xssfFont = (XSSFFont) myFont;
            xssfFont.setColor(new XSSFColor(rgb, null));//Load new values into SportData.xlsx sheet
            sportDataSheet.getRow(0).getCell(0).setCellStyle(myStyle);
            sportDataSheet.getRow(0).getCell(0).setCellValue("Updated " + new Date().toString());
            sportDataSheet.getRow(rowOffset + gameCount).getCell(0).setCellStyle(myStyle);
            sportDataSheet.getRow(rowOffset + gameCount).getCell(0).setCellValue(awayTeam + " @ " + homeTeam);
            sportDataSheet.getRow(rowOffset + gameCount).getCell(1).setCellStyle(myStyle);
            sportDataSheet.getRow(rowOffset + gameCount).getCell(1).setCellValue(thisGameDate);
            sportDataSheet.getRow(rowOffset + gameCount).getCell(2).setCellStyle(myStyle);
            //nflSeasonYear = matchupsCalendarDate.substring(0,4);
            sportDataSheet.getRow(rowOffset + gameCount).getCell(2).setCellValue(nflSeasonYear);
            sportDataSheet.getRow(rowOffset + gameCount).getCell(3).setCellStyle(myStyle);
            //sportDataSheet.getRow(rowOffset + gameCount).getCell(3).setCellValue("week" + weekNumberString);
            sportDataSheet.getRow(rowOffset + gameCount).getCell(59).setCellStyle(myStyle);
            sportDataSheet.getRow(rowOffset + gameCount).getCell(59).setCellValue(home);//BH60
            sportDataSheet.getRow(rowOffset + gameCount).getCell(61).setCellStyle(myStyle);
            sportDataSheet.getRow(rowOffset + gameCount).getCell(61).setCellValue(away);//BJ62
            sportDataSheet.getRow(rowOffset + gameCount).getCell(64).setCellStyle(myStyle);
            sportDataSheet.getRow(rowOffset + gameCount).getCell(64).setCellValue(over);//BM65
            sportDataSheet.getRow(rowOffset + gameCount).getCell(66).setCellStyle(myStyle);
            sportDataSheet.getRow(rowOffset + gameCount).getCell(66).setCellValue(under);//BO67
            //JOptionPane.showMessageDialog(null, "Week " + weekNumberString + "\n" + "Week Date " + matchupsCalendarDate + "\n" + "Game Date " + thisGameDate + "\n" + awayTeam + " at " + homeTeam + "\nOver " + getOver() + "\nUnder " + getUnder() + "\nHome " + getHome() + "\nAway " + getAway(), "Sharp Markets version " + version, JOptionPane.INFORMATION_MESSAGE);
            gameCount++;
        }
    }
    public String getUnder()
    {
        return under;
    }
    public String getOver()
    {
        return over;
    }
    public String getHomeTeam()
    {
        return homeTeam;
    }
    public String getAwayTeam()
    {
        return awayTeam;
    }
    public String getDataEventID() {return dataEventID; }
    public String getAway()
    {
        return away;
    }
    public String getHome()
    {
        return home;
    }
    public void setSportDataWorkBook(XSSFWorkbook sportDataWorkBook) {this.sportDataWorkBook = sportDataWorkBook;}
    public void setThisGameDate(String thisGameDate)
    {
        this.thisGameDate = thisGameDate;
    }
    public void setVersion(String version) {this.version = this.version;}
}
