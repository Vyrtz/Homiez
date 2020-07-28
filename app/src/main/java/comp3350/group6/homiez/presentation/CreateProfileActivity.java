package comp3350.group6.homiez.presentation;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import comp3350.group6.homiez.R;
import comp3350.group6.homiez.application.Main;

import java.util.Calendar;


public class CreateProfileActivity extends Activity {

    //TODO: Change the manifest back once done testing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Main.startUp();

        setContentView(R.layout.create_profile);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Main.shutDown();
    }

}
