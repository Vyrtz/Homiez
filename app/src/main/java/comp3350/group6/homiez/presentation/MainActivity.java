package comp3350.group6.homiez.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import comp3350.group6.homiez.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private boolean self_posting;
    private String userID;
    private Fragment public_postings;
    private boolean onSelfPostings = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigationbar);

        bottomNavigationView=findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationMethod);

        public_postings = new ViewPostingsFragment();
        Bundle b = getIntent().getExtras();
        b.putBoolean("self_posting", false);
        public_postings.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, public_postings).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationMethod=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment = null;
            switch(menuItem.getItemId()) {
                case R.id.find_room:
                    fragment = new ViewPostingsFragment();
                    Bundle b = getIntent().getExtras();
                    b.putBoolean("self_posting", false);
                    fragment.setArguments(b);
                    onSelfPostings = false;
                    break;
                case R.id.your_rooms:
                    fragment = new ViewPostingsFragment();
                    Bundle b1 = getIntent().getExtras();
                    b1.putBoolean("self_posting", true);
                    fragment.setArguments(b1);
                    onSelfPostings = true;
                    break;
                case R.id.profile:

                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            return true;
        }
    };

    public void onResume() {
        super.onResume();
        if (onSelfPostings) {
            public_postings = new ViewPostingsFragment();
            Bundle b1 = getIntent().getExtras();
            b1.putBoolean("self_posting", true);
            public_postings.setArguments(b1);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, public_postings).commit();
         }
        onSelfPostings = false;
    }
}
