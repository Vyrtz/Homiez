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

    //business stuff
    private AccessMatches accessMatches;
    private AccessPostings accessPostings;
    private AccessUser accessUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matches);
        accessMatches = new AccessMatches();
        accessPostings = new AccessPostings();
        accessUser = new AccessUser();

        Bundle b = getIntent().getExtras();
        String id = b.getString("userID");                    //The id of the user or posting, depending on where the user is coming from
        String direction = b.getString("direction");   //Where the user is coming from. user= matches from their requests, post= matches for their post

        ArrayList<Match> matches = new ArrayList<Match>();//the list of matches
        User u;//the user
        Posting p;//the post

        ListView listMatches = (ListView) findViewById(R.id.listMatches);
        HashMap<String, String> pairs = new HashMap<>(); //user/posting pairs or posting/user pairs
        List<HashMap<String, String>> matchList = new ArrayList<>();


        if(direction.equals("user")){//posting on top, user on bottom
            u = accessUser.getUser(id);
            accessMatches.getMatchesForUser(matches, id);
            for(Match m : matches)
            {
                HashMap<String, String> map = new HashMap<>();
                Posting post = accessPostings.getPostingById(m.getPostingId());
                map.put("Top", post.getTitle());
                map.put("Bottom",post.getUser().getName());
                matchList.add(map);
            }
        } else{ //user on top, posting on bottom
            ArrayList<Posting> allpostings = new ArrayList<>();
            ArrayList<Match> allMacthes = new ArrayList<>();
            accessPostings.getPostingsByUserId(allpostings, id);
            for (Posting posting : allpostings)
            {
                accessMatches.getMatchesForPosting(matches, posting.getPostingId());
                allMacthes.addAll(matches);
                matches.clear();
            }
            for(Match m : allMacthes)
            {
                HashMap<String, String> map = new HashMap<>();
                map.put("Top", accessUser.getUser(m.getUserId()).getName());
                map.put("Bottom",accessPostings.getPostingById(m.getPostingId()).getTitle());
                matchList.add(map);
            }
        }
        Messages.warning(this,matchList.toString());

        SimpleAdapter adapter = new SimpleAdapter(this, matchList, R.layout.match, new String[]{"Top", "Bottom"}, new int[]{R.id.matchTop, R.id.matchBottom});
        listMatches.setAdapter(adapter);

    }
}
