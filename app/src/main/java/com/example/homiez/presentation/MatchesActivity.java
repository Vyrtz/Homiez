package com.example.homiez.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.homiez.R;
import com.example.homiez.business.AccessMatches;
import com.example.homiez.business.AccessPostings;
import com.example.homiez.business.AccessUser;
import com.example.homiez.objects.Match;
import com.example.homiez.objects.Posting;
import com.example.homiez.objects.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MatchesActivity extends Activity {

    private AccessMatches accessMatches;
    private AccessPostings accessPostings;
    private AccessUser accessUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matches);
        //Bundle b = getIntent.getExtras();
        String id = "3";//b.getString("id");                    //The id of the user or posting, depending on where the user is coming from
        String direction = "user";//b.getString("direction");   //Where the user is coming from. user= matches from their requests, post= matches for their post

        ArrayList<Match> matches = new ArrayList<Match>();//the list of matches
        User u;//the user
        Posting p;//the post

        ListView listMatches = (ListView) findViewById(R.id.listMatches);
        HashMap<String, String> pairs = new HashMap<>(); //user/posting pairs or posting/user pairs
        List<HashMap<String, String>> matchList = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, matchList, R.layout.matches, new String[]{"Top", "Bottom"}, new int[]{R.id.matchTop, R.id.matchBottom});


        if(direction.equals("user")){//posting on top, user on bottom
            u = accessUser.getUser(id);
            accessMatches.getMatchesForUser(matches, id);
            for(Match m : matches)
                pairs.put(accessPostings.getPostingById(m.getPostingId()).getTitle(), u.getName());

        } else{//user on top, posting on bottom
            p = accessPostings.getPostingById(id);
            accessMatches.getMatchesForPosting(matches, id);
            for(Match m : matches)
                pairs.put(accessUser.getUser(m.getUserId()).getName(), p.getTitle());
        }

        Iterator it = pairs.entrySet().iterator();
        while(it.hasNext()){
            HashMap<String, String> map = new HashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            map.put("Top", pair.getKey().toString());
            map.put("Bottom", pair.getValue().toString());
            matchList.add(map);
        }

        listMatches.setAdapter(adapter);

    }
}
