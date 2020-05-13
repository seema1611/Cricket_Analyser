package com.cricketanalyser.adapter;

import com.cricketanalyser.dao.CricketDAO;
import com.cricketanalyser.exception.CricketAnalyserException;
import com.cricketanalyser.model.BattingCSVFile;

import java.util.Map;

public class BatsmanAdapter extends CricketAdapter {
    @Override
    public Map<String, CricketDAO> loadCricketData(String csvFilePath) throws CricketAnalyserException {
        return super.loadCricketData( BattingCSVFile.class,csvFilePath );
    }
}
