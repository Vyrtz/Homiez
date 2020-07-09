package com.example.homiez.application;

import com.example.homiez.objects.Posting;
import com.example.homiez.objects.User;

public class Main {

    public static final String dbName = "iteration1";

    public static void main(String[] args) {
        startUp();

        shutDown();

    }

    public static void startUp(){
        Services.createDataAccess(dbName);
    }

    public static void shutDown(){
        Services.closeDataAccess();
    }
}
