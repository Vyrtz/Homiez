package comp3350.group6.homiez.presentation;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import comp3350.group6.homiez.R;
import comp3350.group6.homiez.business.AccessPostings;
import comp3350.group6.homiez.objects.Posting;

public class ViewPostingsFragment extends Fragment implements View.OnClickListener {

    private boolean self_posting = false;
    private String userID;
    private static int currentPosting = 6;

    public ViewPostingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle b = this.getArguments();
        userID = b.getString("userID");
        self_posting = b.getBoolean("self_posting");

        final ArrayList<Posting> postings = new ArrayList<>();
        AccessPostings accessPostings = new AccessPostings();

        View v = null;

        // Inflate the layout for this fragment
        if (self_posting) {
            accessPostings.getPostingsByUserId(postings, userID);
            v = inflater.inflate(R.layout.your_postings, container, false);

            Button button1 = (Button) v.findViewById(R.id.button_requests);
            button1.setOnClickListener(this);
            Button button3 = (Button) v.findViewById(R.id.button_create_posting);
            button3.setOnClickListener(this);
        } else {
            accessPostings.getPostings(postings, userID);
            v = inflater.inflate(R.layout.public_postings, container, false);
        }

        Button button = (Button) v.findViewById(R.id.button_matches);
        button.setOnClickListener(this);

        final ArrayAdapter<Posting> adapter = new ArrayAdapter<Posting>(getActivity(), android.R.layout.simple_list_item_2, android.R.id.text1, postings) {
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

        ListView listView = (ListView)v.findViewById(R.id.postingsList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int p, long id) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), PostingActivity.class);
                Bundle bundle = getActivity().getIntent().getExtras();
                bundle.putString("userID", userID);
                bundle.putString("postingId", postings.get(p).getPostingId());
                bundle.putBoolean("self_posting", self_posting);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void onClick(View view)
    {
        Intent singleReq = new Intent();
        Bundle bundle = getActivity().getIntent().getExtras();
        switch (view.getId())
        {
            case R.id.button_requests:
                singleReq.setClass(getActivity(), RequestsActivity.class);
                break;
            case R.id.button_matches:
                singleReq.setClass(getActivity(), MatchesActivity.class);
                if(self_posting) {
                    bundle.putString("direction","postings");
                }
                else {
                    bundle.putString("direction","user");
                }
                bundle.putString("userID", userID);
                break;
            case R.id.button_create_posting:
                singleReq.setClass(getActivity(), CreatePostingActivity.class);
                bundle.putString("userID", userID);
                bundle.putInt("createPostingId", currentPosting);
                currentPosting++;
                break;
        }
        singleReq.putExtras(bundle);
        getActivity().startActivity(singleReq);
    }
}