package com.example.homiez.objects;

import java.util.ArrayList;
import java.util.Iterator;

public class Posting {
    private String title;  //post id.
    private User user;
    private int price;  //we could set a price, and the budget is around that price, for instamce around 500$
    private String location;
    private String type;
    private String description;
    private ArrayList<User>attachedUsers;


    public Posting(String title,User user, int price, String location, String type, String description){
        this.title=title;
        this.user=user;
        this.price=price;
        this.location=location;
        this.type=type;
        this.description=description;
        this.attachedUsers=new ArrayList<User>();
        user.addPosting(this);
    }

    public void setTitle(String tile){
        this.title=title;
    }
    public String getTitle(){
        return this.title;
    }

    public void setPrice(int price){
        this.price=price;
    }
    public int getPrice(){
        return this.price;
    }


    public void setLocation(String location){
        this.location=location;
    }
    public String getLocation(){
        return this.location;
    }


    public void setType(String type){
        this.type=type;
    }
    public String getType(){
        return this.type;
    }


    public void setDescription(String description){
        this.description=description;
    }
    public String getDescription(){
        return this.description;
    }


    public void addAttached(User user){
        this.attachedUsers.add(user);
    }

    public boolean equals(Posting user){
        return this.title.equals(user.getTitle());

    }

    public void deleteAttached(User user){
        Iterator it=this.attachedUsers.iterator();
        if(this.attachedUsers.size()>0){

            while(it.hasNext()){
                if(it.next().equals(user)){
                    //found it
                    it.remove();

                }
            }


        }
    }

    public void printAttachedUsers(){
        Iterator it=this.attachedUsers.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }


    }

    public String toString(){
        return "Post: "+this.title+" price"+this.price
                +" location"+this.location+" type"+this.type;
    }




}
