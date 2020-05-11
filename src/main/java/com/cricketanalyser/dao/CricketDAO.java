package com.cricketanalyser.dao;

import com.cricketanalyser.model.BattingCSVFile;

public class CricketDAO {
    public String player;
    public int runs;
    public String average;
    public double strikeRate;
    public int fours;
    public int sixes;
    public int sumSixFour;

    public CricketDAO(BattingCSVFile playerObj) {
        this.average = playerObj.average;
        this.fours = playerObj.fours;
        this.player = playerObj.player;
        this.runs = playerObj.runs;
        this.sixes = playerObj.sixes;
        this.strikeRate = playerObj.strikeRate;
        this.sumSixFour = playerObj.fours + playerObj.sixes;
    }
}
