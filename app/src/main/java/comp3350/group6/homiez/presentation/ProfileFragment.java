package comp3350.group6.homiez.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import comp3350.group6.homiez.business.AccessUser;

public class ProfileFragment extends Fragment{

    private String userID;
    private AccessUser accessUser;

    public ProfileFragment(){
        //Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle b = this.getArguments();
        userID = b.getString("userID");
    }
}
