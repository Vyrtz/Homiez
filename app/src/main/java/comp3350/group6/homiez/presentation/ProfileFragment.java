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

        //Initialize DB access and a postings list
        postings = new ArrayList<>();
        accessPostings = new AccessPostings();

        //Populate postings
        accessPostings.getPostingsByUserId(postings, user.getUserId());

        // Set up the Visuals for each posting in the list
        final ArrayAdapter<Posting> adapter = new ArrayAdapter<Posting>(getActivity(), R.layout.postinglist_white, R.id.list_content, postings) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(R.id.list_content);
                TextView text2 = (TextView) view.findViewById(R.id.list_content2);
                TextView text3 = (TextView) view.findViewById(R.id.list_content3);

                text1.setText(postings.get(position).getTitle());
                text2.setText(postings.get(position).getLocation());
                text3.setText("$" + postings.get(position).getPrice());
                return view;
            }
        };

        ListView postingsList = v.findViewById(R.id.postingList);
        postingsList.setAdapter(adapter);

        return v;
    }

    public void onClick(View view) {
        Intent startIntent = new Intent(getActivity(), CustomizeProfileActivity.class);
        Bundle b = this.getArguments();
        startIntent.putExtras(b);
        startActivity(startIntent);

    }

}
