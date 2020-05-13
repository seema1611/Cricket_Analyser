package com.cricketanalyser.adapter;

import com.censusanalyser.opencsv.CSVBuilderFactory;
import com.censusanalyser.opencsv.ICSVBuilder;
import com.cricketanalyser.dao.CricketDAO;
import com.cricketanalyser.exception.CricketAnalyserException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CricketAdapter {
    Map<String, CricketDAO> cricketMap = new HashMap<>();
    public abstract Map<String, CricketDAO> loadCricketData(String csvFilePath) throws CricketAnalyserException;

    public <E> Map<String, CricketDAO> loadCricketData(Class<E> cricketClass, String csvFilePath)
            throws CricketAnalyserException {
        try {
            Reader reader = Files.newBufferedReader( Paths.get( csvFilePath ) );
            ICSVBuilder csvBuilderInterface = CSVBuilderFactory.createCSVBuilder();
            List csvFileList = csvBuilderInterface.getCSVFileList  ( reader, cricketClass );
            csvFileList.forEach( (cricketCSV) -> new CricketAnalyserFactory().getCricketObject( cricketClass,
                    cricketMap, cricketCSV ) );
            return cricketMap;
        } catch (IOException e) {
            throw new CricketAnalyserException( e.getMessage(),
                    CricketAnalyserException.ExceptionType.CRICKET_FILE_PROBLEM );
        }
    }
}
