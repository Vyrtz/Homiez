package comp3350.group6.homiez.objects;

public class Contact {
    private String info;

    public Contact (String info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o ) {

        //foot ball == football
        if (o == null) {
            return false;
        }
        String first = ((Contact) o).getInfo().toLowerCase().trim().replaceAll("\\s+","");
        String second = this.info.toLowerCase().trim().replaceAll("\\s+","");

        return (o instanceof Contact)
                ? first.equals(second)
                : false;
    }

    public String getInfo() {
        return info;
    }
}