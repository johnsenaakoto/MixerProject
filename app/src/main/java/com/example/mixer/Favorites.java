package com.example.mixer;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

// Post class stores attributes of posts and allows us to retrieve and send posts to the backend
@ParseClassName("Favorites")
public class Favorites extends ParseObject {

    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_DRINK_ID = "drinkID";


    // Getter and setter functions for username and user info
    public ParseUser getUser() { return getParseUser(KEY_USER); }

    // Getter and setter functions for drink ID
    public int getDrinkID() { return getInt(KEY_DRINK_ID); }

}
