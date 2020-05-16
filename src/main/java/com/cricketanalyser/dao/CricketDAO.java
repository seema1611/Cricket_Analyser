package com.cricketanalyser.dao;

import com.cricketanalyser.model.BattingCSVFile;
import com.cricketanalyser.model.BowlingCSVFile;

public class CricketDAO {
    public String player;
    public int runs;
    public double average;
    public double strikeRate;
    public int fours;
    public int sixes;
    public int sumSixFour;
    public double economy;
    public int five;
    public  int sumWickets;
    public double batsmenAvg;
    public double bowlerAvg;
    public  double bothAvg;
    public int matches;

    public CricketDAO(BattingCSVFile battingCSVFile) {
        average = battingCSVFile.average;
        fours = battingCSVFile.fours;
        player = battingCSVFile.player;
        runs = battingCSVFile.runs;
        sixes = battingCSVFile.sixes;
        strikeRate = battingCSVFile.strikeRate;
        sumSixFour = battingCSVFile.fours + battingCSVFile.sixes;
    }

    public CricketDAO(BowlingCSVFile bowlingCSVFile) {
        average = bowlingCSVFile.average;
        player = bowlingCSVFile.player;
        strikeRate = bowlingCSVFile.strikeRate;
        economy = bowlingCSVFile.economy;
        fours = bowlingCSVFile.fourWicket;
        five = bowlingCSVFile.fiveWicket;
        sumWickets= bowlingCSVFile.fourWicket + bowlingCSVFile.fiveWicket;
    }

    public CricketDAO(CricketDAO batsMen, CricketDAO bowler) {
        this.player=batsMen.player;
        this.batsmenAvg=batsMen.average;
        this.bowlerAvg=bowler.average;
        this.sumWickets=bowler.sumWickets;
        this.runs=batsMen.runs;
    }

    public double getStrikeRate() {
        return strikeRate;
    }

    public int getSumSixFour() {
        return sumSixFour;
    }

    public double getAverage() {
        return average;
    }

    public int getRuns() {
        return runs;
    }

    public int getSumWickets() {
        return sumWickets;
    }

    public double getBatsmenAvg() {
        return batsmenAvg;
    }

    public double getBowlerAvg() {
        return bowlerAvg;
    }

    public double getEconomy() {
        return economy;
    }
    public int getMatches() {
        return matches;
    }
}