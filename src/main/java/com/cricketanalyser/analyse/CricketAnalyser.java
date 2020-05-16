package com.cricketanalyser.analyse;

import com.censusanalyser.opencsv.CSVBuilderFactory;
import com.censusanalyser.opencsv.ICSVBuilder;
import com.cricketanalyser.adapter.CricketAnalyserFactory;
import com.cricketanalyser.dao.CricketDAO;
import com.cricketanalyser.exception.CricketAnalyserException;
import com.cricketanalyser.model.BattingCSVFile;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class CricketAnalyser {
    List<CricketDAO> cricketList;
    Map<String, CricketDAO> cricketMap;

    public CricketAnalyser() {
    }

    public int loadCricketData(String player, String csvFilePath) throws CricketAnalyserException {
        cricketMap = CricketAnalyserFactory.getCricketData( player, csvFilePath );
        return cricketMap.size();
    }

    public String getFieldWiseData(String fieldType) throws CricketAnalyserException {
        if (cricketMap == null || cricketMap.size() == 0) {
            throw new CricketAnalyserException( "No Cricket Data",
                    CricketAnalyserException.ExceptionType.NO_CRICKET_DATA );
        }
        cricketList = new ArrayList( cricketMap.values() );
        return sortDataJSONFormat( fieldType );
    }

    public String mergeBatsMenBowlerData(String batsMenPath, String bowlerPath, String field) throws CricketAnalyserException {
        Map<String, CricketDAO> batsMenMap =   CricketAnalyserFactory.getCricketData( "BATSMAN", batsMenPath );
        Map<String, CricketDAO> bowlerMenMap =  CricketAnalyserFactory.getCricketData( "BOWLER", bowlerPath );
        cricketList = new ArrayList<>();
        batsMenMap.values().stream().forEach( (batsMen) -> {
            CricketDAO bowler = bowlerMenMap.get( batsMen.player );
            CricketDAO dao = new CricketAnalyserFactory().generateCricketDAO( batsMen, bowler, field );
            if (dao != null) {
                cricketList.add( dao );
            }
        } );
        return sortDataJSONFormat( field );
    }

    private String sortDataJSONFormat(String fieldType) {
        Comparator<CricketDAO> cricketComparator = new CricketAnalyserFactory().getCurrentSort( fieldType );
        cricketList = CricketAnalyserFactory.getFilteredData( cricketList, fieldType );
        List<CricketDAO> sortedList = cricketList.stream()
                .sorted( cricketComparator.reversed() )
                .collect( Collectors.toList() );
        return new Gson().toJson( sortedList );
    }
}
