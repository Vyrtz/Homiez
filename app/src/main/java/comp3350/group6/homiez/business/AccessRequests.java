package comp3350.group6.homiez.business;

import comp3350.group6.homiez.application.Shared;
import comp3350.group6.homiez.application.Shared.QueryResult;
import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.application.Services;
import comp3350.group6.homiez.objects.Request;
import comp3350.group6.homiez.persistence.DataAccess;

import java.util.List;

import static comp3350.group6.homiez.application.Shared.isNotNull;
import static comp3350.group6.homiez.application.Shared.isNotNullOrBlank;

public class AccessRequests {
    private DataAccess dataAccess;

    public AccessRequests() {
        dataAccess = Services.getDataAccess(Main.dbName);
    }

    public QueryResult getRequestsForPosting(List<Request> requests, String postingId) {
        if(isNotNullOrBlank(postingId)) {
            return dataAccess.getRequests(requests, postingId);
        }
        return QueryResult.FAILURE;
    }

    public QueryResult insertRequest(Request currentRequest) {
        if (isNotNull(currentRequest)) {
            return dataAccess.insertRequest(currentRequest);
        }
        return QueryResult.FAILURE;
    }

    public QueryResult deleteRequest(Request currentRequest) {
        if (isNotNull(currentRequest)) {
            return dataAccess.deleteRequest(currentRequest);
        }
        return QueryResult.FAILURE;
    }
}
