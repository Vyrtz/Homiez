package comp3350.group6.homiez.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import comp3350.group6.homiez.R;
import comp3350.group6.homiez.application.Constants.QueryResult;
import comp3350.group6.homiez.business.AccessPostings;
import comp3350.group6.homiez.business.AccessUser;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.User;

public class CreatePostingActivity extends Activity {
    private AccessPostings accessPostings;
    private String userId;
    private static String currentPostingId;
    private AccessUser accessUser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_posting);
        Bundle b = getIntent().getExtras();
        userId = b.getString("userID");
        accessPostings = new AccessPostings();
        accessUser = new AccessUser();
        currentPostingId = java.util.UUID.randomUUID().toString();
    }
    public void clickCreatePosting(View view) {
        EditText title  = (EditText) findViewById(R.id.editTextTitle);
        EditText type  = (EditText) findViewById(R.id.editTextType);
        EditText location  = (EditText) findViewById(R.id.editTextLocation);
        EditText description  = (EditText) findViewById(R.id.editTextDescription);
        EditText price  = (EditText) findViewById(R.id.editTextPrice);
        double priceD = Double.parseDouble(price.getText().toString());
        User u = accessUser.getUser(userId);
        Posting p = new Posting(currentPostingId,title.getText().toString(),u,priceD, location.getText().toString(), type.getText().toString(), description.getText().toString());

        if(accessPostings.insertPosting(p) == QueryResult.FAILURE) {

            Messages.fatalError(this, "failed to create the posting, check the values Note: price should be a double " + title.toString() + type.toString() + price.toString());
        }
        else {
            finish();
        }
    }
}
