package com.example.mixer.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.mixer.Drink;
import com.example.mixer.R;
import com.example.mixer.adapters.DrinkAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public static final String RANDOM_DRINK_URL = "https://www.thecocktaildb.com/api/json/v1/1/random.php"; // Create string to hold http for API request
    public static final String TAG = "HomeFragment";    // Create a tag for logging this activity
    public static int COLD_START = 1;

    private SwipeRefreshLayout swipeContainer;
    List<Drink> drinks = new ArrayList<>();


    public HomeFragment() {
        // Required empty public constructor
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //drinks = new ArrayList<>();

        RecyclerView rvDrinks = view.findViewById(R.id.rvDrinks);
        swipeContainer = view.findViewById(R.id.swipeContainer);

        // Create the adapter
        DrinkAdapter drinkAdapter = new DrinkAdapter(getContext(), drinks);

        // Set the adapter o the recycler view
        rvDrinks.setAdapter(drinkAdapter);

        // Set a layout manager
        rvDrinks.setLayoutManager(new LinearLayoutManager(getContext()));
        queryDrinks(drinkAdapter);


        // Refresh screen when you swipe up
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
                Log.d(TAG, "Refresh start drinks are: " + String.valueOf(drinks));
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