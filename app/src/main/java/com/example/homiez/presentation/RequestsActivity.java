package com.example.homiez.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.example.homiez.R;
import com.example.homiez.application.Main;
import com.example.homiez.business.AccessPostings;
import com.example.homiez.business.AccessRequests;
import com.example.homiez.business.AccessUser;
import com.example.homiez.objects.Posting;
import com.example.homiez.objects.Request;
import com.example.homiez.objects.User;

import java.util.ArrayList;

public class RequestsActivity extends Activity {
    private AccessRequests accessRequests;
    private ArrayList<Request> requests;
    private ArrayAdapter<Request> requestArrayAdapter;
    private AccessPostings accessPostings;
    private AccessUser accessUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.requests);

        accessRequests = new AccessRequests();
        accessPostings = new AccessPostings();
        accessUser = new AccessUser();
        Bundle b = getIntent().getExtras();
        String tempUser = b.getString("userId");
        ArrayList<Posting> allposts = new ArrayList<>();
        accessPostings.getPostingsByUserId(allposts, tempUser);
        requests = new ArrayList<>();
        for (Posting post: allposts)
        {
            ArrayList<Request> req = new ArrayList<>();
            String result = accessRequests.getRequestsForPosting(req, post.getPostingId());
            if(result == null)
            {
                Messages.fatalError(this, "Failure while getting requests for user id");
            }
            else
            {
                requests.addAll(req);
            }
        }
        if(requests == null)
        {
            Messages.fatalError(this, "Failure while getting requests for user id");
        }
        else
        {
            requestArrayAdapter = new ArrayAdapter<Request>(this, android.R.layout.simple_list_item_activated_2, android.R.id.text1, requests)
            {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);

                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                    User u = accessUser.getUser(requests.get(position).getUserId());
                    text1.setText(u.getName());
                    Posting p = accessPostings.getPostingById(requests.get(position).getPostingId());
                    text2.setText(p.getTitle());

                    return view;
                }
            };

            final ListView listView = findViewById(R.id.listRequests);
            listView.setAdapter(requestArrayAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent singleReq = new Intent(RequestsActivity.this, RequestActivity.class);
                    Request selected = requestArrayAdapter.getItem(position);
                    Bundle b = new Bundle();
                    b.putString("userId", selected.getUserId());
                    b.putString("postingId", selected.getPostingId());
                    singleReq.putExtras(b);
                    RequestsActivity.this.startActivity(singleReq);
                }
            });
        }
    }
}
