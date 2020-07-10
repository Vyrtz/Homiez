package comp3350.group6.homiez.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.homiez.R;

import comp3350.group6.homiez.business.AccessPostings;
import comp3350.group6.homiez.business.AccessRequests;
import comp3350.group6.homiez.business.Matching;
import comp3350.group6.homiez.objects.Posting;

public class PostingActivity extends Activity {

    private AccessPostings accessPostings;
    private AccessRequests accessRequests;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posting);

        Bundle b = getIntent().getExtras();
        final String postingID = b.getString("postingId");

        accessPostings = new AccessPostings();
        accessRequests = new AccessRequests();

        Posting post = accessPostings.getPostingById(postingID);

        if(b.getBoolean("self_posting")){
            setContentView(R.layout.self_posting);
        }else{
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
    }
    public void sendMatch(View v)
    {
        Bundle b =getIntent().getExtras();
        String u = b.getString("userID");
        String p = b.getString("postingId");
        Matching.SendRequest(accessRequests,accessPostings,u,p);
        Messages.popup(this, "You have sent a match request!", "Match request sent");
    }
}
