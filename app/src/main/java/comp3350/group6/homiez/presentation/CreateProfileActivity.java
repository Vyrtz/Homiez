package comp3350.group6.homiez.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import comp3350.group6.homiez.R;
import comp3350.group6.homiez.objects.User;


public class CreateProfileActivity extends Activity {

    private String userID;
    private String password;
    private String name;
    private int age;
    private String gender;
    private String email;
    private String phoneNum;
    private String biography;
    private String interests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_profile);
    }

    public void createPressed(View v){
        //TODO: Input validation

        //Fetch userID
        EditText fields = findViewById(R.id.editID);
        userID = fields.getText().toString();

        //Fetch password
        fields = findViewById(R.id.editPassword);
        password = fields.getText().toString();

        fields = findViewById(R.id.editName);
        name = fields.getText().toString();

        //Fetch the age - check if there was anything entered into the field
        fields = findViewById(R.id.editAge);
        if(!fields.getText().toString().equals("")) {
            age = Integer.parseInt(fields.getText().toString());
        }

        //Fetch gender
        fields = findViewById(R.id.editGender);
        gender = fields.getText().toString();

        //Fetch email
        fields = findViewById(R.id.editEmail);
        email = fields.getText().toString();

        //Fetch phone number
        fields = findViewById(R.id.editPhone);
        phoneNum = fields.getText().toString();

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
