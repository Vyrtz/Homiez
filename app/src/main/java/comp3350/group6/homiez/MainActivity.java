package comp3350.group6.homiez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import comp3350.group6.homiez.presentation.PostingActivity;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView=findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationMethod);

        Fragment public_postings = new Test1Fragment();
        Bundle b = getIntent().getExtras();
        b.putBoolean("self_posting", true);
        public_postings.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, public_postings).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationMethod=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment = null;
            switch(menuItem.getItemId()) {
                case R.id.find_room:
                    fragment = new Test1Fragment();
                    break;
                case R.id.your_rooms:
                    fragment = new Test2Fragment();
                    break;
                case R.id.profile:
                    fragment = new Test3Fragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            return true;
        }
    };
}
