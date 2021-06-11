package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 210610
 * Builds data event id array and calendar date array
 *******************************************************************/
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.HashMap;
class DataCollector
{
    private String thisMatchupID;
    private String homeTeam;
    private String awayTeam;
    private String awayTeamScore;
    private String homeTeamScore;
    private String gameDate;
    private String ouOver;
    private String ouUnder;
    private ArrayList<String> thisWeekGameDates = new ArrayList<String>();
    private HashMap<String, String> thisWeekGameDatesMap= new HashMap<>();
    private ArrayList<String> thisGameWeekNumbers = new ArrayList<String>();
    private ArrayList<String> thisWeekHomeTeamScores = new ArrayList<String>();
    private ArrayList<String> thisWeekAwayTeamScores = new ArrayList<String>();
    private ArrayList<String> thisWeekHomeTeams = new ArrayList<String>();
    private ArrayList<String> thisWeekMatchupIDs = new ArrayList<String>();
    private HashMap<String, String> thisWeekHomeTeamsMap = new HashMap<>();
    private ArrayList<String> thisWeekAwayTeams = new ArrayList<String>();
    private HashMap<String, String> thisWeekAwayTeamsMap = new HashMap<>();
    private ArrayList<String> atsHomes = new ArrayList<String>();
    private HashMap<String, String> atsHomesMap = new HashMap<>();
    private HashMap<String, String> atsAwaysMap = new HashMap<>();
    private HashMap<String, String> ouUndersMap = new HashMap<>();
    private HashMap<String, String> ouOversMap = new HashMap<>();
    private String thisWeek;
    private Elements thisWeekMatchupIdElements;
    private ArrayList<Object> thisSeasonDates;
    private ArrayList<Object> thisSeasonCodes;
    public void collectThisWeek(Elements nflRandomElements)//e.g all week dates for the 2021-2022 season
    {
        thisGameWeekNumbers = new ArrayList<String>();
        thisWeekGameDates = new ArrayList<String>();
        Elements cmg_week_filter_dropdown = nflRandomElements.select("#cmg_week_filter_dropdown");
        Elements options = cmg_week_filter_dropdown.select("Option");
        int i = 0;
        for (Element e : options)
        {
            thisGameWeekNumbers.add(e.text());//e.g.Week 1, Week 2
            thisWeekGameDates.add(e.val());//e.g. 2021-08-05, 2021-08-12
            System.out.println("date => " + e.val() + " week =>" + e.text());
            i++;
        }
    }
    public void collectThisWeekMatchups(Elements thisWeekElements)
    {
        ArrayList<String> thisWeekMatchupIDs = new ArrayList<String>();
        this.thisWeekMatchupIDs = thisWeekMatchupIDs;
        thisWeekMatchupIdElements = thisWeekElements.select(".cmg_matchup_game_box");
        for (Element e : thisWeekMatchupIdElements)//Build week matchup IDs array
        {
            awayTeam = e.attr("data-away-team-fullname-search");
            homeTeam = e.attr("data-home-team-fullname-search");
            thisMatchupID = e.attr("data-event-id");
            gameDate = e.attr("data-game-date");
            String[] gameDateTime = gameDate.split(" ");
            gameDate = gameDateTime[0];
            String gameTime = gameDateTime[1];
            homeTeamScore = e.attr("data-home-score");
            awayTeamScore = e.attr("data-away-score");
            thisWeek = e.attr("data-competition-type");
            thisWeekGameDates.add(gameDate);
            thisWeekGameDatesMap.put(thisMatchupID, gameDate);
            thisWeekHomeTeams.add(homeTeam);
            thisWeekAwayTeams.add(awayTeam);
            thisWeekHomeTeamsMap.put(thisMatchupID, homeTeam);
            thisWeekAwayTeamsMap.put(thisMatchupID, awayTeam);
            thisWeekHomeTeamScores.add(homeTeamScore);
            thisWeekAwayTeamScores.add((awayTeamScore));
            thisGameWeekNumbers.add(thisWeek);
            thisWeekMatchupIDs.add(thisMatchupID);
        }
    }
    public void collectThisSeason(Elements thisSeasonElements)
    {
        thisSeasonDates = new ArrayList<>();
        thisSeasonCodes = new ArrayList<>();
        Elements thisSeason = thisSeasonElements.select(".covers-MatchupFilters-footballDate");
        Elements dates = thisSeason.select("option");
        for (Element e : dates)
        {
            thisSeasonDates.add(e.val());
            thisSeasonCodes.add(e.text());
        }
    }
    public void collectConsensusData(Elements thisMatchupConsensus, String thisMatchupID)
    {
//        Elements rightConsensus = thisMatchupConsensus.select(".covers-CoversConsensusDetailsTable-finalWagersright");
//        Elements leftConsensus = thisMatchupConsensus.select(".covers-CoversConsensusDetailsTable-finalWagersleft");
//        String ouOver = leftConsensus.select("div").get(1).text();
//        String ouUnder = rightConsensus.select("div").get(1).text();
//        String atsAway = leftConsensus.select("div").get(0).text();
//        String atsHome = rightConsensus.select("div").get(0).text();
//        atsHomesMap.put(thisMatchupID, atsAway);
//        atsAwaysMap.put(thisMatchupID, atsHome);
//        ouOversMap.put(thisMatchupID,ouOver);
//        ouUndersMap.put(thisMatchupID, ouUnder);
    }

    public ArrayList<String> getThisWeekMatchupIDs()
    {
        return thisWeekMatchupIDs;
    }
    public HashMap<String, String> getThisWeekHomeTeamsMap()
    {
        return thisWeekHomeTeamsMap;
    }
    public HashMap<String, String> getThisWeekAwayTeamsMap()
    {
        return thisWeekAwayTeamsMap;
    }
    public HashMap<String, String> getThisWeekGameDatesMap()
    {
        return thisWeekGameDatesMap;
    }
    public HashMap<String, String> getAtsHomesMap()
    {
        return atsHomesMap;
    }
    public HashMap<String, String> getAtsAwaysMap()
    {
        return atsAwaysMap;
    }
    public HashMap<String, String> getOuOversMap()
    {
        return ouOversMap;
    }
    public HashMap<String, String> getOuUndersMap()
    {
        return ouUndersMap;
    }
    public ArrayList<Object> getThisSeasonDates()
    {
        return thisSeasonDates;
    }
    public ArrayList<String> getThisWeekGameDates()
    {
        return thisWeekGameDates;
    }
}
