package objects;

import java.util.ArrayList;

public class Post {
    private String title;  //post id.
    private User user;
    private int price;  //we could set a price, and the budget is around that price, for instamce around 500$
    private String location;
    private String type;
    private String description;
    private ArrayList<User>attachedUsers;


    public Post(String title,User user, int price, String location, String type, String description){
        this.title=title;
        this.user=user;
        this.price=price;
        this.location=location;
        this.type=type;
        this.description=description;
        this.attachedUsers=new ArrayList<User>();

    }

    public void setTitle(String a){
        this.title=a;
    }
    public String getTitle(){
        return this.title;
    }

    public void setPrice(int a){
        this.price=a;
    }
    public int getPrice(){
        return this.price;
    }


    public void setLocation(String a){
        this.location=a;
    }
    public String getLocation(){
        return this.location;
    }


    public void setType(String a){
        this.type=a;
    }
    public String getType(){
        return this.type;
    }


    public void setDescription(String a){
        this.description=a;
    }
    public String getDescription(){
        return this.description;
    }


    public void addAttached(User a){
        this.attachedUsers.add(a);
    }

    public boolean equals(Post a){
        if (this.title.equals(a.getTitle())) {
            return true;
        }
        else{

            return false;
        }
    }

    public void deleteAttached(User a){
        if(this.attachedUsers.size()>0){

            for(int i=0;i<attachedUsers.size();i++){
                if(attachedUsers.get(i).equals(a)){
                    //found it
                    attachedUsers.remove(i);

                }
            }


        }
    }

    public void printAttachedUsers(){
        for(int i=0;i<attachedUsers.size();i++){
            System.out.println(attachedUsers.get(i));
        }



    }

    public String toString(){
        return "Post: "+this.title+" price"+this.price
                +" location"+this.location+" type"+this.type;
    }




}
