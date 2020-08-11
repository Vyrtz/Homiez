package comp3350.group6.homiez.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import comp3350.group6.homiez.R;
import comp3350.group6.homiez.business.AccessUser;
import comp3350.group6.homiez.objects.User;

public class ShareInfoActivity extends Activity {

    private AccessUser accessUser;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accessUser = new AccessUser();

        setContentView(R.layout.share_info);

        Bundle b = getIntent().getExtras();

        System.out.println(b.getString("profileID"));
        System.out.println(b.getString("userID"));
        user = accessUser.getUser(b.getString("profileID"));

        //Populate the fields
        TextView header = findViewById(R.id.header);
        TextView phoneNum = findViewById(R.id.phone);
        TextView email = findViewById(R.id.email);

        header.setText(user.getName() + "'s Contact Info");
        //TODO: Database changes for phone and email
        //phoneNum.setText(user.getPhone());
        //email.setText(user.getEmail());

    }

    public void viewProfile(View v){
        Intent startIntent = new Intent(ShareInfoActivity.this, PublicProfileActivity.class);
        Bundle bundle = getIntent().getExtras();
        bundle.putString("profileID", user.getUserId());
        startIntent.putExtras(bundle);
        startActivity(startIntent);
    }



}
