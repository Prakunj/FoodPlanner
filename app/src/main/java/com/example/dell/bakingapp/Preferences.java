package com.example.dell.bakingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.dell.bakingapp.Model.Fields;
import com.fasterxml.jackson.databind.ser.std.ObjectArraySerializer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Preferences {

    private static final String LOF_TAG = "Preference";

    public static final String PREFS_NAME = "prefs";

    public static void saveRecipe(Context context, ArrayList<Fields> fields) {
        Log.e(LOF_TAG, "Recipe");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(fields);
        editor.putString(PREFS_NAME, json);

        editor.apply();

    }

    public static ArrayList<Fields> loadRecipe(Context context) {
        Log.e(LOF_TAG, "loadRecipe");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        Gson gson = new Gson();
        String json = prefs.getString(PREFS_NAME, null);

        Type type = new TypeToken<ArrayList<Fields>>(){}.getType();
        ArrayList<Fields> fieldsArrayList = gson.fromJson(json, type);
        return fieldsArrayList;

    }


}
