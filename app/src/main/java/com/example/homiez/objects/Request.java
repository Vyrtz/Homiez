package objects;

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


    public void setAccept(boolean a){
        this.accept=a;
    }
    public boolean getAccept(){
        return this.accept;
    }

    public String toString(){
        return "Match: "+this.match+" Accept?: "+this.accept;
    }

}
