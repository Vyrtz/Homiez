package com.example.homiez.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.homiez.R;
import com.example.homiez.business.AccessPostings;
import com.example.homiez.objects.Posting;

public class PostingActivity extends Activity {

    private AccessPostings accessPostings;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.posting);

        //Uncomment when linked
        //Bundle b = getIntent().getExtras();
        //String postingID = b.getString("postingId");

        Posting post = accessPostings.getPostingById("0");

        TextView locationText = findViewById(R.id.locationText);
        TextView typeText = findViewById(R.id.typeText);
        TextView priceText = findViewById(R.id.priceText);
        TextView descriptionText = findViewById(R.id.descriptionText);

        locationText.setText(post.getLocation());
        typeText.setText(post.getType());
        priceText.setText("" +post.getPrice());
        descriptionText.setText(post.getDescription());




    }
}