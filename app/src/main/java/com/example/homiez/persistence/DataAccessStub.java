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
        User user;
        Posting post;
        Request request;
        Match match;

        users = new ArrayList<User>();
        user = new User ("0","Abhi", 20, "m");
        users.add(user);
        user = new User ("1","Jordan", 20, "m");
        users.add(user);
        user = new User ("2","Matt", 20, "m");
        users.add(user);
        user = new User ("3","Vinh", 18, "m");
        users.add(user);
        user = new User ("4","Ma", 18, "m");
        users.add(user);

        postings = new ArrayList<Posting>();
        post = new Posting("0", "New condo 1", users.get(0), 1200,  "Pembina", "Condo", "new condo here 1!");
        postings.add(post);
        post = new Posting("1", "New condo 2", users.get(0), 1200,  "Pembina", "Condo", "new condo here 2!");
        postings.add(post);
        post = new Posting("2", "New condo 3", users.get(0), 1200,  "Pembina", "Condo", "new condo here 3!");
        postings.add(post);
        post = new Posting("3", "New house", users.get(1), 2000,  "Pembina", "house", "new house here!");
        postings.add(post);
        post = new Posting("4", "New house", users.get(2), 2200,  "Pembina", "house", "new house here!");
        postings.add(post);

        matches = new ArrayList<Match>();
        match = new Match("3", "0");
        matches.add(match);
        match = new Match("3", "1");
        matches.add(match);
        match = new Match("4", "3");
        matches.add(match);

        matchRequests = new ArrayList<Request>();
        request = new Request("4", "0");
        matchRequests.add(request);
        request = new Request("4", "1");
        matchRequests.add(request);
        request = new Request("4", "2");
        matchRequests.add(request);
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
