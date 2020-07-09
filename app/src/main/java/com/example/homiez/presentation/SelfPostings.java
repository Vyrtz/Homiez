package com.example.homiez.presentation;

import android.app.Activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.homiez.R.layout;
import com.example.homiez.R.id;
import com.example.homiez.application.Main;
import com.example.homiez.objects.Posting;
import com.example.homiez.business.AccessPostings;
import com.example.homiez.objects.Request;

public class SelfPostings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(layout.self_postings);

        // Uncomment this once it is connected from the previous page
        Bundle b = getIntent().getExtras();
        final String userID = b.getString("userID");


        final ArrayList<Posting> postings = new ArrayList<>();
        AccessPostings accessPostings = new AccessPostings();

        accessPostings.getPostingsByUserId(postings, userID);

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int p, long id) {
                Intent selfIntent = new Intent( SelfPostings.this, PostingActivity.class);

                Bundle bundle = getIntent().getExtras();
                bundle.putString("userID", userID);
                bundle.putString("postingId", postings.get(p).getPostingId());

                selfIntent.putExtras(bundle);
                SelfPostings.this.startActivity(selfIntent);
            }
        });
    }
    public void seeRequests(View view)
    {
        Intent singleReq = new Intent(SelfPostings.this, RequestsActivity.class);
        Bundle b = getIntent().getExtras();
        singleReq.putExtras(b);
        SelfPostings.this.startActivity(singleReq);
    }
}
