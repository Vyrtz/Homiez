package comp3350.group6.homiez.business;

import comp3350.group6.homiez.objects.Match;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.Request;

import java.util.ArrayList;
import java.util.List;

public class Matching {
    public static String AcceptRequest(AccessRequests requests, AccessMatches matches, String userId, String postingId) {
        //validate User and Posting
        //Create match and request database connection
        //update those databases
        if (userId != null && postingId != null) {
            List<Request> reqs = new ArrayList<Request>();
            String success = requests.getRequestsForPosting(reqs, postingId);
            if (success != null) {
                Request toMatch = new Request(userId, postingId);
                if (reqs.contains(toMatch)) {
                    requests.deleteRequest(toMatch);
                    Match newMatch = new Match(userId, postingId);
                    matches.insertMatch(newMatch);
                    return "Success";
                }
            }
        }
        return null;
    }

    public static String DeclineRequest(AccessRequests requests, String userId, String postingId) {
        //validate User and Posting
        //Create match and request database connection
        //update those databases
        if(userId != null && postingId != null) {
            List<Request> reqs = new ArrayList<Request>();
            String success = requests.getRequestsForPosting(reqs, postingId);
            if (success != null) {
                Request toMatch = new Request(userId, postingId);
                if (reqs.contains(toMatch)) {
                    requests.deleteRequest(toMatch);
                    return "Success";
                }
            }
        }
        return null;
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
                Posting posting = new Posting( postingId);
                if (posts.contains(posting)) {
                    requests.insertRequest(newReq);
                    return "Success";
                }
            }
        }
        return null;
    }
}
