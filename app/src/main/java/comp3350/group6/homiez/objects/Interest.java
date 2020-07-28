package comp3350.group6.homiez.objects;

public class Interest {
    private String interest;
    public Interest (String interest) {
        this.interest = interest;
    }

    @Override
    public boolean equals(Object o ) {
        return (o instanceof Interest) ? ((Interest) o).getInterest().toLowerCase().equals(interest.toLowerCase()) : false ;
    }

    public String getInterest() {
        return interest;
    }
}
