package comp3350.group6.homiez.business;

import comp3350.group6.homiez.application.Constants.QueryResult;
import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.application.Services;
import comp3350.group6.homiez.objects.Request;
import comp3350.group6.homiez.persistence.DataAccess;

import java.util.List;

public class AccessRequests {
    private DataAccess dataAccess;

    public AccessRequests() {
        dataAccess = Services.getDataAccess(Main.dbName);
    }

    public QueryResult getRequestsForPosting(List<Request> requests, String postingId) {
        return dataAccess.getRequests(requests, postingId);
    }

    public QueryResult insertRequest(Request currentRequest) {
        return dataAccess.insertRequest(currentRequest);
    }

    public QueryResult deleteRequest(Request currentRequest) {
        return dataAccess.deleteRequest(currentRequest);
    }
}
