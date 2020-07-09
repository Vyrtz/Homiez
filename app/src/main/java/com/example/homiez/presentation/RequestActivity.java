package com.example.homiez.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.homiez.R;
import com.example.homiez.business.AccessMatches;
import com.example.homiez.business.AccessPostings;
import com.example.homiez.business.AccessRequests;
import com.example.homiez.business.AccessUser;
import com.example.homiez.business.Matching;
import com.example.homiez.objects.Posting;
import com.example.homiez.objects.Request;
import com.example.homiez.objects.User;

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
        //goBack();
        finish();
    }
    public void declineRequest (View view)
    {
        String result = Matching.DeclineRequest(accessRequests,requestUserId,postingId);
        if(result == null){
            Messages.fatalError(this, "Failure while declining requests ");
        }
        else{
            //goBack();
            finish();
        }
    }
    public void goBack(){
        Intent singleReq = new Intent(RequestActivity.this, RequestsActivity.class);
        Bundle newb = new Bundle();
        newb.putString("userID", mainUser);
        singleReq.putExtras(newb);
        RequestActivity.this.startActivity(singleReq);
    }
}
