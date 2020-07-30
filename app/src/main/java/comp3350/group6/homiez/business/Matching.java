package comp3350.group6.homiez.business;

import comp3350.group6.homiez.objects.Match;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.Request;
import comp3350.group6.homiez.objects.User;

import java.util.ArrayList;
import java.util.List;

public class Matching {
    private static String result;

    public static String AcceptRequest(AccessRequests requests, AccessMatches matches, String userId, String postingId) {
        result = null;
        if (Validate(userId, postingId)) {
            if (userId != null && postingId != null && requests != null && matches != null) {
                boolean success = deleteRequest(requests, userId, postingId);
                if (success) {
                    Match newMatch = new Match(userId, postingId);
                    result = matches.insertMatch(newMatch);
                }
            }
        }
        return result;
    }

    public static String DeclineRequest(AccessRequests requests, String userId, String postingId) {
        result = null;
        if (Validate(userId, postingId)) {
            if (userId != null && postingId != null && requests != null) {
                boolean success = deleteRequest(requests, userId, postingId);
                if (success) {
                    result = "Success";
                }
            }
        }
        return result;
    }

    public static String SendRequest(AccessRequests requests, AccessPostings postings, AccessMatches matches, String userId, String postingId) {
        result = null;
        if (Validate(userId, postingId)) {
            if (userId != null && postingId != null && requests != null && postings != null && matches != null) {
                boolean matchAlreadyPresent = checkMatch(matches, userId, postingId);
                if (!matchAlreadyPresent) {
                    Request newReq = new Request(userId, postingId);
                    List<Posting> posts = new ArrayList<Posting>();
                    String success = postings.getPostings(posts, userId);
                    //this would mean a posting is found and not made by the user
                    if (success != null) {
                        Posting posting = new Posting(postingId);
                        if (posts.contains(posting)) {
                            result = requests.insertRequest(newReq);
                        }
                    }
                }
            }
        }
        return result;
    }

    private static boolean deleteRequest(AccessRequests requests, String userId, String postingId) {
        List<Request> reqs = new ArrayList<Request>();
        String success = requests.getRequestsForPosting(reqs, postingId);
        if (success != null) {
            Request toMatch = new Request(userId, postingId);
            if (reqs.contains(toMatch)) {
                requests.deleteRequest(toMatch);
                return true;
            }
        }
        return false;
    }
    private static boolean checkMatch (AccessMatches matches , String userId, String postingId) {
        Match toCompare = new Match(userId, postingId);
        List<Match> matchList = new ArrayList<>();
        String success = matches.getMatchesForUser(matchList, userId);
        if (success != null) {
            return matchList.contains(toCompare);
        }
        return false;
    }

    private static boolean Validate (String userId, String postingId) {
        AccessUser users = new AccessUser();
        AccessPostings postings = new AccessPostings();
        User u = users.getUser(userId);
        Posting p = postings.getPostingById(postingId);
        if(u == null || p == null) {
            return false;
        }
        return true;
    }
}

