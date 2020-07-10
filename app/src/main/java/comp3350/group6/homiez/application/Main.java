package comp3350.group6.homiez.application;

public class Main {

    public static final String dbName = "iteration1";

    public static void main(String[] args) {
        startUp();

        shutDown();

    }

    public static void startUp(){
        Services.createDataAccess(dbName);
    }

    public static void shutDown(){
        Services.closeDataAccess();
    }
}
