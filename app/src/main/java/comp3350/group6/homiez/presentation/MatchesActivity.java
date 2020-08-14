package comp3350.group6.homiez.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.group6.homiez.R;
import comp3350.group6.homiez.business.AccessMatches;
import comp3350.group6.homiez.business.AccessPostings;
import comp3350.group6.homiez.business.AccessUser;
import comp3350.group6.homiez.objects.Match;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MatchesActivity extends AppCompatActivity {

    //business stuff
    private AccessMatches accessMatches;
    private AccessPostings accessPostings;
    private AccessUser accessUser;

    private List<HashMap<String, String>> matchList;

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
        matchList = new ArrayList<>();


        if (direction.equals("user")) {//posting on top, user on bottom
            u = accessUser.getUser(id);
            accessMatches.getMatchesForUser(matches, id);
            for (Match m : matches) {
                HashMap<String, String> map = new HashMap<>();
                Posting post = accessPostings.getPostingById(m.getPostingId());
                map.put("Top", post.getTitle());
                map.put("Bottom",post.getUser().getName());
                map.put("ID", post.getUser().getUserId());
                matchList.add(map);
            }
        }
        else { //user on top, posting on bottom
            ArrayList<Posting> allpostings = new ArrayList<>();
            ArrayList<Match> allMacthes = new ArrayList<>();
            accessPostings.getPostingsByUserId(allpostings, id);
            for (Posting posting : allpostings) {
                accessMatches.getMatchesForPosting(matches, posting.getPostingId());
                allMacthes.addAll(matches);
                matches.clear();
            }
            for (Match m : allMacthes) {
                HashMap<String, String> map = new HashMap<>();
                map.put("Top", accessUser.getUser(m.getUserId()).getName());
                map.put("Bottom",accessPostings.getPostingById(m.getPostingId()).getTitle());
                map.put("ID", accessUser.getUser(m.getUserId()).getUserId());
                matchList.add(map);
            }
        }
        //Messages.warning(this,matchList.toString());

        SimpleAdapter adapter = new SimpleAdapter(this, matchList, R.layout.match, new String[]{"Top", "Bottom"}, new int[]{R.id.matchTop, R.id.matchBottom});
        listMatches.setAdapter(adapter);

        listMatches.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> a, View v, int p, long id) {
                Intent intent = new Intent(MatchesActivity.this, ShareInfoActivity.class);
                Bundle bundle = getIntent().getExtras();
                bundle.putString("profileID", matchList.get(p).get("ID"));
                intent.putExtras(bundle);
                startActivity(intent);
            }

        });
    }
}
