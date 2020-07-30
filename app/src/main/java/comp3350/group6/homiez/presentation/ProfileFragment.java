package comp3350.group6.homiez.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import comp3350.group6.homiez.R;
import comp3350.group6.homiez.business.AccessUser;
import comp3350.group6.homiez.business.AccessPostings;
import comp3350.group6.homiez.objects.Interest;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.User;

public class ProfileFragment extends Fragment{

    private AccessUser accessUser;
    private ArrayList<Posting> postings;
    private AccessPostings accessPostings;
    private User user;

    final private String CUSTOMIZE = "Customize";
    final private String SUBMIT = "Submit Changes";
    final private String HEADER_SUFFIX = "'s Profile";

    public ProfileFragment(){
        //Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Grab the user
        Bundle b = this.getArguments();
        accessUser = new AccessUser();
        user = accessUser.getUser(b.getString("userID"));

        View v = inflater.inflate(R.layout.profile, container, false);

        //Gather variables for fields that are going to be changed
        TextView header = v.findViewById(R.id.header);
        EditText name = v.findViewById(R.id.name);
        EditText age = v.findViewById(R.id.age);
        EditText gender = v.findViewById(R.id.gender);
        EditText biography = v.findViewById(R.id.bio);
        EditText interests = v.findViewById(R.id.interests);

        //Filling default values for the profile
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


        //Initialize DB access and a postings list
        postings = new ArrayList<>();
        accessPostings = new AccessPostings();

        //Populate postings
        accessPostings.getPostingsByUserId(postings, user.getUserId());





        return v;
    }
}
