package comp3350.group6.homiez.business;

import comp3350.group6.homiez.objects.Match;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.Request;

import java.util.ArrayList;
import java.util.List;

public class Matching {
    public static String AcceptRequest(AccessRequests requests, AccessMatches matches, String userId, String postingId) {
        if (userId != null && postingId != null && requests != null && matches != null) {
            boolean success = deleteRequest(requests, userId, postingId);
            if (success) {
                Match newMatch = new Match(userId, postingId);
                return matches.insertMatch(newMatch);
            }
        }
        return null;
    }

    public static String DeclineRequest(AccessRequests requests, String userId, String postingId) {
        if (userId != null && postingId != null && requests != null) {
            boolean success = deleteRequest(requests, userId, postingId);
            if (success) {
                return "Success";
            }
        }
        return null;
    }

    public static String SendRequest(AccessRequests requests, AccessPostings postings,AccessMatches matches, String userId, String postingId) {
        if (userId != null && postingId != null && requests != null && postings != null && matches != null) {
            boolean matchAlreadyPresent = checkMatch(matches, userId, postingId);
            if (matchAlreadyPresent) { return null; }
            Request newReq = new Request(userId, postingId);
            List<Posting> posts = new ArrayList<Posting>();
            String success = postings.getPostings(posts, userId);
            //this would mean a posting is found and not made by the user
            if (success != null) {
                Posting posting = new Posting(postingId);
                if (posts.contains(posting)) {
                    return requests.insertRequest(newReq);
                }
            }
        }
        return null;
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
}

