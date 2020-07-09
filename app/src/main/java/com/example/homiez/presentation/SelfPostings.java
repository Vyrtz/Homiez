package com.example.homiez.presentation;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.homiez.R.layout;
import com.example.homiez.R.id;
import com.example.homiez.application.Main;
import com.example.homiez.objects.Posting;
import com.example.homiez.business.AccessPostings;

public class SelfPostings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Main.startUp();

        super.onCreate(savedInstanceState);
        setContentView(layout.self_postings);

//        Bundle b = getIntent().getExtras();
//        String userID = b.getString("userID");

        final ArrayList<Posting> postings = new ArrayList<>();
        AccessPostings accessPostings = new AccessPostings();

        accessPostings.getPostingsByUserId(postings, "1");

        final ArrayAdapter<Posting> adapter = new ArrayAdapter<Posting>(this, android.R.layout.simple_list_item_2, android.R.id.text1, postings)
        {
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

        ListView listView = (ListView)findViewById(id.postingsList);
        listView.setAdapter(adapter);
    }
}
