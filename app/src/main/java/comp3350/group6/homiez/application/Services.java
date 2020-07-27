package comp3350.group6.homiez.application;

import comp3350.group6.homiez.persistence.DataAccessStub;

public class Services {
    private static DataAccessStub dataAccessService;

    public static DataAccessStub createDataAccess( String dbName ) {
        if(dataAccessService == null) {
            dataAccessService = new DataAccessStub(dbName);
            dataAccessService.open(Main.dbName);
        }
        return dataAccessService;
    }

    public static DataAccessStub getDataAccess(String dbName) {
        if (dataAccessService == null) {
            System.out.println("Connection to data access has not been established.");
            System.exit(1);
        }
        return dataAccessService;
    }

    public static void closeDataAccess() {
        if (dataAccessService != null) {
            dataAccessService.close();
        }
        dataAccessService = null;
    }

}
