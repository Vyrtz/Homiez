package comp3350.group6.homiez.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import comp3350.group6.homiez.R;
import comp3350.group6.homiez.business.AccessUser;
import comp3350.group6.homiez.business.AccessPostings;
import comp3350.group6.homiez.objects.Posting;

public class ProfileFragment extends Fragment{

    private String userID;
    private AccessUser accessUser;
    private ArrayList<Posting> postings;
    private AccessPostings accessPostings;

    public ProfileFragment(){
        //Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Grab the user
        Bundle b = this.getArguments();
        userID = b.getString("userID");

        View v;
        postings = new ArrayList<>();

        accessPostings = new AccessPostings();

        v = inflater.inflate(R.layout.profile, container, false);



        return v;
    }
}
