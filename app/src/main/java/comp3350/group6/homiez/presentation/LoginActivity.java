package comp3350.group6.homiez.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import comp3350.group6.homiez.R;
import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.business.AccessUser;

public class LoginActivity extends Activity {

    private AccessUser accessUser;
    final private String NOT_FOUND = "User not found";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Main.startUp();

        accessUser = new AccessUser();

        setContentView(R.layout.login_page);

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        Main.shutDown();
    }

    public void loginPressed(View v){
        //fetch the userID and password from UI fields
        EditText IDField = findViewById(R.id.editUserID);
        EditText passField = findViewById(R.id.editPassword);

        String userID = IDField.getText().toString();
        String password = IDField.getText().toString();

        //TODO: Check for correct password associated with the user once done

        if(accessUser.getUser(userID) != null){
            Intent startIntent = new Intent(LoginActivity.this, StartActivity.class);
            Bundle b = new Bundle();
            b.putString("userID", userID);
            startIntent.putExtras(b);
            LoginActivity.this.startActivity(startIntent);

        }else{
            Messages.warning(this, NOT_FOUND);
        }

    }

    //Called when signup is called
    public void signupPressed(View v){
        Intent startIntent = new Intent(LoginActivity.this, CreateProfileActivity.class);
        LoginActivity.this.startActivity(startIntent);

    }

}
