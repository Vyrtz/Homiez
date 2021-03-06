package comp3350.group6.homiez.presentation;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import comp3350.group6.homiez.R;
import comp3350.group6.homiez.business.AccessPostings;
import comp3350.group6.homiez.business.AccessUser;
import comp3350.group6.homiez.business.Compatibility;
import comp3350.group6.homiez.business.CompatibilityController;
import comp3350.group6.homiez.objects.Interest;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.User;

public class PublicProfileActivity extends Activity {

    private AccessPostings accessPostings;
    private AccessUser accessUser;

    final private String HEADER_SUFFIX = "'s Profile";

    private ArrayList<Posting> postings;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_profile);

        //Initialize DB access
        accessPostings = new AccessPostings();
        accessUser = new AccessUser();
        Compatibility c = new CompatibilityController();

        Bundle bundle = getIntent().getExtras();

        User user = accessUser.getUser(bundle.getString("profileID"));
        User self = accessUser.getUser(bundle.getString("userID"));

        //Initialize UI element variables
        TextView header = findViewById(R.id.header);
        TextView name = findViewById(R.id.name);
        TextView age = findViewById(R.id.age);
        TextView gender = findViewById(R.id.gender);
        TextView biography = findViewById(R.id.bio);
        TextView interests = findViewById(R.id.interests);
        TextView budget = findViewById(R.id.budget);
        TextView match = findViewById(R.id.match);

        ListView postingList = findViewById(R.id.postingList);
        postingList.setNestedScrollingEnabled(true);

        biography.setMovementMethod(new ScrollingMovementMethod());
        interests.setMovementMethod(new ScrollingMovementMethod());

        //Fill in the fields
        BigDecimal matchPercent = new BigDecimal(c.calculateCompatibility(user, self)).setScale(2, RoundingMode.HALF_UP);
        match.setText("Match : " + matchPercent +"%");
        header.setText(user.getName() + HEADER_SUFFIX);
        name.setText(user.getName());
        age.setText("" + user.getAge());
        gender.setText(user.getGender());
        biography.setText(user.getBiography());
        budget.setText("" + user.getBudget());

        //Build the string for the interests
        ArrayList<Interest> interestList = user.getInterests();

        String interestText = "";
        for (int i = 0; i < interestList.size(); i++) {
            Interest temp = interestList.get(i);
            interestText += temp.getInterest();
            if (i != interestList.size()-1) {
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
        final ArrayAdapter<Posting> adapter = new ArrayAdapter<Posting>(this, R.layout.postinglist_white, R.id.list_content, postings) {
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

        ListView postingsList = findViewById(R.id.postingList);
        postingsList.setAdapter(adapter);
    }
}
