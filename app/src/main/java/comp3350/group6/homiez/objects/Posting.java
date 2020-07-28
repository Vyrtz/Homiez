package comp3350.group6.homiez.objects;

import java.util.ArrayList;
import java.util.Iterator;

public class Posting {
    private String postingId;
    private String title;  //post id.
    private User user;
    private double price;  //we could set a price, and the budget is around that price, for instamce around 500$
    private String location;
    private String type;
    private String description;
    private ArrayList<User>attachedUsers;


    public Posting(String postingId,String title,User user, double price, String location, String type, String description) {
        this.postingId=postingId;
        this.title=title;
        this.user=user;
        this.price=price;
        this.location=location;
        this.type=type;
        this.description=description;
        this.attachedUsers=new ArrayList<User>();
        this.addAttachedUser(user);
        user.addPosting(this);
    }
    public Posting(String postingId){
        this.postingId = postingId;
    }

    public User getUser() {
        return user;
    }

    public String getPostingId(){
        return this.postingId;
    }

    public String getTitle(){
        return this.title;
    }

    public double getPrice(){
        return this.price;
    }


    public String getLocation(){
        return this.location;
    }

    public String getType(){
        return this.type;
    }

    public String getDescription(){
        return this.description;
    }

    public String toString() {
        return "Post: "+this.title+" price"+this.price
                +" location"+this.location+" type"+this.type;
    }
    @Override
    public boolean equals(Object post) {
        return (post instanceof Posting)
                ? this.postingId.equals(((Posting)post).getPostingId())
                : false;
    }

    public ArrayList<User> getAttachedUsers() {
        return attachedUsers;
    }

    public void addAttachedUser(User u) {
        this.attachedUsers.add(u) ;
    }
}
