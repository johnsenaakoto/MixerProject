package com.example.mixer;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("3F4CjSFAjFTQuhucadJUzyE4xFJFj9qndccZJmrE")
                .clientKey("XiUDVjKgsUk53noIP8z34fLrMIT0qaIOHzmRBJeG")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
