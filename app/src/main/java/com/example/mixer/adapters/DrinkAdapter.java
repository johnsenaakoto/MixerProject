package com.example.mixer.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mixer.DetailActivity;
import com.example.mixer.Drink;
import com.example.mixer.LoginActivity;
import com.example.mixer.MainActivity;
import com.example.mixer.R;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.ViewHolder>{

    Context context;
    List<Drink> drinks;

    public DrinkAdapter(Context context, List<Drink> drinks) {
        this.context = context;
        this.drinks = drinks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View drinkView = LayoutInflater.from(context).inflate(R.layout.item_drink, parent, false);
        return new ViewHolder(drinkView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the drink at the passed position
        Drink drink = drinks.get(position);
        // Bind the movie data into the VH
        holder.bind(drink);
    }

    @Override
    public int getItemCount() {
        return drinks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Define views
        TextView tvName;
        TextView tvAlcoholic;
        TextView tvCategory;
        ImageView ivPoster;
        RelativeLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAlcoholic = itemView.findViewById(R.id.tvAlcoholic);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(Drink drink) {
            tvName.setText(drink.getDrinkName());
            tvAlcoholic.setText(drink.getDrinkIBA());
            tvCategory.setText(drink.getDrinkCategory());
            String imageUrl = drink.getPosterPath();

            // add rounded corners to images
            int radius = 30; // corner radius, higher value = more rounded
            int margin = 10; // crop margin, set to 0 for corners with no crop
            Glide.with(context).load(imageUrl).fitCenter().transform(new RoundedCornersTransformation(radius, margin)).into(ivPoster); // Binding an image is more complex, we use Glide

            // Create an onClickListener on the drinks so that stuff can be done when it's clicked
            // 1. Register click listener on the whole row
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Navigate to Detail Activity on tap
                    Log.d("Toast", "Toast clicked for drink");

                    Intent i = new Intent(context, DetailActivity.class);
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity) context, ivPoster, "activityTransition");
                    i.putExtra("drink", Parcels.wrap(drink)); // send entire movie class using Parcels to DetailActivity
                    context.startActivity(i, options.toBundle());

                }
            });
        }
    }
}
