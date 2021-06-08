package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2021 Dan Farris
 * version 210608
 * * Launch with Covers.command
 *******************************************************************/
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.select.Elements;
import javax.swing.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
public class Main extends JComponent
{
    private static String version = "210608";
    private String nflRandomWeekURL = "https://www.covers.com/sports/nfl/matchups";
    private XSSFWorkbook sportDataWorkbook;
    private String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop path */
    private HashMap<String, String> weekList = new HashMap<>();
    private InfoPrinter infoPrinter = new InfoPrinter();
    public DataCollector dataCollector = new DataCollector();
    public WebSiteReader webSiteReader = new WebSiteReader();
    public SportDataReader sportDataReader = new SportDataReader();
    public Aggregator aggregator = new Aggregator();
    public SportDataWriter sportDataWriter = new SportDataWriter();
    private Elements thisWeekElements;
    private Elements nflHistoryElements;
    private String thisWeek;
    private Elements thisMatchupConsensusElements;
    public static void main(String[] args) throws IOException, ParseException
    {
        System.out.println("(1) Starting SharpMarkets, version " + version + ", Copyright 2021 Dan Farris");
        Main main = new Main();
        main.getGoing();
    }
    private void getGoing() throws IOException
    {
        String thisSeason;// = JOptionPane.showInputDialog("NFL Season (2yyy)?");
        thisSeason = "2020";
        thisWeek = "2020-09-10";
        nflHistoryElements = webSiteReader.readCleanWebsite("https://www.covers.com/sports/nfl/matchups?selectedDate=" + thisSeason);//Has all NFL season beginning date history and this season year info from https://www.covers.com/sports/nfl/matchups?selectedDate=thisSeasonYear
        dataCollector.collectAllSeasonDates(nflHistoryElements);//Builds a String array of all past and current NFL season year dates available from Covers.com
        thisWeekElements = webSiteReader.readCleanWebsite("https://www.covers.com/sports/nfl/matchups?selectedDate=" + thisWeek);//Get all of this week's games info
        dataCollector.collectThisSeasonWeeks(nflHistoryElements);
        dataCollector.collectThisWeekMatchups(thisWeekElements);
        sportDataWorkbook = sportDataReader.readSportData();
        int i =3;
        for (String s : dataCollector.getThisWeekMatchupIDs())
        {
            String thisMatchupID = dataCollector.getThisWeekMatchupIDs().get(i-3);//Get this matchup ID...used as key for all data retrieval
            thisMatchupConsensusElements = webSiteReader.readCleanWebsite("https://contests.covers.com/consensus/matchupconsensusdetails?externalId=%2fsport%2ffootball%2fcompetition%3a" + thisMatchupID);
            dataCollector.collectConsensusData(thisMatchupConsensusElements, thisMatchupID);
            aggregator.setThisWeekAwayTeamsMap(dataCollector.getThisWeekAwayTeamsMap());
            aggregator.setThisWeekHomeTeamsMap(dataCollector.getThisWeekHomeTeamsMap());
            aggregator.setThisWeekGameDatesMap(dataCollector.getThisWeekGameDatesMap());
            aggregator.setAtsHomesMap(dataCollector.getAtsHomesMap());
            aggregator.setAtsAwaysMap(dataCollector.getAtsAwaysMap());
            aggregator.setOuOversMap(dataCollector.getOuOversMap());
            aggregator.setOuUndersMap(dataCollector.getOuUndersMap());
            aggregator.buildSportDataUpdate(sportDataWorkbook, thisMatchupID, i);
            sportDataWriter.writeSportData(sportDataWorkbook);
            i++;
            if (i > 5) break;
        }
        sportDataWriter.writeSportData(sportDataWorkbook);
        System.out.print("(11)  Proper Finish...hooray!");
    }
}
