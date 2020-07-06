package objects;

public class Match {

    private User user;
    private Post post;

    public Match(User user, Post post){
        this.user=user;
        this.post=post;
    }

    //when need to change user or post just make a new match object.
    public User getUser(){
        return this.user;
    }
    public Post getPost(){
        return this.post;
    }

    public String toString(){
        return "user: "+this.user+" post: "+this.post;
    }


}
