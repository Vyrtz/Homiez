package com.example.homiez.business;

import com.example.homiez.application.Main;
import com.example.homiez.application.Services;
import com.example.homiez.objects.Request;
import com.example.homiez.persistence.DataAccessStub;

import java.util.List;

public class AccessRequests {
    private DataAccessStub dataAccess;

    public AccessRequests(){
        dataAccess = Services.getDataAccess(Main.dbName);
    }

    //provides postings, except the ones made by the user
    public String getRequestsForPosting(List<Request> requests, String postingId)
    {
        return dataAccess.getRequests(requests, postingId);
    }

    public String insertRequest(Request currentRequest)
    {
        return dataAccess.insertRequest(currentRequest);
    }

    public String deleteRequest(Request currentRequest)
    {
        return dataAccess.deleteRequest(currentRequest);
    }
}
