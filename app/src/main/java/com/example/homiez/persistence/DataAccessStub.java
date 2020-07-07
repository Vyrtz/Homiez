package com.example.homiez.persistence;

import com.example.homiez.objects.Match;
import com.example.homiez.objects.Posting;
import com.example.homiez.objects.Request;
import com.example.homiez.objects.User;

import java.util.ArrayList;
import java.util.List;

public class DataAccessStub {
    private String dbName;
    private String dbType = "stub";

    private ArrayList<User> users;
    private ArrayList<Posting> postings;
    private ArrayList<Match> matches;
    private ArrayList<Request> matchRequests;

    public DataAccessStub(String dbName){
        this.dbName = dbName;
    }
    public void open(String dbName)
    {
    }
    public void close()
    {
        System.out.println("Closed " +dbType +" database " +dbName);
    }

    public String getMatches(List<Match> matches, User u) {
        return null;
    }

    public String getMatches(List<Match> matches, Posting p) {
        return null;
    }


    public String insertMatch(Match m){
        return null;
    }

    public String deleteMatch(Match m){
        return null;
    }

    public List<Posting> getAllPostings(List<Posting> postings){
        return null;
    }
    public Posting getPosting(Posting posting){
        return null;
    }
    public String getPostingsByUser(List<Posting> postings, User user){
        return null;
    }
    public String insertPosting(Posting p){
        return null;
    }

    public String deletePosting(Posting p){
        return null;
    }
    public String updatePosting(Posting p){
        return null;
    }
    public String getRequests(List<Request> requests, Posting p){
        return null;
    }
    public String insertRequest(Request p){
        return null;
    }
    public String deleteRequest(Request p){
        return null;
    }
    public User getUser(User u){
        return null;
    }
    public String insertUser(User u){
        return null;
    }
    public String updateUser(User u){
        return null;
    }
}
