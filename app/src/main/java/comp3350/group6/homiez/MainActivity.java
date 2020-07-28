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
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationMethod=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Intent selfIntent = null;
            Bundle b = getIntent().getExtras();
            switch(menuItem.getItemId()) {
                case R.id.find_room:
                    selfIntent = new Intent(MainActivity.this, PostingActivity.class);
                    b.putBoolean("self_posting", false);
                    break;
                case R.id.your_rooms:
                    selfIntent = new Intent(MainActivity.this, PostingActivity.class);
                    b.putBoolean("self_posting", true);
                    break;
                case R.id.profile:
                    //selfIntent = new Test3Fragment();
                    break;
            }
            selfIntent.putExtras(b);
            MainActivity.this.startActivity(selfIntent);
            return true;
        }
    };
}
