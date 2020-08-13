package comp3350.group6.homiez.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import comp3350.group6.homiez.R;
import comp3350.group6.homiez.application.Constants.QueryResult;
import comp3350.group6.homiez.business.AccessPostings;
import comp3350.group6.homiez.business.AccessUser;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.User;

public class EditPostingActivity extends Activity {

    private AccessPostings accessPostings;

    private TextView title;
    private EditText location;
    private EditText type;
    private EditText price;
    private EditText description;
    private EditText tenants;

    private Posting posting;
    private String postingId;
    private AccessUser accessUser;

    final private String HEADER_SUFFIX = "'s Posting";
    final private String ERROR = "Error: Couldn't update posting";
    final private String SUCCESS_HEADER = "Success!";
    final private String SUCCESS = "Posting updated";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_posting);

        accessPostings = new AccessPostings();
        accessUser = new AccessUser();
        Bundle b = getIntent().getExtras();
        postingId = b.getString("postingID");
        posting = accessPostings.getPostingById(postingId);

        title = findViewById(R.id.title);
        location = findViewById(R.id.location);
        type = findViewById(R.id.type);
        price = findViewById(R.id.price);
        description = findViewById(R.id.description);
        tenants = findViewById(R.id.tenants);

        title.setText(posting.getTitle() + HEADER_SUFFIX);
        location.setText(posting.getLocation());
        type.setText(posting.getType());
        price.setText(String.valueOf(posting.getPrice()));
        description.setText(posting.getDescription());

        String tenantText = "";
        for (User u :posting.getAttachedUsers()) {
            if (!u.equals(posting.getUser())) {
                tenantText += u.getUserId() + ",";
            }
        }
        if(!tenantText.isEmpty()) {
            tenantText.substring(0, tenantText.length() - 1);
            tenants.setText(tenantText);
        }
    }

    public void submitClicked (View v){
        
        //modify location
        posting.setLocation(location.getText().toString());

        //modify type
        posting.setType(type.getText().toString());

        //modify price
        posting.setPrice(Double.parseDouble(price.getText().toString()));

        //modify description
        posting.setDescription(description.getText().toString());

        String individualTenants[] = tenants.getText().toString().split(",");

        boolean failed = false;

        if(!tenants.getText().toString().trim().isEmpty()) {
            failed = addTenants(individualTenants, posting);
        }else{
            posting.setAttachedUsers(new ArrayList<User>());
        }


        if (failed) {
            Messages.fatalError(this, "failed to create the posting, tenants do not exist");
        }
        else {
            QueryResult result = accessPostings.updatePosting(posting);
            if(result == QueryResult.FAILURE) {
                Messages.fatalError(this, "Failure while updating posting ");
            }
            else {
                finish();
            }
        }
    }

    private boolean addTenants(String[] tenants, Posting p) {
        boolean failed = false;

        for (String s: tenants) {
            User t = accessUser.getUser(s.trim());
            if (t!= null) {
                p.addAttachedUser(t);
            }
            else {
                failed = true;
            }
        }

        return failed;
    }
}
