package comp3350.group6.homiez.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import comp3350.group6.homiez.R;
import comp3350.group6.homiez.application.Constants.QueryResult;
import comp3350.group6.homiez.business.AccessPostings;
import comp3350.group6.homiez.business.AccessUser;
import comp3350.group6.homiez.objects.Interest;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.User;

public class CustomizeProfileActivity extends Activity {

    private ArrayList<Posting> postings;

    private AccessUser accessUser;

    private AccessPostings accessPostings;

    private EditText name;
    private EditText age;
    private EditText gender;
    private EditText biography;
    private EditText interests;
    private EditText budget;
    private EditText email;
    private EditText phone;

    private User user;

    final private String HEADER_SUFFIX = "'s Profile";
    final private String ERROR = "Error: Couldn't update profile";
    final private String SUCCESS_HEADER = "Success!";
    final private String SUCCESS = "Profile updated";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.customize_profile);

        accessUser = new AccessUser();
        accessPostings = new AccessPostings();

        Bundle b = getIntent().getExtras();
        user = accessUser.getUser(b.getString("userID"));

        TextView header = findViewById(R.id.header);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        budget = findViewById(R.id.budget);
        biography = findViewById(R.id.bio);
        interests = findViewById(R.id.interests);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);

        header.setText(user.getName() + HEADER_SUFFIX);
        name.setText(user.getName());
        age.setText("" + user.getAge());
        gender.setText(user.getGender());
        budget.setText("" + user.getBudget());
        biography.setText(user.getBiography());
        //TODO: Set the email and phone text once it's implemented in the database
        //email.setText(user.getEmail());
        //phone.setText(user.getPhone());

        //Build the string for the interests
        ArrayList<Interest> interestList = user.getInterests();

        String interestText = "";
        for (int i = 0; i < interestList.size(); i++) {
            Interest temp = interestList.get(i);
            interestText += temp.getInterest();
            if(i != interestList.size()-1) {
                interestText += ", ";
            }
        }

        interests.setText(interestText);

    }

    public void submitClicked(View v) {
        //TODO: Modify email and phone once the changes are implemented

        //Modify the user object that we have
        user.setName(name.getText().toString());

        //Modify age if there is another integer in its place
        if(!age.getText().toString().equals("")) {
            int tempAge = Integer.parseInt(age.getText().toString());
            if(tempAge > 150 || tempAge <= 0) {
                Messages.warning(this, "Error: Age value invalid");
                return;
            }
            user.setAge(tempAge);
        }

        //Modify the gender
        user.setGender(gender.getText().toString());

        //Modify the biography
        user.setBiography(biography.getText().toString());

        if(!budget.getText().toString().equals("")) {
            user.setBudget(Double.parseDouble(budget.getText().toString()));
        }

        //Create a new list of interests to store
        String interestString = interests.getText().toString();
        String[] tempList = interestString.split(",");

        ArrayList<Interest> interestList = new ArrayList<>();

        for(String currInterest : tempList) {
            if(!currInterest.equals("")) {
                interestList.add(new Interest(currInterest));
            }
        }

        user.setInterests(interestList);

        if(accessUser.updateUser(user)== QueryResult.FAILURE) {
            Messages.warning(this, ERROR);
        }
        else {
            Messages.popup(this, SUCCESS, SUCCESS_HEADER);
        }

    }
}
