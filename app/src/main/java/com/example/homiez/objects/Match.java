package com.example.homiez.objects;

public class Match {

    private User user;
    private Posting posting;

    public Match(User user, Posting posting){
        this.user=user;
        this.posting=posting;
    }

    //when need to change user or post just make a new match object.
    public User getUser(){
        return this.user;
    }
    public Posting getPost(){
        return this.posting;
    }

    public String toString(){
        return "user: "+this.user+" post: "+this.posting;
    }


}
