package comp3350.group6.homiez.persistence;

import comp3350.group6.homiez.objects.Match;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.Request;
import comp3350.group6.homiez.objects.User;
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
        post = new Posting("0", "Room at Pembina Riverside Condo", users.get(0), 1000,  "Pembina", "Condo", "A beautiful riverside condo in the heart of Pembina. Great view of the surrounding area.");
        postings.add(post);
        post = new Posting("1", "Room at Windsor Park House", users.get(0), 800,  "Windsor Park", "House", "The Windsor Park community is known for its ample green space, Windsor Park Golf course and easy access to schools and recreational facilities. On-site Resident Managers, security entrance, two elevators, fitness facility, laundry facilities, common room, as well as indoor and outdoor parking. This stunning and renovated high-rise complex offers clean and spacious suites with plenty of sunlight, fully renovated kitchens that feature stainless-steel appliances with dishwashers, granite countertops for the penthouse suites, modernized bathroom with designer finishes, beautifully refinished hardwood floors throughout and large covered balconies with beautiful views. Each suite also has independent forced air heating & cooling. There is also an underground parkade with a car wash and a shuttle bus service to the local grocery stores weekly. This apartment building is a cat and a small dog-friendly building.");
        postings.add(post);
        post = new Posting("2", "Room at North Kildonan Condo", users.get(0), 600,  "North Kildonan", "Condo", "This great condo comes with 2 bedroom and 5 bath. All utilities included.");
        postings.add(post);
        post = new Posting("3", "Room at East St. Paul House", users.get(1), 200,  "East St. Paul", "House", "3000 square ft. home with incredible view of the surrounding fields.");
        postings.add(post);
        post = new Posting("4", "Room at Bridgewater Apartment", users.get(2), 700,  "Bridgewater", "Apartment", "Close to University of Manitoba, making this apartment great for any students wanting a quick commute to school (once covid stops).");
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
    public String getPostingsByUser(List<Posting> postingsList, User user){
        for(Posting p : postings)
        {
            if (p.getUser().equals(user))
            {
               postingsList.add(p);
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
        postings.remove(posting);
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
