package com.cricketanalyser.model;

import com.opencsv.bean.CsvBindByName;

public class BattingCSVFile {
    @CsvBindByName(column = "Player", required = true)
    public String player;

    @CsvBindByName(column = "Runs", required = true)
    public int runs;

    @CsvBindByName(column = "Avg", required = true)
    public String average;

    @CsvBindByName(column = "SR", required = true)
    public double strikeRate;

    @CsvBindByName(column = "4s", required = true)
    public int fours;

    @CsvBindByName(column = "6s", required = true)
    public int sixes;

    @Override
    public String toString() {
        return "BattingCSVFile{" +
                "player='" + player + '\'' +
                "runs='" + runs + '\'' +
                ", average=" + average +
                ", strikeRate=" + strikeRate +
                ", sixs=" + sixes +
                ", fours=" + fours +
                '}';
    }
}
