package comp3350.group6.homiez.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import comp3350.group6.homiez.R;
import comp3350.group6.homiez.objects.User;


public class CreateProfileActivity extends Activity {

    private String userID;
    private String name;
    private String gender;
    private String email;
    private String phoneNum;
    private String biography;
    private String interests;

    private int age;
    private int budget;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_profile);
    }

    public void createPressed(View v){
        //TODO: Input validation

        //Fetch userID
        EditText fields = findViewById(R.id.editID);
        userID = fields.getText().toString(); //

        //Fetch budget
        fields = findViewById(R.id.editBudget);
        if(!fields.getText().toString().equals("")) {
            budget = Integer.parseInt(fields.getText().toString());
        }

        //Fetch name
        fields = findViewById(R.id.editName);
        name = fields.getText().toString(); //

        //Fetch the age - check if there was anything entered into the field
        fields = findViewById(R.id.editAge);
        if(!fields.getText().toString().equals("")) {
            age = Integer.parseInt(fields.getText().toString());
        } //

        //Fetch gender
        fields = findViewById(R.id.editGender);
        gender = fields.getText().toString(); //

        //Fetch biography
        fields = findViewById(R.id.editBiography);
        biography = fields.getText().toString();

        //Fetch interests
        fields = findViewById(R.id.editInterest);
        interests = fields.getText().toString();

        //TODO: Flesh out the user we create then insert it into the database
        User newUser = new User(userID, name, age, gender);
    }

}
