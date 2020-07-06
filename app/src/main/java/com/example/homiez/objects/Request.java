package com.example.homiez.objects;
public class Request {

    private Match match;
    private boolean accept;

    public Request(Match match){
        this.match=match;
        this.accept=false;
    }

    public Match getmatch(){
        return this.match;
    }


    public void setAccept(boolean accept){
        this.accept=accept;
    }
    public boolean getAccept(){
        return this.accept;
    }

    public String toString(){
        return "Match: "+this.match+" Accept?: "+this.accept;
    }

}
