package comp3350.group6.homiez.application;

public class Main {

    public static final String dbName = "Homiez";
    public static String dbPathName = "database/Homiez";

    public static void main(String[] args) {
        startUp();
        //test();
        shutDown();
    }

    public static void startUp() {
        Services.createDataAccess(dbName);
    }

    public static void shutDown() {
        Services.closeDataAccess();
    }

    public static String getDBPathName() {
        if (dbPathName == null)
            return dbName;
        else
            return dbPathName;
    }

    public static void setDBPathName(String pathName) {
        System.out.println("Setting DB path to: " + pathName);
        dbPathName = pathName;
    }
    public static void test() {
        Services.createDataAccess(dbName);
    }
}
