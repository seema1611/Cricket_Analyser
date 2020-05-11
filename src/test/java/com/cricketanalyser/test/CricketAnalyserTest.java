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

    //TC-1.3
    @Test
    public void givenWhenCricketDataType_whenInCorrect_shouldThrowException() {
        try {
            cricketAnalyser.loadCricketData( WRONG_IPL_RUNS_FILE_TYPE );
        } catch (CricketAnalyserException e) {
            Assert.assertEquals( CricketAnalyserException.ExceptionType.CRICKET_FILE_PROBLEM, e.type );
        }
    }

    //TC-1.4
    @Test
    public void givenWhenCricketData_whenInCorrect_shouldThrowException() {
        try {
            cricketAnalyser.loadCricketData( WRONG_IPL_RUNS_FILE );
        } catch (CricketAnalyserException e) {
            Assert.assertEquals( CricketAnalyserException.ExceptionType.CRICKET_FILE_INTERNAL_ISSUE, e.type );
            System.out.println(e.getMessage());
        }
    }

    //TC-1.5
    @Test
    public void givenWhenCricketData_ShouldReturn_TopBattingAverage() {
        try {
            cricketAnalyser.loadCricketData( IPL_RUNS_FILE_PATH );
            String sortedData = cricketAnalyser.getFieldWiseData( "average" );
            BattingCSVFile[] cricketCSV = new Gson().fromJson( sortedData,BattingCSVFile[].class );
            Assert.assertEquals( "MS Dhoni", cricketCSV[0].player );
        } catch (CricketAnalyserException e) {
        }
    }

    //UC-2
    //TC-2.1
    @Test
    public void givenWhenCricketData_ShouldReturn_TopStrikeRate() {
        try {
            cricketAnalyser.loadCricketData( IPL_RUNS_FILE_PATH );
            String sortedData = cricketAnalyser.getFieldWiseData( "strikerate" );
            BattingCSVFile[] cricketCSV = new Gson().fromJson( sortedData,BattingCSVFile[].class );
            Assert.assertEquals( "Ishant Sharma", cricketCSV[0].player );
        } catch (CricketAnalyserException  e) {
        }
    }
}
