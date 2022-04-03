package com.example.mixer;

import android.util.Log;

import com.like.LikeButton;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Parcel // To make Drink object parceable
// Encapsulates what makes up a Drink in the app
public class Drink {
    int drinkID;
    String drinkName;
    String drinkIBA;
    String drinkCategory;
    String posterPath;
    String drinkInstructions;
    List <String> drinkIngredients;
    Boolean isFav;


    // Strings to help parse ingredients
    String ingredient = "strIngredient";
    String measure = "strMeasure";
    int LEN_INGREDIENTS =15;

    // empty constructor needed by Parcelor Library
    public Drink(){}

    public Drink(JSONObject jsonObject) throws JSONException {
        drinkID = jsonObject.getInt("idDrink");
        drinkName = jsonObject.getString("strDrink");
        drinkIBA = jsonObject.getString("strAlcoholic");
        drinkCategory = jsonObject.getString("strCategory");
        drinkInstructions = jsonObject.getString("strInstructions");
        posterPath = jsonObject.getString("strDrinkThumb");
        drinkIngredients = new ArrayList<>();


        // Extracts ingredients and measurements and stores it in the list
        for (int i=1; i < LEN_INGREDIENTS; i++) {
            String ingredientKey = ingredient + i;
            String measurementKey = measure + i;

            String nullIngredient = jsonObject.getString(ingredientKey);
            String nullMeasurement = jsonObject.getString(measurementKey);
            nullIngredient = nullIngredient.replaceAll("\\s+","");
            nullMeasurement = nullMeasurement.replaceAll("\\s", "");


            // Parse ingredient and measurement from JSON
            if (nullIngredient == "null" || nullIngredient.isEmpty()){
            }
            else {
                if (nullMeasurement == "null"){
                    String tempIngredient = jsonObject.getString(ingredientKey);
                    drinkIngredients.add(tempIngredient);
                }
                else{
                    String tempIngredient = jsonObject.getString(ingredientKey) + ": " + jsonObject.getString(measurementKey);
                    drinkIngredients.add(tempIngredient);
                }
            }
        }
        Log.d("Ingredients", String.valueOf(drinkIngredients));
    }

    public static Drink fromJsonArray(JSONArray drinkJsonArray) throws JSONException {
        return new Drink(drinkJsonArray.getJSONObject(0));
    }

    // getters to extract posterPath, title, and overview from Movie objects
    // setters to set drinkID, setPosterPath, setDrinkName, setDrinkIBA, setDrinkCategory, setDrinkInstructions, setIngredients
    public int getDrinkID() { return drinkID; }
    public void setDrinkID(int drinkID) { this.drinkID = drinkID; }

    public String getPosterPath() { return String.format(posterPath); }
    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }

    public String getDrinkName() {
        return drinkName;
    }
    public void setDrinkName(String drinkName) { this.drinkName = drinkName; }

    public String getDrinkIBA() {
        return drinkIBA;
    }
    public void setDrinkIBA(String drinkIBA) { this.drinkIBA = drinkIBA; }

    public String getDrinkCategory() {
        return drinkCategory;
    }
    public void setDrinkCategory(String drinkCategory) { this.drinkCategory = drinkCategory; }

    public String getDrinkInstructions() {
        return drinkInstructions;
    }
    public void setDrinkInstructions(String drinkInstructions) { this.drinkInstructions = drinkInstructions; }

    public List<String> getDrinkIngredients() {
        return drinkIngredients;
    }
    public void setDrinkIngredients(List<String> drinkIngredients) { this.drinkIngredients = drinkIngredients; }

    public Boolean getIsFav(){return isFav;}
    public void setIsFav(boolean isFav){ this.isFav = isFav;}


}
