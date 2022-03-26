package com.example.mixer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.mixer.adapters.DrinkAdapter;
import com.example.mixer.fragments.FavoritesFragment;
import com.example.mixer.fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;
    private ImageButton btnProfile;
    private Button btnLogout;
    private int LogOutVisibility = 0;
    public static final String TAG = "MainActivity";    // Create a tag for logging this activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();   // Hide Action bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        btnProfile = findViewById(R.id.ibLogOut);
        btnLogout = findViewById(R.id.btnLogout);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.action_favorites:
                    default:
                        // TODO: build Favorites Fragment
                        fragment = new FavoritesFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_home);

        // Toggle visibility of btnLogout
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnLogout.getVisibility() == View.INVISIBLE) {
                    btnLogout.setVisibility(View.VISIBLE);
                }
                else if (btnLogout.getVisibility() == View.VISIBLE) {
                    btnLogout.setVisibility(View.INVISIBLE);
                }

            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Clicked Logout button");
//                Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                ParseUser.logOut();
                goToLogIn();            }
        });
    }
    private void goToLogIn() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        this.getSupportFragmentManager().popBackStack();
    }
}