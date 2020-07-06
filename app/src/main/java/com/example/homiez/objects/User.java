package com.example.homiez.objects;

import java.util.ArrayList;
import java.util.Iterator;

public class User {

    //some of them may be used later
    private String name;
    private int age;
    private String gender;
    private int budget;
    private String description;
    private ArrayList<Posting> posting;


    public User(String name,int age, String gender){
        this.name=name;
        this.age=age;
        this.gender=gender;
        this.posting=new ArrayList<Posting>();
    }


    public void setName(String a){
        this.name=a;
    }
    public String getName(){
        return this.name;
    }



    public void setBudget(int a){
        this.budget=a;
    }
    public int getBudget(){
        return this.budget;
    }



    public void setDescription(String a){
        this.description=a;
    }

    public String getDescription(){
        return this.description;
    }


    public boolean equals(User a){
        return this.name.equals(a.getName());

    }

    public void addPosting(Posting posting){
        this.posting.add(posting);
    }

    public void deletePost(Posting posting){
        Iterator it=this.posting.iterator();
        if(this.posting.size()>0){

            while(it.hasNext()){
                if(it.next().equals(posting)){
                    //found it
                    it.remove();

                }
            }


        }
    }

    public ArrayList<Posting> getPosts(){
        return this.posting;
    }

    public void printPosts(){
        Iterator it=this.posting.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }


    }

    public String toString(){
        return "User: "+this.name+" age:"+this.age+" gender:"+this.gender;
    }


}
