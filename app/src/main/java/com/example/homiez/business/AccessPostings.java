package com.example.homiez.business;

import com.example.homiez.application.Services;
import com.example.homiez.application.Main;
import com.example.homiez.objects.Posting;
import com.example.homiez.objects.User;
import com.example.homiez.persistence.DataAccessStub;

import java.util.Iterator;
import java.util.List;

public class AccessPostings {
    private DataAccessStub dataAccess;

    public AccessPostings(){
        dataAccess = Services.getDataAccess(Main.dbName);
    }

    //provides postings, except the ones made by the user
    public String getPostings(List<Posting> postings, String userId)
    {
        User user = new User(userId);
        postings.clear();
        String ret = dataAccess.getAllPostings(postings);
        Iterator<Posting> it= postings.iterator();
        while(it.hasNext()){
                if(it.next().getUser().equals(user)){
                    it.remove();
                }
        }
        return ret;
    }

    public Posting getPostingById(String postingId)
    {
        Posting posting = new Posting(postingId);
        return dataAccess.getPosting(posting);
    }

    public String getPostingsByUserId(List<Posting> postings, String userId)
    {
        User user = new User(userId);
        return dataAccess.getPostingsByUser(postings, user);
    }

    public String insertPosting(Posting currentPosting)
    {
        return dataAccess.insertPosting(currentPosting);
    }

    public String updatePosting(Posting currentPosting)
    {
        return dataAccess.updatePosting(currentPosting);
    }

    public String deletePosting(Posting currentPosting)
    {
        return dataAccess.deletePosting(currentPosting);
    }
}
