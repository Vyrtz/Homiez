package comp3350.group6.homiez.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import comp3350.group6.homiez.R;
import comp3350.group6.homiez.business.AccessUser;
import comp3350.group6.homiez.objects.Interest;
import comp3350.group6.homiez.objects.User;

public class CustomizeProfileActivity extends Activity {

    private AccessUser accessUser;

    final private String HEADER_SUFFIX = "'s Profile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.customize_profile);

        accessUser = new AccessUser();

        Bundle b = getIntent().getExtras();
        User user = accessUser.getUser(b.getString("userID"));

        TextView header = findViewById(R.id.header);
        EditText name = findViewById(R.id.name);
        EditText age = findViewById(R.id.age);
        EditText gender = findViewById(R.id.gender);
        EditText biography = findViewById(R.id.bio);
        EditText interests = findViewById(R.id.interests);

        header.setText(user.getName() + HEADER_SUFFIX);
        name.setText(user.getName());
        age.setText("" + user.getAge());
        gender.setText(user.getGender());
        biography.setText(user.getBiography());

        //Build the string for the interests
        ArrayList<Interest> interestList = user.getInterests();

        String interestText = "";
        for (int i = 0; i < interestList.size(); i++){
            Interest temp = interestList.get(i);
            interestText += temp.getInterest();
            if(i != interestList.size()-1){
                interestText += ", ";
            }
        }

        interests.setText(interestText);

        //TODO: Populate the postings list as well

    }
}
