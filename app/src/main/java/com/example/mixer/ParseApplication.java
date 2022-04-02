package com.example.mixer;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Register Favorites model
        ParseObject.registerSubclass(Favorites.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("3F4CjSFAjFTQuhucadJUzyE4xFJFj9qndccZJmrE")
                .clientKey("XiUDVjKgsUk53noIP8z34fLrMIT0qaIOHzmRBJeG")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
