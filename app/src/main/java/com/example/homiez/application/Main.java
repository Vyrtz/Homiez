package com.example.homiez.application;

public class Main {

    public static final String dbName = "iteration1";

    public static void main(String[] args) {
    }

    public static void startUp(){
        Services.createDataAccess(dbName);
    }

    public static void shutDown(){
        Services.closeDataAccess();
    }
}
