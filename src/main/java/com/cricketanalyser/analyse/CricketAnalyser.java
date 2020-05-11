package com.cricketanalyser.analyse;

import com.censusanalyser.opencsv.CSVBuilderFactory;
import com.censusanalyser.opencsv.ICSVBuilder;
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
        this.cricketList = new ArrayList<>();
        this.cricketMap = new HashMap<>();
    }
    public int loadCricketData(String csvFilePath) throws CricketAnalyserException {
        try {
            Reader reader = Files.newBufferedReader( Paths.get( csvFilePath ) );
            ICSVBuilder csvBuilderInterface = CSVBuilderFactory.createCSVBuilder();
            List<BattingCSVFile> csvFileList = csvBuilderInterface.getCSVFileList( reader, BattingCSVFile.class );
            csvFileList.forEach( list -> cricketMap.put( list.player, new CricketDAO( list ) ) );
            return cricketMap.size();
        } catch (RuntimeException e) {
            throw new CricketAnalyserException( "No Cricket Data is present",CricketAnalyserException.ExceptionType.CRICKET_FILE_INTERNAL_ISSUE);
        } catch (IOException e) {
            throw new CricketAnalyserException( "No File Present",CricketAnalyserException.ExceptionType.CRICKET_FILE_PROBLEM );
        }
    }

    public String getFieldWiseData(String fieldType) throws CricketAnalyserException {
        if (cricketMap == null || cricketMap.size() == 0) {
            throw new CricketAnalyserException( "No Cricket Data", CricketAnalyserException.ExceptionType.NO_CRICKET_DATA );
        }
        cricketList = new ArrayList( cricketMap.values() );
        Comparator<CricketDAO> cricketComparator = new SortField().getCurrentSort( fieldType );
        List<CricketDAO> sortedList = cricketList.stream()
                .sorted( cricketComparator.reversed() )
                .collect( Collectors.toList() );
        return new Gson().toJson( sortedList );
    }
}
