package com.example.mixer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Movie;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class DetailActivity extends AppCompatActivity {

    // Define views
    TextView tvName;
    TextView tvAlcoholic;
    TextView tvCategory;
    TextView tvInstructions;
    ListView lvIngredients;
    ImageView ivPoster;
    ImageButton icFavorite;

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

        // Instantiating List View
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                DetailActivity.this,
                R.layout.list_layout,
                drink.drinkIngredients
        );

        lvIngredients.setAdapter(arrayAdapter);

        String imageUrl = drink.getPosterPath();

        // add rounded corners to images
        int radius = 30; // corner radius, higher value = more rounded
        int margin = 10; // crop margin, set to 0 for corners with no crop
        Glide.with(DetailActivity.this).load(imageUrl).fitCenter().transform(new RoundedCornersTransformation(radius, margin)).into(ivPoster);

        // Favorite icon
        // TODO: Logic for favorite icon
        icFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailActivity.this, "Added to Favorite Drinks", Toast.LENGTH_LONG).show();
            }
        });

    }
}