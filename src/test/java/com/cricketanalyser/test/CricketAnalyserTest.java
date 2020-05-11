package com.cricketanalyser.test;

import com.cricketanalyser.analyse.ConstantPaths;
import com.cricketanalyser.analyse.CricketAnalyser;
import com.cricketanalyser.exception.CricketAnalyserException;
import com.cricketanalyser.model.BattingCSVFile;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CricketAnalyserTest extends ConstantPaths {
    CricketAnalyser cricketAnalyser;

    @Before
    public void initialize() {
        cricketAnalyser=new CricketAnalyser();
    }

    //UC-1
    //TC-1.1
    @Test
    public void givenWhenCricketData_whenCorrect_shouldReturnsCorrectRecords() {
        try {
            int numOfRecords = cricketAnalyser.loadCricketData( IPL_RUNS_FILE_PATH );
            Assert.assertEquals( 100, numOfRecords );
        } catch (CricketAnalyserException e) {
            System.out.println("No Record matches");
        }
    }

    //TC-1.2
    @Test
    public void givenWhenCricketDataPath_whenInCorrect_shouldThrowException() {
        try {
            cricketAnalyser.loadCricketData( WRONG_IPL_RUNS_FILE_PATH );
        } catch (CricketAnalyserException e) {
            Assert.assertEquals( CricketAnalyserException.ExceptionType.CRICKET_FILE_PROBLEM, e.type );
        }
    }
}
