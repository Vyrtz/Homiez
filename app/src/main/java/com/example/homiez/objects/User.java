package objects;

import java.util.ArrayList;

public class User {

    //some of them may be used later
    private String name;
    private int age;
    private String gender;
    private int budget;
    private String description;
    private ArrayList<Post> posts;


    public User(String name,int age, String gender){
        this.name=name;
        this.age=age;
        this.gender=gender;
        this.posts=new ArrayList<Post>();
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
        if(this.name.equals(a.getName())){
            return true;
        }
        else{
            return false;
        }
    }

    public void addPost(Post a){
        this.posts.add(a);
    }

    public void deletePost(Post a){
        if(this.posts.size()>0){

            for(int i=0;i<this.posts.size();i++){
                if(this.posts.get(i).equals(a)){
                    //found it
                    posts.remove(i);

                }
            }


        }
    }

    public ArrayList<Post> getPosts(){
        return this.posts;
    }

    public void printPosts(){
        for(int i=0;i<posts.size();i++){
            System.out.println(posts.get(i));
        }



    }

    public String toString(){
        return "User: "+this.name+" age:"+this.age+" gender:"+this.gender;
    }





}
