package com.example.mixer;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.mixer.adapters.DrinkAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    // Create string to hold http for API request
    public static final String RANDOM_DRINK_URL = "https://www.thecocktaildb.com/api/json/v1/1/random.php";

    // Create a tag for logging this activity
    public static final String TAG = "MainActivity";
    List<Drink> drinks;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();   // Hide Action bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvDrinks = findViewById(R.id.rvDrinks);
        drinks = new ArrayList<>();

        // Create the adapter
        DrinkAdapter drinkAdapter = new DrinkAdapter(this, drinks);

        // Set the adapter o the recycler view
        rvDrinks.setAdapter(drinkAdapter);

        // Set a layout manager
        rvDrinks.setLayoutManager(new LinearLayoutManager(this));
        queryDrinks(drinkAdapter);

        // Refresh screen when you swipe up
        swipeContainer = findViewById(R.id.swipeContainer);
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "fetching new data!");
                drinks.clear();
                drinkAdapter.notifyDataSetChanged();
                queryDrinks(drinkAdapter);
                swipeContainer.setRefreshing(false);
            }
        });
    }

    private void queryDrinks(DrinkAdapter drinkAdapter) {
        for (int i = 0; i < 10; i++) {
            // Instantiate an AsyncHttpClient to execute the API request
            AsyncHttpClient client = new AsyncHttpClient();
            final Drink[] drink = new Drink[1];

            // Make a get request on the client object
            client.get(RANDOM_DRINK_URL, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Headers headers, JSON json) {
                    Log.d(TAG, "Success"); // log for success, connected to TAG
                    JSONObject jsonObject = json.jsonObject; // we store the response jsonObject in the variable jsonObject
                    try {
                        JSONArray result = jsonObject.getJSONArray("drinks");
                        Log.i(TAG, "Results: " + result.toString()); //logs onSuccess and shows what is in results
                        drink[0] = Drink.fromJsonArray(result);
                        drinks.add(Drink.fromJsonArray(result));
                        drinkAdapter.notifyDataSetChanged();
                        Log.i(TAG, "Drinks is: " + drinks);
                    } catch (JSONException e) {
                        Log.e(TAG, "Hit json exception", e); // handles the exception if results is not in the jsonObject
                    }
                }
                @Override
                public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                    Log.d(TAG, "Failure: " + response);
                }
            });

        }
    }
}