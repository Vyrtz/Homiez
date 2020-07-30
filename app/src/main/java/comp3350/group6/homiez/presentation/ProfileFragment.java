package comp3350.group6.homiez.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
    private ArrayList<Posting> postings;
    private AccessPostings accessPostings;
    private User user;
    private Boolean customize;
    private Boolean self;

    final private String HEADER_SUFFIX = "'s Profile";

    public ProfileFragment(){
        //Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        customize = false;

        //Grab the user
        Bundle b = this.getArguments();
        accessUser = new AccessUser();
        user = accessUser.getUser(b.getString("userID"));
        self = b.getBoolean("selfProfile");

        View v = inflater.inflate(R.layout.profile, container, false);

        //Initialize UI element variables
        TextView header = v.findViewById(R.id.header);
        TextView name = v.findViewById(R.id.name);
        TextView age = v.findViewById(R.id.age);
        TextView gender = v.findViewById(R.id.gender);
        TextView biography = v.findViewById(R.id.bio);
        TextView interests = v.findViewById(R.id.interests);

        //Set up button listener
        Button customizeButt = v.findViewById(R.id.submitButt);
        customizeButt.setOnClickListener(this);

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

        // Set up the Visuals for each posting in the list
        final ArrayAdapter<Posting> adapter = new ArrayAdapter<Posting>(getActivity(), android.R.layout.simple_list_item_2, android.R.id.text1, postings) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(postings.get(position).getTitle());
                text2.setText(postings.get(position).getLocation() + ", $" + postings.get(position).getPrice());

                return view;
            }
        };

        ListView postingsList = v.findViewById(R.id.postingList);
        postingsList.setAdapter(adapter);

        if(self) {
            customizeButt.setVisibility(View.VISIBLE);
        }


        return v;
    }

    public void onClick(View view) {
        Intent startIntent = new Intent(getActivity(), CustomizeProfileActivity.class);
        Bundle b = this.getArguments();
        startIntent.putExtras(b);
        startActivity(startIntent);

    }

}
