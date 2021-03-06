package comp3350.group6.homiez.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import comp3350.group6.homiez.R;

import comp3350.group6.homiez.application.Shared.QueryResult;
import comp3350.group6.homiez.business.AccessMatches;
import comp3350.group6.homiez.business.AccessPostings;
import comp3350.group6.homiez.business.AccessRequests;
import comp3350.group6.homiez.business.AccessUser;
import comp3350.group6.homiez.business.CompatibilityController;
import comp3350.group6.homiez.business.Matching;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.User;

public class PostingActivity extends Activity {

    private AccessPostings accessPostings;
    private AccessRequests accessRequests;
    private AccessMatches accessMatches;
    private AccessUser accessUser;
    private Posting post;
    private String postingID;
    private User currentUser;


    private List<HashMap<String, String>> userList;

    private CompatibilityController compatibilityController;
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
        accessUser = new AccessUser();
        compatibilityController = new CompatibilityController();

        post = accessPostings.getPostingById(postingID);
        userList = new ArrayList<>();

        String uid = b.getString("userID");
        currentUser = accessUser.getUser(uid);

        if (b.getBoolean("self_posting")) {
            setContentView(R.layout.self_posting);
        }
        else { //Public posting
            setContentView(R.layout.posting);

            TextView userText = findViewById(R.id.userText);
            userText.setText(post.getUser().getName());
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

        setUserList( post, currentUser);

    }
    public void sendMatch(View v) {
        Bundle b =getIntent().getExtras();
        Matching.SendRequest(accessRequests,accessPostings,accessMatches,currentUser.getUserId(),postingID);
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
        if (result == QueryResult.FAILURE) {
            Messages.fatalError(this, "Failure while deleting posting ");
        }
        else {
            finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

        setUserList(post, currentUser);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setUserList(Posting post, User currentUser) {
        userList.clear();
        ListView viewUsers = findViewById(R.id.userList);
        viewUsers.setNestedScrollingEnabled(true);

        for (User u : post.getAttachedUsers()) {
            if (!u.equals(post.getUser())) {
                HashMap<String, String> map = new HashMap<>();
                map.put("Top", u.getName());
                BigDecimal matchPercent = new BigDecimal(compatibilityController.calculateCompatibility(currentUser,u)).setScale(2, RoundingMode.HALF_UP);
                map.put("Bottom", "Match : " + matchPercent+"%");
                map.put("ID", u.getUserId());
                userList.add(map);
            }
        }

        SimpleAdapter adapter = new SimpleAdapter(this, userList,  android.R.layout.simple_list_item_2, new String[]{"Top", "Bottom"}, new int[]{android.R.id.text1, android.R.id.text2});
        viewUsers.setAdapter(adapter);

        viewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            // Go to the posting based on which posting was clicked
            public void onItemClick(AdapterView<?> a, View v, int p, long id) {
                Intent intent = new Intent(PostingActivity.this, PublicProfileActivity.class);
                Bundle bundle = getIntent().getExtras();
                bundle.putString("profileID", userList.get(p).get("ID"));

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}
