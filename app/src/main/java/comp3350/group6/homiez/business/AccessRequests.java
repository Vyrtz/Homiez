package comp3350.group6.homiez.business;

import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.application.Services;
import comp3350.group6.homiez.objects.Request;
import comp3350.group6.homiez.persistence.DataAccessStub;

import java.util.List;

public class AccessRequests {
    private DataAccessStub dataAccess;

    public AccessRequests(){
        dataAccess = Services.getDataAccess(Main.dbName);
    }

    //provides postings, except the ones made by the user
    public String getRequestsForPosting(List<Request> requests, String postingId) {
        return dataAccess.getRequests(requests, postingId);
    }

    public String insertRequest(Request currentRequest) {
        return dataAccess.insertRequest(currentRequest);
    }

    public String deleteRequest(Request currentRequest) {
        return dataAccess.deleteRequest(currentRequest);
    }
}
