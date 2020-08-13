package comp3350.group6.homiez.persistence;

import comp3350.group6.homiez.application.Constants.QueryResult;
import comp3350.group6.homiez.objects.Contact;
import comp3350.group6.homiez.objects.Match;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.Request;
import comp3350.group6.homiez.objects.User;
import comp3350.group6.homiez.persistence.DataAccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataAccessStub implements DataAccess {
    private String dbName;
    private String dbType = "stub";

    private ArrayList<User> users;
    private ArrayList<Posting> postings;
    private ArrayList<Match> matches;
    private ArrayList<Request> matchRequests;
    private HashMap<User, String> logins;
    private HashMap<User, Contact> contacts;

    public DataAccessStub(String dbName) {
        this.dbName = dbName;
    }
    public void open(String dbName) {
        User user;
        Posting post;
        Request request;
        Match match;

        users = new ArrayList<User>();
        user = new User ("0","Abhi", 20, "m", 500, "abhi's bio");
        users.add(user);
        user = new User ("1","Jordan", 20, "m", 700, "Jordan's bio");
        users.add(user);
        user = new User ("2","Matt", 20, "m", 1000, "Matt's bio");
        users.add(user);
        user = new User ("3","Vinh", 18, "m", 600, "Vinh's bio");
        users.add(user);
        user = new User ("4","Ma", 18, "m", 800, "Ma's bio");
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

        logins = new HashMap<>();
        for (User u: users) {
            logins.put(u,"dev");
        }
        contacts = new HashMap<>();
//        for (User u: users) {
//            contacts.put(u,new Contact("contact"));
//        }

        contacts.put(users.get(0), new Contact("Abhi contact"));
        contacts.put(users.get(1), new Contact("Jordan contact"));
        contacts.put(users.get(2), new Contact("Matt contact"));
    }
    public void close()
    {
        System.out.println("Closed " +dbType +" database " +dbName);
    }

    public QueryResult getMatchesForUser(List<Match> matches, String u) {
        if (matches != null && u != null) {
            for (Match m : this.matches) {
                if (m.getUserId().equals(u))
                    matches.add(m);
            }
            return QueryResult.SUCCESS;
        }
        return QueryResult.FAILURE;
    }

    public QueryResult getMatchesForPosting(List<Match> matches, String p) {
        if (matches != null && p != null) {
            for (Match m : this.matches) {
                if (m.getPostingId().equals(p))
                    matches.add(m);
            }
            return QueryResult.SUCCESS;
        }
        return QueryResult.FAILURE;
    }


    public QueryResult insertMatch(Match m) {
        if (m != null) {
            boolean exist = matches.contains(m);
            if (!exist) {
                matches.add(m);
                return QueryResult.SUCCESS;
            }
        }
        return QueryResult.FAILURE;
    }

    public QueryResult deleteMatch(Match m) {
        if (m != null) {
            boolean exist = matches.contains(m);
            if (!exist) {
                return QueryResult.FAILURE;
            }
            matches.remove(m);
            return QueryResult.SUCCESS;
        }
        return QueryResult.FAILURE;
    }

    public QueryResult getAllDisplayPostings(List<Posting> postings, User user) {
        if (postings != null && user != null) {
            for (Posting p : this.postings) {
                if (!p.getUser().equals(user)) {
                    postings.add(p);
                }
            }
            return QueryResult.SUCCESS;
        }
        return QueryResult.FAILURE;
    }

    public Posting getPosting(Posting posting) {
        for(Posting p : postings) {
            if (p.equals(posting)) {
                return p;
            }
        }
        return null;
    }
    public QueryResult getPostingsByUser(List<Posting> postingsList, User user) {
        if (postingsList != null && user != null) {
            for (Posting p : postings) {
                if (p.getUser().equals(user)) {
                    postingsList.add(p);
                }
            }
            return QueryResult.SUCCESS;
        }
        return QueryResult.FAILURE;
    }
    public QueryResult insertPosting(Posting posting) {
        if (posting != null) {
            boolean exist = postings.contains(posting);
            if (!exist) {
                postings.add(posting);
                return QueryResult.SUCCESS;
            }
        }
        return QueryResult.FAILURE;
    }

    public QueryResult deletePosting(Posting posting) {
        boolean exist = postings.contains(posting);
        if (!exist) {
            return QueryResult.FAILURE;
        }
        postings.remove(posting);
        return QueryResult.SUCCESS;
    }

    public QueryResult updatePosting(Posting posting) {
        boolean exist = postings.contains(posting);
        if (exist) {
            postings.remove(posting);
            postings.add(posting); //replace with new posting object
            return QueryResult.SUCCESS;
        }
        return QueryResult.FAILURE;
    }

    public QueryResult getRequests(List<Request> requests, String pId) {
        if (requests != null && pId != null) {
            for (Request r : this.matchRequests) {
                if (r.getPostingId().equals(pId))
                    requests.add(r);
            }
            return QueryResult.SUCCESS;
        }
        return QueryResult.FAILURE;
    }

    public QueryResult insertRequest(Request req) {
        if (req != null) {
            boolean exist = matchRequests.contains(req);
            if (!exist) {
                matchRequests.add(req);
                return QueryResult.SUCCESS;
            }
        }
        return QueryResult.FAILURE;
    }
    public QueryResult deleteRequest(Request req) {
        boolean exist = matchRequests.contains(req);
        if (!exist) {
            return QueryResult.FAILURE;
        }
        matchRequests.remove(req);
        return QueryResult.SUCCESS;
    }

    public User getUser(User toFind) {
        User found = null;
        for(User u : users) {
            if (u.equals(toFind)) {
                found = u;
            }
        }
        return found;
    }
    public QueryResult insertUser(User insert, String password) {
        if (insert != null) {
            boolean exist = users.contains(insert);
            if (!exist) {
                users.add(insert);
                logins.put(insert,password);
                return QueryResult.SUCCESS;
            }
        }
        return QueryResult.FAILURE;
    }

    public QueryResult updateUser(User update) {
        boolean exist = users.contains(update);
        if (exist) {
            users.remove(update);
            users.add(update); //replace with new posting object
            return QueryResult.SUCCESS;
        }
        return QueryResult.FAILURE;
    }

    public QueryResult deleteUser(User user) {
        boolean exist = users.contains(user);
        if (!exist) {
            return QueryResult.FAILURE;
        }
        users.remove(user);
        return QueryResult.SUCCESS;
    }

    public QueryResult authenticateLogin(User u, String password) {
        if (logins.get(u) == password) {
            return QueryResult.SUCCESS;
        }
        return QueryResult.FAILURE;
    }
    public Contact getContactInfo(User user) {
       return contacts.get(user);
    }

    public QueryResult updateContactInfo(User user, Contact info) {
        try {
            contacts.remove(user);
            contacts.put(user, info);
            return QueryResult.SUCCESS;
        }
        catch (Exception e) {
            return QueryResult.FAILURE;
        }
    }
}