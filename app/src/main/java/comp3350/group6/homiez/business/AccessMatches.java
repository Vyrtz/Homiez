package comp3350.group6.homiez.business;

import comp3350.group6.homiez.application.Shared.QueryResult;
import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.application.Services;
import comp3350.group6.homiez.objects.Match;
import comp3350.group6.homiez.persistence.DataAccess;

import java.util.List;

import static comp3350.group6.homiez.application.Shared.isNotNull;
import static comp3350.group6.homiez.application.Shared.isNotNullOrBlank;

public class AccessMatches {
    private DataAccess dataAccess;

    public AccessMatches() {
        dataAccess = Services.getDataAccess(Main.dbName);
    }

    //provides postings, except the ones made by the user
    public QueryResult getMatchesForUser(List<Match> matches, String userId) {
        if (isNotNullOrBlank(userId) && isNotNull(matches)) {
            return dataAccess.getMatchesForUser(matches, userId);
        }
        return QueryResult.FAILURE;
    }

    public QueryResult getMatchesForPosting(List<Match> matches, String postingId) {
        if (isNotNullOrBlank(postingId) && isNotNull(matches)) {
            return dataAccess.getMatchesForPosting(matches, postingId);
        }
        return QueryResult.FAILURE;
    }

    public QueryResult insertMatch(Match currentMatch) {
        if (isNotNull(currentMatch)) {
            return dataAccess.insertMatch(currentMatch);
        }
        return QueryResult.FAILURE;
    }

    public QueryResult deleteMatch(Match currentMatch)
    {
        if (isNotNull(currentMatch)) {
            return dataAccess.deleteMatch(currentMatch);
        }
        return  QueryResult.FAILURE;
    }
}

