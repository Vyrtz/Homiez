package comp3350.group6.homiez.business;

import comp3350.group6.homiez.application.Shared;
import comp3350.group6.homiez.application.Shared.QueryResult;
import comp3350.group6.homiez.application.Services;
import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.User;
import comp3350.group6.homiez.persistence.DataAccess;

import java.util.List;

import static comp3350.group6.homiez.application.Shared.isNotNull;
import static comp3350.group6.homiez.application.Shared.isNotNullOrBlank;

public class AccessPostings {
    private DataAccess dataAccess;

    public AccessPostings() {
        dataAccess = Services.getDataAccess(Main.dbName);
    }

    //provides postings, except the ones made by the user
    public QueryResult getPostings(List<Posting> postings, String userId) {
        if (isNotNullOrBlank(userId)) {
            User user = new User(userId);
            postings.clear();
            return dataAccess.getAllDisplayPostings(postings, user);
        }
        return QueryResult.FAILURE;
    }

    public Posting getPostingById(String postingId) {
        if (isNotNullOrBlank(postingId)) {
            Posting posting = new Posting(postingId);
            return dataAccess.getPosting(posting);
        }
        return null;
    }

    public QueryResult getPostingsByUserId(List<Posting> postings, String userId) {
        if (isNotNullOrBlank(userId) && isNotNull(postings)) {
            User user = new User(userId);
            return dataAccess.getPostingsByUser(postings, user);
        }
        return QueryResult.FAILURE;
    }

    public QueryResult insertPosting(Posting currentPosting) {
        if (isNotNull(currentPosting)) {
            return dataAccess.insertPosting(currentPosting);
        }
        return QueryResult.FAILURE;
    }

    public QueryResult updatePosting(Posting currentPosting) {
        if (isNotNull(currentPosting)) {
            return dataAccess.updatePosting(currentPosting);
        }
        return QueryResult.FAILURE;
    }

    public QueryResult deletePosting(Posting currentPosting) {
        if (isNotNull(currentPosting)) {
            return dataAccess.deletePosting(currentPosting);
        }
        return QueryResult.FAILURE;
    }
}
