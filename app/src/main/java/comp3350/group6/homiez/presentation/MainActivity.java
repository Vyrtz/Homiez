package comp3350.group6.homiez.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import comp3350.group6.homiez.R;

/*
    This Activity is used to display the bottom navigation bar and all the fragments that need to use it
 */
public class MainActivity extends AppCompatActivity {
    // Variables
    private BottomNavigationView bottomNavigationView;
    private Fragment public_postings;
    private boolean onSelfPostings = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigationbar);
        // Get the bottom navigation bar and add a listener to it for when one is clicked
        bottomNavigationView=findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationMethod);

        // By default show the public postings page
        public_postings = new ViewPostingsFragment();
        Bundle b = getIntent().getExtras();
        b.putBoolean("self_posting", false);
        public_postings.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, public_postings).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationMethod=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        // When an option on the navigation bar is selected
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment = null;
            // Find what item was selected and display a different page based on which it was
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
            // Display the page
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            return true;
        }
    };

    // Used to ensure that when you create a new posting that it appears on screen without another refresh
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
