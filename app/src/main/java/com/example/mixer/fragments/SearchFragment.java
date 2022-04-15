package com.example.mixer.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.mixer.Drink;
import com.example.mixer.Favorites;
import com.example.mixer.MainActivity;
import com.example.mixer.R;
import com.example.mixer.adapters.DrinkAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.Headers;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    public static final String INGREDIENTS_URL = "https://www.thecocktaildb.com/api/json/v1/1/list.php?i=list";
    public static final String TAG = "SearchFragment";    // Create a tag for logging this activity
    ListView lvSearch;
    List<String> ingredients;
    String selectedIngredient;
    ArrayAdapter<String> arrayAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ingredients = new ArrayList<>();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lvSearch = view.findViewById(R.id.lvSearch);
        arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.search_list_layout, ingredients);
        //lvSearch.setAdapter(arrayAdapter);
        queryIngredients(arrayAdapter, lvSearch);

        SearchView searchView = view.findViewById(R.id.search_bar);

        // search view for search list
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                lvSearch.setVisibility(View.INVISIBLE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                lvSearch.setVisibility(View.VISIBLE);
                arrayAdapter.getFilter().filter(query);
                return false;
            }
        });
        // Set onItemClickListener for items in listview
        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedIngredient = (lvSearch.getItemAtPosition(i).toString());
                Toast.makeText(getContext(), selectedIngredient, Toast.LENGTH_SHORT).show();
                //queryDrinks(getDrinkAdapter());
            }
        });



            // use ingredient to query drinks
            // Extract ids
            // Query drinks
            // create recyclerview and populate drinks maybe inherit that from home fragment

    }

    //@Override
    private void queryDrinks(DrinkAdapter drinkAdapter) {
        String test = selectedIngredient;
        Log.d("Check pass", test);

    }

    // queryIngredients populates searchlist with drink ingredients
    private void queryIngredients(ArrayAdapter<String> arrayAdapter, ListView lvSearch) {
        // Instantiate an AsyncHttpClient to execute the API request
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(INGREDIENTS_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "Success"); // log for success, connected to TAG
                JSONObject jsonObject = json.jsonObject; // we store the response jsonObject in the variable jsonObject
                try {
                    JSONArray results = jsonObject.getJSONArray("drinks"); // we store the results array into new variable results. This results array is extracted from the jsonObject by using the getJSONArray method
                    for (int i=0; i < results.length(); i++) {
                        String tempResult = (results.getJSONObject(i)).getString("strIngredient1");
                        ingredients.add(tempResult);
                        Log.d(TAG, "Ingredient: " + tempResult);
                    }
                    // Add ingredients to searhlist
                    lvSearch.setAdapter(arrayAdapter);

                    Log.i(TAG, "Ingredients: " + results.toString()); //logs onSuccess and shows what is in results
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