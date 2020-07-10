package comp3350.group6.homiez.objects;

import java.util.ArrayList;
import java.util.Iterator;

public class User {

    //some of them may be used later
    private String userId;
    private String name;
    private int age;
    private String gender;
    private int budget;
    private String description;
    private ArrayList<Posting> postingList;


    public User(String userId,String name,int age, String gender){
        this.userId=userId;
        this.name=name;
        this.age=age;
        this.gender=gender;
        this.postingList=new ArrayList<Posting>();
    }

    public User(String userId){
        this.userId=userId;
    }

    //once u set up an userid then you cannot change it anymore.
    public String getUserId(){
        return this.userId;
    }

    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }



    public void setBudget(int budget){
        this.budget=budget;
    }
    public int getBudget(){
        return this.budget;
    }

    public int getAge(){
        return age;
    }

    public void setDescription(String description){
        this.description=description;
    }

    public String getDescription(){
        return this.description;
    }

    @Override
    public boolean equals(Object user){
        return (user instanceof User) ? this.userId.equals(((User)user).getUserId()) : false;
    }

    public void addPosting(Posting posting){
        this.postingList.add(posting);
    }

    public void deletePost(Posting posting){
        Iterator it=this.postingList.iterator();
        if(this.postingList.size()>0){

            while(it.hasNext()){
                if(it.next().equals(posting)){
                    //found it
                    it.remove();

                }
            }


        }
    }

    public ArrayList<Posting> getPosts(){
        return this.postingList;
    }

    public void printPosts(){

        for( Posting i : this.postingList){
            System.out.println(i);
        }


    }

    public String toString(){
        return "User: "+this.name+" age:"+this.age+" gender:"+this.gender;
    }
}
