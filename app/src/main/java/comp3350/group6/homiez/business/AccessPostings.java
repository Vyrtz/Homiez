package comp3350.group6.homiez.business;

import comp3350.group6.homiez.application.Services;
import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.User;
import comp3350.group6.homiez.persistence.DataAccess;

import java.util.List;

public class AccessPostings {
    private DataAccess dataAccess;

    public AccessPostings() {
        dataAccess = Services.getDataAccess(Main.dbName);
    }

    //provides postings, except the ones made by the user
    public String getPostings(List<Posting> postings, String userId) {
        User user = new User(userId);
        postings.clear();
        return dataAccess.getAllDisplayPostings(postings, user);
    }

    public Posting getPostingById(String postingId) {
        Posting posting = new Posting(postingId);
        return dataAccess.getPosting(posting);
    }

    public String getPostingsByUserId(List<Posting> postings, String userId) {
        if(userId != null) {
            User user = new User(userId);
            return dataAccess.getPostingsByUser(postings, user);
        }
        return null;
    }

    public String insertPosting(Posting currentPosting) {
        return dataAccess.insertPosting(currentPosting);
    }

    public String updatePosting(Posting currentPosting) {
        return dataAccess.updatePosting(currentPosting);
    }

    public String deletePosting(Posting currentPosting) {
        return dataAccess.deletePosting(currentPosting);
    }
}
