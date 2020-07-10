package comp3350.group6.homiez.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.example.homiez.R;
import comp3350.group6.homiez.business.AccessPostings;
import comp3350.group6.homiez.business.AccessRequests;
import comp3350.group6.homiez.business.AccessUser;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.Request;
import comp3350.group6.homiez.objects.User;

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
        String tempUser = b.getString("userID");
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
                    Intent singleReq = new Intent(RequestsActivity.this, AnswerRequestActivity.class);
                    Request selected = requestArrayAdapter.getItem(position);
                    Bundle prev = getIntent().getExtras();
                    String tempUser = prev.getString("userID");
                    Bundle b = new Bundle();
                    b.putString("userID",tempUser);
                    b.putString("requestUserId", selected.getUserId());
                    b.putString("postingId", selected.getPostingId());
                    singleReq.putExtras(b);
                    RequestsActivity.this.startActivity(singleReq);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle b = getIntent().getExtras();
        onCreate(b);
    }
}
