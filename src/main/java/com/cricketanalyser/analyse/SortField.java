package com.cricketanalyser.analyse;

import com.cricketanalyser.dao.CricketDAO;

import java.util.Comparator;

public class SortField {

    public Comparator<CricketDAO> getCurrentSort(String field) {

        Comparator<CricketDAO> comparator = null;
        switch (field) {
            case "player":
                comparator = Comparator.comparing( Cricket -> Cricket.player );
                break;

            case "strikerate":
                comparator = Comparator.comparing( cricket -> cricket.strikeRate );
                break;

            case "average":
                comparator = Comparator.comparing( (cricket) -> Double.parseDouble( cricket.average
                        .replace( "-", "0" ) ) );
                break;

            case "sixes":
                comparator = Comparator.comparing( cricket -> cricket.sixes );
                break;

            case "sixandfour":
                comparator = Comparator.comparing( cricket -> cricket.sumSixFour );
                break;

            case "sixfourandstrike":
                comparator = Comparator.comparing( CricketDAO::getSumSixFour )
                        .thenComparing( CricketDAO::getStrikeRate );
                break;

            case "avgandstrike":
                comparator = Comparator.comparing( CricketDAO::getStrikeRate )
                        .thenComparing( CricketDAO::getAverage );
                break;

            case "runsandavg":
                comparator = Comparator.comparing( CricketDAO::getRuns )
                        .thenComparing( CricketDAO::getAverage );
        }
        return comparator;
    }
}
