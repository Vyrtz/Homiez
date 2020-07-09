package com.example.homiez.presentation;

import android.app.Activity;
import android.os.Bundle;

import com.example.homiez.R;
import com.example.homiez.application.Main;
import com.example.homiez.business.AccessUser;

public class LoginActivity extends Activity {

    private AccessUser accessUser;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Main.startUp();

        setContentView(R.layout.login_page);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        Main.shutDown();
    }
}
