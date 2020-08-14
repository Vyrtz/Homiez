package comp3350.group6.homiez.persistence;

import java.util.List;

import comp3350.group6.homiez.application.Shared.QueryResult;
import comp3350.group6.homiez.objects.Contact;
import comp3350.group6.homiez.objects.Match;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.Request;
import comp3350.group6.homiez.objects.User;

public interface DataAccess {
    void open(String dbpath);

    void close();

    //USER STUFF
    User getUser(User user);

    QueryResult insertUser(User user, String password);

    QueryResult updateUser(User user);

    QueryResult authenticateLogin(User user, String password);

    Contact getContactInfo(User user );

    QueryResult updateContactInfo(User user, Contact info);

    QueryResult deleteUser(User user);

    //POSTING STUFF
    QueryResult getAllDisplayPostings(List<Posting> postingList, User loggedin);

    Posting getPosting(Posting posting);

    QueryResult getPostingsByUser(List<Posting> postingsList, User user);

    QueryResult insertPosting(Posting posting);

    QueryResult deletePosting(Posting posting);

    QueryResult updatePosting(Posting p);


    //MATCH STUFF
    QueryResult getMatchesForUser(List<Match> matchList, String userId);

    QueryResult getMatchesForPosting(List<Match> matchList, String postingId);

    QueryResult insertMatch(Match m);

    QueryResult deleteMatch(Match m);


    //REQUEST STUFF
    QueryResult getRequests(List<Request> requests, String postingId);

    QueryResult insertRequest(Request request);

    QueryResult deleteRequest(Request request);
}
