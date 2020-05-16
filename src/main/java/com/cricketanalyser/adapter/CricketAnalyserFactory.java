package com.cricketanalyser.adapter;

import com.cricketanalyser.dao.CricketDAO;
import com.cricketanalyser.exception.CricketAnalyserException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CricketAnalyserFactory {
    public static Map<String, CricketDAO> getCricketData(String player,
                                                         String csvFilePath) throws CricketAnalyserException {
        if (player.equals( "BATSMAN" )) {
            return new BatsmanAdapter().loadCricketData( csvFilePath );
        } else if (player.equals( "BOWLER" )) {
            return new BowlerAdapter().loadCricketData( csvFilePath );
        } else {
            throw new CricketAnalyserException( "Incorrect Player", CricketAnalyserException.ExceptionType.CRICKET_FILE_INTERNAL_ISSUE );
        }
    }

    public static List<CricketDAO> getFilteredData(List<CricketDAO> cricketList, String fieldType) {
        switch (fieldType) {
            case "average":
                cricketList = cricketList.stream()
                        .filter( cricket -> cricket.getAverage() > 0 )
                        .collect( Collectors.toList() );
                break;

            case "strike_rate":
                cricketList = cricketList.stream()
                        .filter( cricket -> cricket.getStrikeRate() > 0 )
                        .collect( Collectors.toList() );
                break;

            case "strike_and_six_four":
                cricketList = cricketList.stream()
                        .filter( cricket -> cricket.getRuns() > 100 )
                        .collect( Collectors.toList() );
                break;

            case "avg_and_strike_rate":
                cricketList = cricketList.stream()
                        .filter( cricket -> cricket.getStrikeRate()
                                > 0 && cricket.getAverage() > 0)
                        .collect( Collectors.toList() );
                break;

            case "strike_and_wickets":
                cricketList = cricketList.stream()
                        .filter( cricket -> cricket.getSumWickets()
                                > 0 && cricket.getEconomy() < 10 )
                        .collect( Collectors.toList() );
                break;

            case "wicket_average":
                cricketList = cricketList.stream()
                        .filter( cricket -> cricket.getMatches() > 15 )
                        .collect( Collectors.toList() );
                break;

            case "bowler_avg_and_strike_rate":
                cricketList = cricketList.stream()
                        .filter( cricket -> cricket.getAverage()
                                > 0 && cricket.getMatches() > 10 )
                        .collect( Collectors.toList() );
                break;
        }
        return cricketList;
    }

    public Comparator<CricketDAO> getCurrentSort(String field) {
        Comparator<CricketDAO> comparator = null;
        switch (field) {
            case "strike_rate":
                comparator = Comparator.comparing( cricket -> cricket.strikeRate );
                break;

            case "average":
                comparator = Comparator.comparing( cricket -> cricket.average );
                break;

            case "six_four":
                comparator = Comparator.comparing( cricket -> cricket.sumSixFour );
                break;

            case "strike_and_six_four":
                comparator = Comparator.comparing( CricketDAO::getSumSixFour )
                        .thenComparing( CricketDAO::getStrikeRate );
                break;

            case "avg_and_strike_rate":
                comparator = Comparator.comparing( CricketDAO::getStrikeRate )
                        .thenComparing( CricketDAO::getAverage );
                break;

            case "runs_and_avg":
                comparator = Comparator.comparing( CricketDAO::getRuns )
                        .thenComparing( CricketDAO::getAverage );
                break;
            case "economy":
                comparator = Comparator.comparing( cricket -> cricket.economy );
                break;

            case "strike_and_wickets":
                comparator = Comparator.comparing( CricketDAO::getStrikeRate )
                        .thenComparing( CricketDAO::getSumWickets );
                break;

            case "wicket_average":
                comparator = Comparator.comparing( CricketDAO::getSumWickets )
                        .thenComparing( CricketDAO::getAverage );
                break;

            case "batsman_bowler_average":
                comparator = Comparator.comparing( CricketDAO::getBatsmenAvg )
                        .thenComparing( CricketDAO::getBowlerAvg );
                break;

            case "all_rounder":
                comparator = Comparator.comparing( CricketDAO::getRuns)
                        .thenComparing( CricketDAO::getSumWickets);
                break;
        }
        return comparator;
    }

    public <E> void getCricketObject(Class<E> cricketClass, Map cricketMap, Object cricketCSV) {
        try {
            Field field = cricketClass.getDeclaredField( "player" );
            Class<?> cricketDAO = Class.forName( "com.cricketanalyser.dao.CricketDAO" );
            Constructor<?> cricketConstructor = cricketDAO.getConstructor( cricketClass );
            String value = (String) field.get( cricketCSV );
            cricketMap.put( value, cricketConstructor.newInstance( cricketCSV ) );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CricketDAO generateCricketDAO(CricketDAO batsMen, CricketDAO bowler,String field) {

        if(field.equals( "batsman_bowler_average" )) {
            double batsmenAvg = batsMen.average;
            double bowlerAvg = bowler == null ? 0 : bowler.average;
            if (batsmenAvg != 0 && bowlerAvg != 0)
                return new CricketDAO( batsMen,bowler);
        }

        if(field.equals( "all_rounder" )) {
            double batsmenRuns = batsMen.runs;
            double bowlerWickets = bowler == null ? 0 : bowler.sumWickets;
            if (batsmenRuns != 0 && bowlerWickets != 0)
                return new CricketDAO( batsMen,bowler);
        }
        return null;
    }
}
