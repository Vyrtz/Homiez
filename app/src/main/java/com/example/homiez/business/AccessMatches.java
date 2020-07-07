package com.example.homiez.business;

import com.example.homiez.application.Main;
import com.example.homiez.application.Services;
import com.example.homiez.objects.Match;
import com.example.homiez.objects.Posting;
import com.example.homiez.objects.User;
import com.example.homiez.persistence.DataAccessStub;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AccessMatches {
    private DataAccessStub dataAccess;

    public AccessMatches(){
        dataAccess = Services.getDataAccess(Main.dbName);
    }

    //provides postings, except the ones made by the user
    public String getMatchesForPosting(List<Match> matches, String userId)
    {
        User user = new User(userId);
        return dataAccess.getMatches(matches, user);
    }

    public String getMatchesForUser(List<Match> matches, String postingId)
    {
        Posting posting = new Posting(postingId);
        return dataAccess.getMatches(matches, posting);
    }
    public String insertMatch(Match currentMatch)
    {
        return dataAccess.insertMatch(currentMatch);
    }

    public String deleteMatch(Match currentMatch)
    {
        return dataAccess.deleteMatch(currentMatch);
    }
}

