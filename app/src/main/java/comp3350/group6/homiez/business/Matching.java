package comp3350.group6.homiez.business;

import comp3350.group6.homiez.application.Shared;
import comp3350.group6.homiez.application.Shared.QueryResult;
import comp3350.group6.homiez.objects.Match;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.Request;
import comp3350.group6.homiez.objects.User;

import java.util.ArrayList;
import java.util.List;

import static comp3350.group6.homiez.application.Shared.isNotNull;
import static comp3350.group6.homiez.application.Shared.isNotNullOrBlank;

public class Matching {
    private static QueryResult result;

    public static QueryResult AcceptRequest(AccessRequests requests, AccessMatches matches, String userId, String postingId) {
        result = QueryResult.FAILURE;

        if (isNotNullOrBlank(userId) && isNotNullOrBlank(postingId) && isNotNull(requests) && isNotNull(matches)) {
            if (Validate(userId, postingId)) {
                boolean success = deleteRequest(requests, userId, postingId);
                if (success) {
                    Match newMatch = new Match(userId, postingId);
                    result = matches.insertMatch(newMatch);
                }
            }
        }
        return result;
    }

    public static QueryResult DeclineRequest(AccessRequests requests, String userId, String postingId) {
        result = QueryResult.FAILURE;

        if (isNotNullOrBlank(userId) && isNotNullOrBlank(postingId) && isNotNull(requests)) {
            if (Validate(userId, postingId)) {
                boolean success = deleteRequest(requests, userId, postingId);
                if (success) {
                    result = QueryResult.SUCCESS;
                }
            }
        }
        return result;
    }

    public static QueryResult SendRequest(AccessRequests requests, AccessPostings postings, AccessMatches matches, String userId, String postingId) {
        result = QueryResult.FAILURE;

        if (isNotNullOrBlank(userId) && isNotNullOrBlank(postingId) && isNotNull(requests) && isNotNull(postings) && isNotNull(matches)) {
            if (Validate(userId, postingId)) {
                boolean matchAlreadyPresent = checkMatch(matches, userId, postingId);
                if (!matchAlreadyPresent) {
                    Request newReq = new Request(userId, postingId);
                    List<Posting> posts = new ArrayList<Posting>();
                    QueryResult success = postings.getPostings(posts, userId);
                    //this would mean a posting is found and not made by the user
                    if (success != QueryResult.FAILURE) {
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
        QueryResult success = requests.getRequestsForPosting(reqs, postingId);

        if (success != QueryResult.FAILURE) {
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
        QueryResult success = matches.getMatchesForUser(matchList, userId);

        if (success != QueryResult.FAILURE) {
            return matchList.contains(toCompare);
        }
        return false;
    }

    private static boolean Validate (String userId, String postingId) {
        AccessUser users = new AccessUser();
        AccessPostings postings = new AccessPostings();
        User u = users.getUser(userId);
        Posting p = postings.getPostingById(postingId);

        if(isNotNull(u) && isNotNull(p)) {
            return true;
        }
        return false;
    }
}

