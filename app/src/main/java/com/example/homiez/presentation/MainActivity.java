package com.example.homiez.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.homiez.R;
import com.example.homiez.application.Main;
import com.example.homiez.business.AccessPostings;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.self_postings);
    }
}
