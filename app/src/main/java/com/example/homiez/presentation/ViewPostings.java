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
import com.example.homiez.objects.Posting;
import com.example.homiez.business.AccessPostings;

public class ViewPostings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        final String userID = b.getString("userID");
        final String is_posting = b.getString("is_posting");


        final ArrayList<Posting> postings = new ArrayList<>();
        AccessPostings accessPostings = new AccessPostings();

        if (is_posting.equals("true")) {
            accessPostings.getPostingsByUserId(postings, userID);
            setContentView(layout.your_postings);
        } else {
            accessPostings.getPostings(postings, userID);
            setContentView(layout.public_postings);
        }

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
                Intent selfIntent = new Intent( ViewPostings.this, PostingActivity.class);

                Bundle bundle = getIntent().getExtras();
                bundle.putString("userID", userID);
                bundle.putString("postingId", postings.get(p).getPostingId());

                selfIntent.putExtras(bundle);
                ViewPostings.this.startActivity(selfIntent);
            }
        });
    }
    public void seeRequests(View view)
    {
        Intent singleReq = new Intent(ViewPostings.this, RequestsActivity.class);
        Bundle b = getIntent().getExtras();
        singleReq.putExtras(b);
        ViewPostings.this.startActivity(singleReq);
    }
}
