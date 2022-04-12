package com.example.mixer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class DetailActivity extends AppCompatActivity {
    public static final String TAG = "DetailActivity";    // Create a tag for logging this activity

    // Define views
    TextView tvName;
    TextView tvAlcoholic;
    TextView tvCategory;
    TextView tvInstructions;
    ListView lvIngredients;
    ImageView ivPoster;
    LikeButton icFavorite;
    int drinkId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();   // Hide Action bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvName = findViewById(R.id.tvName);
        tvAlcoholic = findViewById(R.id.tvAlcoholic);
        tvCategory = findViewById(R.id.tvCategory);
        ivPoster = findViewById(R.id.ivPoster);
        tvInstructions = findViewById(R.id.tvInstructions);
        lvIngredients = findViewById(R.id.lvIngredients);
        icFavorite = findViewById(R.id.icFavorite);

        Drink drink = Parcels.unwrap(getIntent().getParcelableExtra("drink"));
        tvName.setText(drink.getDrinkName());
        tvAlcoholic.setText(drink.getDrinkIBA());
        tvCategory.setText(drink.getDrinkCategory());
        tvInstructions.setText(drink.getDrinkInstructions());
        drinkId = drink.getDrinkID();
        queryFav(drinkId);

        // Instantiating List View
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                DetailActivity.this,
                R.layout.list_layout,
                drink.getDrinkIngredients()
        );

        lvIngredients.setAdapter(arrayAdapter);

        String imageUrl = drink.getPosterPath();

        // add rounded corners to images
        int radius = 30; // corner radius, higher value = more rounded
        int margin = 10; // crop margin, set to 0 for corners with no crop
        Glide.with(DetailActivity.this).load(imageUrl).fitCenter().transform(new RoundedCornersTransformation(radius, margin)).into(ivPoster);

        // Favorite icon
        //Check if drink is already a favorite and associate the like button accordingly.
        icFavorite.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                saveFav(ParseUser.getCurrentUser());
            }
            @Override
            public void unLiked(LikeButton likeButton) {
                unFav(drinkId);
            }
        });

    }
// This method predetermines whether the button is like or not
    private void queryFav(int drinkId) {
        ParseQuery<Favorites> query = ParseQuery.getQuery(Favorites.class);
        query.include(Favorites.KEY_USER);
        query.whereEqualTo(Favorites.KEY_USER, ParseUser.getCurrentUser());
        query.addDescendingOrder(Favorites.KEY_CREATED_AT);

        query.findInBackground(new FindCallback<Favorites>() {
            @Override
            public void done(List<Favorites> objects, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issues with getting favorites", e);
                    return;
                }

                for (Favorites favorites : objects) {
                    Log.i(TAG, "drink id = " + drinkId + "favorite Id = " + favorites.getDrinkID());
                    if (drinkId == favorites.getDrinkID()) {
                        icFavorite.setLiked(true);
                        return;
                    } else {
                        icFavorite.setLiked(false);
                    }
                }
            }
        });
    }
    private void saveFav(ParseUser currentUser){
        Favorites fav = new Favorites();
        fav.setUser(currentUser);
        fav.setDrinkId(drinkId);
        fav.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issues with setting favorites", e);
                    return;
                }
            }
        });

    }
    private void unFav(int drinkId){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Favorites");
        query.whereEqualTo("drinkID", drinkId);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                // if the error is null.
                if (e == null) {
                    // on below line we are getting the first cocktail and
                    // calling a delete method to delete this cocktail.
                    objects.get(0).deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            // inside done method checking if the error is null or not.
                            if (e == null) {
                                Toast.makeText(DetailActivity.this, "Favorite Removed..", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(DetailActivity.this, "Failed to remove Favorite..", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(DetailActivity.this, "Failed to get the object..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

