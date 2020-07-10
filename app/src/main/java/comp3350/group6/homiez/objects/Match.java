package comp3350.group6.homiez.objects;

public class Match {

    private String userId;
    private String postingId;

    public Match(String userId, String postingId){
        this.userId=userId;
        this.postingId=postingId;
    }

    public String getUserId(){
        return this.userId;
    }
    public String getPostingId(){
        return this.postingId;
    }

    public String toString(){
        return "user: "+this.userId+" post: "+this.postingId;
    }

    @Override
    public boolean equals (Object o ){
        if (o instanceof  Match){
            if(((Match) o).getPostingId().equals(this.postingId) && ((Match) o).getUserId().equals(this.userId))
            {
                return true;
            }
        }
        return  false;
    }
}
