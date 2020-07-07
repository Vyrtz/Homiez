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

    public String getMatchesForUser(List<Match> matches, String u) {
        for (Match m : this.matches) {
            if(m.getUserId().equals(u))
                matches.add(m);
        }
        return "Success";
    }

    public String getMatchesForPosting(List<Match> matches, String p) {
        for (Match m : this.matches) {
            if(m.getPostingId().equals(p))
                matches.add(m);
        }
        return "Success";
    }


    public String insertMatch(Match m){
        boolean exist = false;
        for(Match match : matches)
        {
            if (match.equals(m))
            {
                exist = true;
            }
        }
        if (!exist)
        {
            matches.add(m);
            return "Success";
        }
        return "Failure";
    }

    public String deleteMatch(Match m){
        boolean exist = false;
        for(Match match : matches)
        {
            if (match.equals(m))
            {
                exist = true;
            }
        }
        if (!exist)
        {
            return "Failure";
        }
        matches.remove(m);
        return "Success";
    }

    public String getAllPostings(List<Posting> postings){
        postings.addAll(this.postings);
        return "Success";
    }
    public Posting getPosting(Posting posting){
        for(Posting p : postings)
        {
            if (posting.equals(p))
            {
                return p;
            }
        }
        return null;
    }
    public String getPostingsByUser(List<Posting> postings, User user){
        for(Posting p : postings)
        {
            if (p.getUser().equals(user))
            {
               postings.add(p);
            }
        }
        return "Success";
    }
    public String insertPosting(Posting posting){
        boolean exist = false;
        for(Posting p : postings)
        {
            if (p.equals(posting))
            {
                exist = true;
            }
        }
        if (!exist)
        {
            postings.add(posting);
            return "Success";
        }
        return "Failure";
    }

    public String deletePosting(Posting posting){
        boolean exist = false;
        for(Posting p : postings)
        {
            if (posting.equals(p))
            {
                exist = true;
            }
        }
        if (!exist)
        {
            return "Failure";
        }
        matches.remove(posting);
        return "Success";
    }
    public String updatePosting(Posting posting){
        Posting exist = null;
        for(Posting p : postings)
        {
            if (p.equals(posting))
            {
                exist = p;
            }
        }
        if (exist != null)
        {
            postings.remove(exist);
            postings.add(posting); //replace with new posting object
            return "Success";
        }
        return "Failure";
    }
    public String getRequests(List<Request> requests, String pId){
        for (Request r : this.matchRequests) {
            if(r.getPostingId().equals(pId))
                requests.add(r);
        }
        return "Success";
    }
    public String insertRequest(Request req){
        boolean exist = false;
        for(Request r : matchRequests)
        {
            if (r.equals(req))
            {
                exist = true;
            }
        }
        if (!exist)
        {
            matchRequests.add(req);
            return "Success";
        }
        return "Failure";
    }
    public String deleteRequest(Request req){
        boolean exist = false;
        for(Request r : matchRequests)
        {
            if (r.equals(req))
            {
                exist = true;
            }
        }
        if (!exist)
        {
            return "Failure";
        }
        matchRequests.remove(req);
        return "Success";
    }
    public User getUser(User toFind){
        User found = null;
        for(User u : users)
        {
            if (u.equals(toFind))
            {
                found = u;
            }
        }
        return found;
    }
    public String insertUser(User insert){
        boolean exist = false;
        for(User u : users)
        {
            if (u.equals(insert))
            {
                exist = true;
            }
        }
        if (!exist)
        {
            users.add(insert);
            return "Success";
        }
        return "Failure";
    }
    public String updateUser(User update) {
        User exist = null;
        for (User u : users) {
            if (u.equals(update)) {
                exist = u;
            }
        }
        if (exist != null) {
            users.remove(exist);
            users.add(update); //replace with new posting object
            return "Success";
        }
        return "Failure";
    }
}
