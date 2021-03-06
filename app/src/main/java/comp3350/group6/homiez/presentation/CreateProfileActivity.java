package comp3350.group6.homiez.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import comp3350.group6.homiez.R;
import comp3350.group6.homiez.application.Shared.QueryResult;
import comp3350.group6.homiez.business.AccessUser;
import comp3350.group6.homiez.objects.Contact;
import comp3350.group6.homiez.objects.Interest;
import comp3350.group6.homiez.objects.User;

import static comp3350.group6.homiez.application.Shared.isNotNullOrBlank;


public class CreateProfileActivity extends Activity {

    final private String SUCCESS = "Profile created!";
    final private String SUCCESS_TITLE = "Success";
    final private String ERROR = "Error: Could not create a new profile";

    private AccessUser accessUser;

    private String userID;
    private String name;
    private String gender;
    private String biography;
    private String interests;
    private String password;
    private String contact;

    private int age;

    private double budget;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accessUser = new AccessUser();
        setContentView(R.layout.create_profile);
    }

    public void createPressed(View v) {

        //Fetch userID
        EditText fields = findViewById(R.id.editID);
        userID = fields.getText().toString(); //

        //Fetch password
        fields = findViewById(R.id.editPassword);
        password = fields.getText().toString();

        //Fetch name
        fields = findViewById(R.id.editName);
        name = fields.getText().toString(); //

        //Fetch the age - check if there was anything entered into the field
        fields = findViewById(R.id.editAge);
        if (checkFieldNotEmpty(fields)) {
            age = Integer.parseInt(fields.getText().toString());
            if (age > 150 || age <= 0) {
                Messages.warning(this, "Error: Age value invalid");
                return;
            }
        }

        if (isNotNullOrBlank(userID) && isNotNullOrBlank(password) && isNotNullOrBlank(name)) {

            //Fetch gender
            fields = findViewById(R.id.editGender);
            gender = fields.getText().toString();

            //Fetch contact info
            fields = findViewById(R.id.editContact);
            contact = fields.getText().toString();

            //Fetch budget
            fields = findViewById(R.id.editBudget);
            if (checkFieldNotEmpty(fields)) {
                budget = Double.parseDouble(fields.getText().toString());
            }

            //Fetch biography
            fields = findViewById(R.id.editBiography);
            biography = fields.getText().toString();

            //Fetch interests
            fields = findViewById(R.id.editInterest);
            interests = fields.getText().toString();

            User newUser = new User(userID, name, age, gender, budget, biography);

            //split up the different interests into separate strings
            String[] interestList = interests.split(",");

            //Store the strings of interests into the arrayList
            for (String interest : interestList) {
                interest = interest.trim();
                if (!interest.trim().equals("")) { //Check that the string we're storing isn't empty
                    newUser.addUniqueInterest(new Interest(interest));
                }
            }

            //Checks if the user was inserted into the DB correctly
            if (accessUser.insertUser(newUser, password) == QueryResult.FAILURE) {
                Messages.fatalError(this, ERROR);
            } else {
                accessUser.updateContactInfoForUser(newUser, new Contact(contact));
                Messages.popup(this, SUCCESS, SUCCESS_TITLE);
            }
        }
        else {
            Messages.fatalError(this, ERROR+" userid, password and name are required");
        }
    }

    private boolean checkFieldNotEmpty (EditText t) {
        return t.getText().toString().equals("") ? false : true;
    }
}
