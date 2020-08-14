package comp3350.group6.homiez.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import comp3350.group6.homiez.R;
import comp3350.group6.homiez.application.Shared.QueryResult;
import comp3350.group6.homiez.business.AccessPostings;
import comp3350.group6.homiez.business.AccessUser;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.User;

import static comp3350.group6.homiez.application.Shared.isNotNullOrBlank;

public class CreatePostingActivity extends Activity {
    private AccessPostings accessPostings;
    private String userId;
    private static String currentPostingId;
    private AccessUser accessUser;

    private String title;
    private String description;
    private String name;
    private String type;
    private String location;
    private String price;

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
        EditText titleE  = (EditText) findViewById(R.id.editTextTitle);
        EditText typeE  = (EditText) findViewById(R.id.editTextType);
        EditText locationE  = (EditText) findViewById(R.id.editTextLocation);
        EditText descriptionE  = (EditText) findViewById(R.id.editTextDescription);
        EditText priceE  = (EditText) findViewById(R.id.editTextPrice);
        EditText tenantsE  = (EditText) findViewById(R.id.editTextTenants);

        title = titleE.getText().toString();
        type = typeE.getText().toString();
        location = locationE.getText().toString();
        description = descriptionE.getText().toString();
        price = priceE.getText().toString();

        if (isNotNullOrBlank(price) && isNotNullOrBlank(title) && isNotNullOrBlank(location)) {

            double priceD = Double.parseDouble(price);

            User u = accessUser.getUser(userId);
            Posting p = new Posting(currentPostingId, title, u, priceD, location, type, description);
            String individualTenants[] = tenantsE.getText().toString().split(",");

            boolean failed = false;

            if (!tenantsE.getText().toString().trim().isEmpty()) {
                failed = addTenants(individualTenants, p);
            }

            if (!failed) {
                if (accessPostings.insertPosting(p) == QueryResult.FAILURE) {

                    Messages.fatalError(this, "failed to create the posting, check the values Note: price should be a double " + title.toString() + type.toString() + price.toString());
                } else {
                    finish();
                }
            } else {
                Messages.fatalError(this, "failed to create the posting, tenants do not exist");
            }
        }
        Messages.fatalError(this, "failed to create the posting, price, title and type are required ");
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
