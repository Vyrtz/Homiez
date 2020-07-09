package com.example.homiez.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.homiez.R;
import com.example.homiez.application.Main;
import com.example.homiez.business.AccessUser;

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
        EditText IDField = findViewById(R.id.editUserID);
        String userID = IDField.getText().toString();

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

}
