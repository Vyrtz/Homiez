package comp3350.group6.homiez.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import comp3350.group6.homiez.R;
import comp3350.group6.homiez.business.AccessUser;
import comp3350.group6.homiez.business.AccessPostings;
import comp3350.group6.homiez.objects.Interest;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.User;

public class ProfileFragment extends Fragment implements View.OnClickListener{

    private AccessUser accessUser;

    final private String HEADER_SUFFIX = "'s Profile";

    public ProfileFragment() {
        //Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Grab the user
        Bundle b = this.getArguments();
        accessUser = new AccessUser();

        View v = inflater.inflate(R.layout.profile, container, false);

        //Set up button listener
        Button customizeButt = v.findViewById(R.id.submitButt);
        customizeButt.setOnClickListener(this);

        User user = accessUser.getUser(b.getString("userID"));


        //Initialize UI element variables
        TextView header = v.findViewById(R.id.header);
        TextView name = v.findViewById(R.id.name);
        TextView age = v.findViewById(R.id.age);
        TextView gender = v.findViewById(R.id.gender);
        TextView budget = v.findViewById(R.id.budget);
        TextView biography = v.findViewById(R.id.bio);
        TextView interests = v.findViewById(R.id.interests);

        biography.setMovementMethod(new ScrollingMovementMethod());
        interests.setMovementMethod(new ScrollingMovementMethod());

        //Filling default values for the profile
        header.setText(user.getName() + HEADER_SUFFIX);
        name.setText(user.getName());
        age.setText("" + user.getAge());
        gender.setText(user.getGender());
        budget.setText("" + user.getBudget());
        biography.setText(user.getBiography());

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

        return v;
    }

    public void onClick(View view) {
        Intent startIntent = new Intent(getActivity(), CustomizeProfileActivity.class);
        Bundle b = this.getArguments();
        startIntent.putExtras(b);
        startActivity(startIntent);

    }

}
