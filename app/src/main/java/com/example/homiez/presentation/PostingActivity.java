package com.example.homiez.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.homiez.R;
import com.example.homiez.business.AccessMatches;
import com.example.homiez.business.AccessPostings;
import com.example.homiez.business.AccessRequests;
import com.example.homiez.business.AccessUser;
import com.example.homiez.business.Matching;
import com.example.homiez.objects.Posting;

public class PostingActivity extends Activity {

    private AccessPostings accessPostings;
    private AccessRequests accessRequests;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.posting);

        Bundle b = getIntent().getExtras();
        final String postingID = b.getString("postingId");

        if(b.getBoolean("self_posting")){
            setContentView(R.layout.self_posting);
        }else{
            setContentView(R.layout.posting);
        }

        accessPostings = new AccessPostings();
        accessRequests = new AccessRequests();

        Posting post = accessPostings.getPostingById(postingID);

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
    }
}
