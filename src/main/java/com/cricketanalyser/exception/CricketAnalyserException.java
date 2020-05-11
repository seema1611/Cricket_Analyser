package com.cricketanalyser.exception;

public class CricketAnalyserException extends Exception {
    public enum ExceptionType {
        NO_CRICKET_DATA, CRICKET_FILE_INTERNAL_ISSUE, CRICKET_FILE_PROBLEM
    }

    public ExceptionType type;

    public CricketAnalyserException(String message, ExceptionType type) {
        super( message );
        this.type = type;
    }
}
