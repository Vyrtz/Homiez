package com.example.homiez.presentation;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.homiez.R.layout;
import com.example.homiez.R.id;
import com.example.homiez.objects.Posting;
import com.example.homiez.business.AccessPostings;

public class SelfPostings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("testing ASDNAKSLUJFBGIASFJB:AISKBFJKASBFASJKF:B");
        super.onCreate(savedInstanceState);
        setContentView(layout.self_postings);

//        Bundle b = getIntent().getExtras();
//        String userID = b.getString("userID");

        final ArrayList<String> test = new ArrayList<>();

        AccessPostings aas = new AccessPostings();

        test.add("Horse");
        test.add("Cow");
        test.add("Camel");
        test.add("Sheep");
        test.add("Goat");

//        ArrayAdapter a = new ArrayAdapter(this, android.R.layout.simple_list_item_1, test);

//        ListView listView = (ListView)findViewById(id.listStudentCourses);
//        listView.setAdapter(a);
    }
}
