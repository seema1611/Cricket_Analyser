package com.cricketanalyser.adapter;

import com.cricketanalyser.dao.CricketDAO;
import com.cricketanalyser.exception.CricketAnalyserException;
import com.cricketanalyser.model.BowlingCSVFile;

import java.util.Map;

public class BowlerAdapter extends CricketAdapter {
    @Override
    public Map<String, CricketDAO> loadCricketData(String csvFilePath) throws CricketAnalyserException {
        return super.loadCricketData( BowlingCSVFile.class,csvFilePath );
    }
}

