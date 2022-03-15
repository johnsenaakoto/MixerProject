package com.example.mixer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Movie;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

    }
}