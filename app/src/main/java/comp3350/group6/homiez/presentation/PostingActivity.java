package comp3350.group6.homiez.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import comp3350.group6.homiez.R;

import comp3350.group6.homiez.application.Constants.QueryResult;
import comp3350.group6.homiez.business.AccessMatches;
import comp3350.group6.homiez.business.AccessPostings;
import comp3350.group6.homiez.business.AccessRequests;
import comp3350.group6.homiez.business.Matching;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.User;

public class PostingActivity extends Activity {

    private AccessPostings accessPostings;
    private AccessRequests accessRequests;
    private AccessMatches accessMatches;
    private Posting post;
    private String postingID;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posting);

        Bundle b = getIntent().getExtras();
        postingID = b.getString("postingId");

        accessPostings = new AccessPostings();
        accessRequests = new AccessRequests();
        accessMatches = new AccessMatches();

        post = accessPostings.getPostingById(postingID);
        System.out.println("Test");
        System.out.println(post);

        if(b.getBoolean("self_posting")) {
            setContentView(R.layout.self_posting);
        }
        else { //Public posting
            setContentView(R.layout.posting);

            TextView userText = findViewById(R.id.userText);
            userText.setText(post.getUser().getName());

            ListView viewUsers = findViewById(R.id.userList);
            viewUsers.setNestedScrollingEnabled(true);

            ArrayList<User> users = post.getAttachedUsers();
            List<HashMap<String, String>> userList = new ArrayList<>();

            //Loop through every user
            for(User u : users){
                HashMap<String, String> map = new HashMap<>();
                map.put("Top", u.getName());
                map.put("Bottom", "" + u.getAge());
                userList.add(map);
            }

            SimpleAdapter adapter = new SimpleAdapter(this, userList,  android.R.layout.simple_list_item_2, new String[]{"Top", "Bottom"}, new int[]{android.R.id.text1, android.R.id.text2});
            viewUsers.setAdapter(adapter);

        }

        TextView titleText = findViewById(R.id.titleText);
        TextView locationText = findViewById(R.id.locationText);
        TextView typeText = findViewById(R.id.typeText);
        TextView priceText = findViewById(R.id.priceText);
        TextView descriptionText = findViewById(R.id.descriptionText);

        titleText.setText(post.getTitle());
        locationText.setText(post.getLocation());
        typeText.setText(post.getType());
        priceText.setText("" +post.getPrice());
        descriptionText.setText(post.getDescription());
    }
    public void sendMatch(View v) {
        Bundle b =getIntent().getExtras();
        String u = b.getString("userID");
        String p = b.getString("postingId");
        Matching.SendRequest(accessRequests,accessPostings,accessMatches,u,p);
        Messages.popup(this, "You have sent a match request!", "Match request sent");
    }

    public void openProfile(View v) {
        Intent startIntent = new Intent(PostingActivity.this, PublicProfileActivity.class);
        Bundle bundle = getIntent().getExtras();
        bundle.putString("profileID", accessPostings.getPostingById(bundle.getString("postingId")).getUser().getUserId());
        startIntent.putExtras(bundle);
        startActivity(startIntent);
    }

    public void editPosting(View v) {
        Intent intent = new Intent(PostingActivity.this, EditPostingActivity.class);
        Bundle bundle = getIntent().getExtras();
        bundle.putString("postingID", postingID);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void deletePosting(View v) {
        QueryResult result = accessPostings.deletePosting(post);
        if(result == QueryResult.FAILURE) {
            Messages.fatalError(this, "Failure while deleting posting ");
        }
        else {
            finish();
        }
    }

    public void onResume() {
        super.onResume();
        post = accessPostings.getPostingById(postingID);
        TextView titleText = findViewById(R.id.titleText);
        TextView locationText = findViewById(R.id.locationText);
        TextView typeText = findViewById(R.id.typeText);
        TextView priceText = findViewById(R.id.priceText);
        TextView descriptionText = findViewById(R.id.descriptionText);

        titleText.setText(post.getTitle());
        locationText.setText(post.getLocation());
        typeText.setText(post.getType());
        priceText.setText("" +post.getPrice());
        descriptionText.setText(post.getDescription());
    }
}
