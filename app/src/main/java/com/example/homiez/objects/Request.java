package com.example.homiez.objects;
public class Request {

    private String userId;
    private String postingId;
    private boolean accepted;

    public Request(String userId, String postingId){
        this.userId = userId;
        this.postingId = postingId;
        this.accepted=false;
    }

    public void setAccepted(boolean accept){this.accepted = accepted;}

    public String getPostingId() {
        return postingId;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isAccepted() {
        return accepted;
    }
}
