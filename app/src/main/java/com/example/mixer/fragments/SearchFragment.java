package com.example.mixer.fragments;

import android.app.Activity;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.mixer.DetailActivity;
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
public class SearchFragment extends HomeFragment {
    public static final String INGREDIENTS_URL = "https://www.thecocktaildb.com/api/json/v1/1/list.php?i=list";
    public static final String DRINK_URL = "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=";
    public static final String INGREDIENT_DRINKS_URL = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?i=";
    public static final String TAG = "SearchFragment";    // Create a tag for logging this activity

    @Override
    public void onStart() {
        super.onStart();
        if(DetailActivity.getKey() == 3){
            DetailActivity.setKey(0);
            getDrinkAdapter().notifyDataSetChanged();
            searchView.clearFocus();
        }
    }


    ListView lvSearch;
    RecyclerView rvDrinks;
    List<String> ingredients;
    TextView tvSearchResults;
    public static String selectedIngredient;
    ArrayAdapter<String> arrayAdapter;
    SearchView searchView;

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
        rvDrinks = view.findViewById(R.id.rvDrinks);
        tvSearchResults = view.findViewById(R.id.tvSearchResults);
        swipeContainer = view.findViewById(R.id.swipeContainer);

        // create arrayAdapter for lvSearch
        arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.search_list_layout, ingredients);
        queryForIngredients(arrayAdapter, lvSearch);

        // Create searchview and filter
        searchView = view.findViewById(R.id.search_bar);

        // search view for search list
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryIngredient) {
                lvSearch.setVisibility(View.GONE);
                tvSearchResults.setText("Search results for " + queryIngredient);
                tvSearchResults.setVisibility(View.VISIBLE);
                drinks.clear();
                queryIDs(queryIngredient);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        // search view for search list
        querySearchView(searchView);


        // Set the adapter to the recycler view
        rvDrinks.setAdapter(

                getDrinkAdapter());

        // Set a layout manager
        rvDrinks.setLayoutManager(new

                LinearLayoutManager(getContext()));

        // Set onItemClickListener for items in listview
        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick (AdapterView < ? > adapterView, View view,int i, long l){
                searchView.clearFocus();
                selectedIngredient = (lvSearch.getItemAtPosition(i).toString());
                tvSearchResults.setText("Search results for " + selectedIngredient);
                tvSearchResults.setVisibility(View.VISIBLE);
                lvSearch.setVisibility(View.GONE);
                queryIDs(selectedIngredient);
            }
        });

        // Takes focus from searchView when list view or recycler view is touched
        clearSearchView(searchView);

        // Refresh screen set to false when you swipe up
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(false);
            }
        });
    }


    private void clearSearchView(SearchView searchView) {
        // clears searchView when the list view of top ingredients is touched
        lvSearch.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                searchView.clearFocus();
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
            }
        });

        // clears searchView when the recycler view of top ingredients is touched
        rvDrinks.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                searchView.clearFocus();
            }
        });
    }

    private void querySearchView(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryIngredient) {
                drinks.clear();
                lvSearch.setVisibility(View.GONE);
                queryIDs(queryIngredient);
                tvSearchResults.setText("Search results for " + queryIngredient);
                tvSearchResults.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                tvSearchResults.setVisibility(View.GONE);
                lvSearch.setVisibility(View.VISIBLE);
                arrayAdapter.getFilter().filter(query);
                return false;
            }
        });
    }



    // Get drink IDs of selectedIngredient
    private void queryIDs(String selectedIngredient) {
        AsyncHttpClient client = new AsyncHttpClient();
        String tempURL = INGREDIENT_DRINKS_URL + selectedIngredient;

        // Make a get request on the client object
        client.get(tempURL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                //Log.d(TAG, "Success"); // log for success, connected to TAG
                JSONObject jsonObject = json.jsonObject; // we store the response jsonObject in the variable jsonObject
                try {
                    JSONArray results = jsonObject.getJSONArray("drinks"); // we store the results array into new variable results. This results array is extracted from the jsonObject by using the getJSONArray method
                    drinks.clear(); // Clear drinks before entering for loop

                    // for loop populates search RV
                    for (int i=0; i < results.length(); i++) {
                        String tempResult = (results.getJSONObject(i)).getString("idDrink");
                        //Log.d(TAG, selectedIngredient + " drinkID: " + tempResult);

                        // Query drink by ID and populate RV
                        queryDrinks(getDrinkAdapter(), tempResult);
                    }

                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e); // handles the exception if results is not in the jsonObject
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "Failure: " + response);
                Toast.makeText(getContext(), "Couldn't find " + selectedIngredient, Toast.LENGTH_LONG).show();
            }
        });
    }

    // Query drinks into recyclerView
    @Override
    protected void queryDrinks(DrinkAdapter drinkAdapter, String drinkID) {

        AsyncHttpClient client = new AsyncHttpClient();
        String tempURL = DRINK_URL + drinkID;

        // Make a get request on the client object
        client.get(tempURL , new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                //Log.d(TAG, "Success"); // log for success, connected to TAG
                JSONObject jsonObject = json.jsonObject; // we store the response jsonObject in the variable jsonObject
                try {
                    JSONArray result = jsonObject.getJSONArray("drinks");
                    //Log.i(TAG, "Results: " + result.toString()); //logs onSuccess and shows what is in results
                    drinks.add(Drink.fromJsonArray(result));
                    drinkAdapter.notifyDataSetChanged();
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

    // queryForIngredients populates searchlist with drink ingredients
    private void queryForIngredients(ArrayAdapter<String> arrayAdapter, ListView lvSearch) {
        // Instantiate an AsyncHttpClient to execute the API request
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(INGREDIENTS_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                //Log.d(TAG, "Success"); // log for success, connected to TAG
                JSONObject jsonObject = json.jsonObject; // we store the response jsonObject in the variable jsonObject
                try {
                    JSONArray results = jsonObject.getJSONArray("drinks"); // we store the results array into new variable results. This results array is extracted from the jsonObject by using the getJSONArray method
                    for (int i=0; i < results.length(); i++) {
                        String tempResult = (results.getJSONObject(i)).getString("strIngredient1");
                        ingredients.add(tempResult);
                        //Log.d(TAG, "Ingredient: " + tempResult);
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