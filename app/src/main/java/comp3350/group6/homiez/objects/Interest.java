package comp3350.group6.homiez.objects;

public class Interest {
    private String interest;
    public Interest (String interest) {
        this.interest = interest;
    }

    @Override
    public boolean equals(Object o ) {

        //foot ball == football
        String first = ((Interest) o).getInterest().toLowerCase().trim().replaceAll("\\s+","");
        String second = this.interest.toLowerCase().trim().replaceAll("\\s+","");

        return (o instanceof Interest)
                ? first.equals(second)
                : false;
    }

    public String getInterest() {
        return interest;
    }

    @Override
    public int hashCode() {
        String interest = this.interest.toLowerCase().trim().replaceAll("\\s+", "");
        return interest.hashCode();
    }
}
