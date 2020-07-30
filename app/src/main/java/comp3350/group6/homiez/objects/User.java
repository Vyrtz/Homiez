package comp3350.group6.homiez.objects;

import java.util.ArrayList;

public class User {

    //some of them may be used later
    private String userId;
    private String name;
    private int age;
    private String gender;
    private double budget;
    private String biography;
    private ArrayList<Posting> postingList;
    private ArrayList<Interest> interests;

    public User(String userId,String name,int age, String gender, double budget, String biography) {
        this.userId=userId;
        this.name=name;
        this.age=age;
        this.gender=gender;
        this.budget=budget;
        this.biography = biography;
        this.postingList=new ArrayList<Posting>();
        interests = new ArrayList<Interest>();
    }

    public User(String userId) {
        this.userId=userId;
        this.postingList=new ArrayList<Posting>();
        this.interests = new ArrayList<Interest>();
    }

    //once u set up an userid then you cannot change it anymore.
    public String getUserId() {
        return this.userId;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getBiography() { return this.biography; }
    public void setBiography(String biography) { this.biography = biography; };


    @Override
    public boolean equals(Object user) {
        return (user instanceof User)
                ? this.userId.equals(((User)user).getUserId())
                : false;
    }

    public void addPosting(Posting posting) {
        this.postingList.add(posting);
    }

    public ArrayList<Posting> getPosts() {
        return this.postingList;
    }

    public String toString() {
        return "User: "+this.name+" age:"+this.age+" gender:"+this.gender;
    }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public void setInterests(ArrayList<Interest> interests) {
        this.interests = interests;
    }

    public ArrayList<Interest> getInterests() {
        return interests;
    }

    public boolean addUniqueInterest (Interest i) {
        if (i != null) {
            if (!interests.contains(i)) {
                interests.add(i);
                return true;
            }
        }
        return false;
    }


    public double getBudget() { return budget; }

    public void setBudget(double budget) {
        this.budget = budget;
    }
}
