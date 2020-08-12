package comp3350.group6.homiez.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import comp3350.group6.homiez.R;
import comp3350.group6.homiez.business.AccessPostings;
import comp3350.group6.homiez.objects.Posting;

public class EditPostingActivity extends Activity {

    private AccessPostings accessPostings;

    private TextView title;
    private EditText location;
    private EditText type;
    private EditText price;
    private EditText description;

    private Posting posting;
    private String postingId;

    final private String HEADER_SUFFIX = "'s Posting";
    final private String ERROR = "Error: Couldn't update posting";
    final private String SUCCESS_HEADER = "Success!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_posting);

        accessPostings = new AccessPostings();
        Bundle b = getIntent().getExtras();
        postingId = b.getString("postingID");
        posting = accessPostings.getPostingById(postingId);

        title = findViewById(R.id.title);
        location = findViewById(R.id.location);
        type = findViewById(R.id.type);
        price = findViewById(R.id.price);
        description = findViewById(R.id.description);

        title.setText(posting.getTitle() + HEADER_SUFFIX);
        location.setText(posting.getLocation());
        type.setText(posting.getType());
        price.setText(String.valueOf(posting.getPrice()));
        description.setText(posting.getDescription());
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

        String result = accessPostings.updatePosting(posting);
        if(result == null) {
            Messages.fatalError(this, "Failure while updating posting ");
        }
        else {
            finish();
        }
    }
}
