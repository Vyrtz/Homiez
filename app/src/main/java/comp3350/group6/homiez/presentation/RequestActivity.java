package comp3350.group6.homiez.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.homiez.R;
import comp3350.group6.homiez.business.AccessMatches;
import comp3350.group6.homiez.business.AccessPostings;
import comp3350.group6.homiez.business.AccessRequests;
import comp3350.group6.homiez.business.AccessUser;
import comp3350.group6.homiez.business.Matching;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.User;

public class RequestActivity extends Activity {
    private AccessRequests accessRequests ;
    private AccessMatches accessMatches ;
    private String requestUserId;
    private String mainUser;
    private String postingId;
    private AccessPostings accessPostings;
    private AccessUser accessUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        accessRequests = new AccessRequests();
        accessPostings = new AccessPostings();
        accessUser = new AccessUser();
        accessMatches = new AccessMatches();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.request);

        Bundle b = getIntent().getExtras();
        requestUserId = b.getString("requestUserId");
        User u = accessUser.getUser(requestUserId);
        postingId = b.getString("postingId");
        mainUser = b.getString("userID");
        Posting p = accessPostings.getPostingById(postingId);
        TextView editID = (TextView)findViewById(R.id.userInfoText);
        editID.setText("   " + u.getName() +" " + u.getAge() );
        editID = (TextView) findViewById(R.id.PostingInfoText);
        editID.setText(" Sent a Match Request for:  " + p.getTitle());
    }
    public void acceptRequest (View view)
    {
        String result = Matching.AcceptRequest(accessRequests,accessMatches,requestUserId,postingId);
        if(result == null){
           Messages.fatalError(this, "Failure while accepting requests " );
        }
        finish();
    }
    public void declineRequest (View view)
    {
        String result = Matching.DeclineRequest(accessRequests,requestUserId,postingId);
        if(result == null){
            Messages.fatalError(this, "Failure while declining requests ");
        }
        else{
            finish();
        }
    }
}
