package com.example.homiez.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.homiez.R;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.start_page);

    }

    //Called when looking for a room gets pressed
    public void lookingPressed(View v){
        Intent selfIntent = new Intent(StartActivity.this, ViewPostings.class);

        Bundle b = getIntent().getExtras();
        b.putString("is_posting", "false");
        selfIntent.putExtras(b);

        StartActivity.this.startActivity(selfIntent);
    }

    //Called when posting room is pressed
    public void postingPressed(View v){
        Intent selfIntent = new Intent(StartActivity.this, ViewPostings.class);

        Bundle b = getIntent().getExtras();
        b.putString("is_posting", "true");
        selfIntent.putExtras(b);

        StartActivity.this.startActivity(selfIntent);
    }

}
