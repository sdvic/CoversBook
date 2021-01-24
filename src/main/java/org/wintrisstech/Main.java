package org.wintrisstech;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 210123
 *******************************************************************/
public class Main
{
    public static void main(String[] args) throws IOException, ParseException
    {
        new Main().getGoing();
    }
    private void getGoing() throws IOException, ParseException
    {
        String version = "210123";
        String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop */
        String weekNumberString = JOptionPane.showInputDialog("DanPic NFL Analysis Program\nversion"  + version + "\nCopyright 2021 Dan Farris\nEnter week number");
        System.out.println("(1) Hello DanPick, version " + version + ", Copyright 2021 Dan Farris, Processing week " + weekNumberString);
        String gameNumber = "80965";//Week 17
        String nflEvent = "https://contests.covers.com/Consensus/MatchupConsensusDetails?externalId=%2fsport%2ffootball%2fcompetition%3a80965" + gameNumber;
        Document gamePage = Jsoup.connect(nflEvent).get();
        SportDataExcelReader sportDataExcelReader = new SportDataExcelReader(deskTopPath);//Reads sports data xlsx file to hash map
        HashMap sportDataMap = sportDataExcelReader.getSportDataMap();
        XSSFWorkbook sportDataWorkBook = sportDataExcelReader.getSportDataWorkBook();
        MatchupDateReader matchupDateReader = new MatchupDateReader();
        String matchupsDate = matchupDateReader.getMatchupDateWeek(weekNumberString);
        SportsDataAggregator sportsDataAggregator = new SportsDataAggregator(sportDataMap, gamePage, sportDataWorkBook, weekNumberString, matchupsDate);
        String totalHomePicks = sportsDataAggregator.getTotalHomePicks();
        String totalAwayPicks = sportsDataAggregator.getTotalAwayPicks();
        new SportsDataWriter(deskTopPath, sportDataWorkBook, totalHomePicks, totalAwayPicks);
        System.out.print("(11)  Proper Finish...hooray!");
    }
}


