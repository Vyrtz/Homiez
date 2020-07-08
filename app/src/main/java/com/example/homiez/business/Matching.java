package com.example.homiez.business;

import com.example.homiez.objects.Match;
import com.example.homiez.objects.Posting;
import com.example.homiez.objects.Request;

import java.util.ArrayList;
import java.util.List;

public class Matching {
    public static String AcceptRequest(AccessRequests requests, AccessMatches matches, String userId, String postingId)
    {
        //validate User and Posting
        //Create match and request database connection
        //update those databases
        if (userId != null && postingId != null) {
            List<Request> reqs = new ArrayList<Request>();
            String success = requests.getRequestsForPosting(reqs, postingId);
            if (success != null) {
                boolean found = false;
                Request toMatch = new Request(userId, postingId);
                for (Request r : reqs) {
                    if (toMatch.equals(r)) {
                        found = true;
                    }
                }
                if (found) {
                    requests.deleteRequest(toMatch);
                    Match newMatch = new Match(userId, postingId);
                    matches.insertMatch(newMatch);
                    return "Success";
                }
            }
        }
        return  null;
    }
    public static String DeclineRequest(AccessRequests requests, String userId, String postingId)
    {
        //validate User and Posting
        //Create match and request database connection
        //update those databases
        if(userId != null && postingId != null) {
            List<Request> reqs = new ArrayList<Request>();
            String success = requests.getRequestsForPosting(reqs, postingId);
            if (success != null) {
                boolean found = false;
                Request toMatch = new Request(userId, postingId);
                for (Request r : reqs) {
                    if (toMatch.equals(r)) {
                        found = true;
                    }
                }
                if (found) {
                    requests.deleteRequest(toMatch);
                    return "Success";
                }
            }
        }
        return  null;
    }
    public static String SendRequest(AccessRequests requests,AccessPostings postings, String userId, String postingId)
    {
        //validate User and Posting
        //Create match and request database connection
        //update those databases
        if(userId != null && postingId != null) {
            Request newReq = new Request(userId, postingId);
            List<Posting> posts = new ArrayList<Posting>();
            String success = postings.getPostings(posts, userId);
            //this would mean a posting is found and not made by the user
            if (success != null) {
                boolean found = false;
                Posting posting = new Posting( postingId);
                for (Posting p : posts) {
                    if (posting.equals(p)) {
                        found = true;
                    }
                }
                if (found) {
                    requests.insertRequest(newReq);
                    return "Success";
                }
            }
        }
        return  null;
    }

}
