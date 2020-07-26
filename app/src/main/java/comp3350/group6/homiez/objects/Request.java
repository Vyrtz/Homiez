package comp3350.group6.homiez.objects;
public class Request {

    private String userId;
    private String postingId;

    public Request(String userId, String postingId) {
        this.userId = userId;
        this.postingId = postingId;
    }

    public String getPostingId() {
        return postingId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Request) ? ((Request) o).getUserId().equals(userId) && ((Request) o).getPostingId().equals(postingId) : false;
    }
}
