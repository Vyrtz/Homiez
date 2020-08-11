package comp3350.group6.homiez.business;

import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.application.Services;
import comp3350.group6.homiez.objects.Match;
import comp3350.group6.homiez.persistence.DataAccess;

import java.util.List;

public class AccessMatches {
    private DataAccess dataAccess;

    public AccessMatches() {
        dataAccess = Services.getDataAccess(Main.dbName);
    }

    //provides postings, except the ones made by the user
    public String getMatchesForUser(List<Match> matches, String userId) {
        return dataAccess.getMatchesForUser(matches, userId);
    }

    public String getMatchesForPosting(List<Match> matches, String postingId) {
        return dataAccess.getMatchesForPosting(matches, postingId);
    }
    public String insertMatch(Match currentMatch) {
        return dataAccess.insertMatch(currentMatch);
    }

    public String deleteMatch(Match currentMatch)
    {
        return dataAccess.deleteMatch(currentMatch);
    }
}

