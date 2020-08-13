package comp3350.group6.homiez.integration;

import junit.framework.TestCase;

import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.application.Services;
import comp3350.group6.homiez.persistence.DataAccess;
import comp3350.group6.homiez.persistence.DataAccessTest;

public class DataAccessHSQLDBTest extends TestCase {

    private static String dbName = Main.dbName;

    public DataAccessHSQLDBTest(String arg0) { super(arg0); }

    public void testDataAccess() {
        DataAccess dataAccess;

        Services.closeDataAccess();

        System.out.println("\nStarting Integration test DataAccess (Default DB)");

        Services.createDataAccess(dbName);
        dataAccess = Services.getDataAccess(dbName);

        //TODO: Pass the new data access to DataAccessTest - test on real DB instead of stub
        //DataAccessTest.dataAccessTest(dataAccess);

        Services.closeDataAccess();

        System.out.println("Finished Integration test DataAccess (Default DB)");
    }
}
