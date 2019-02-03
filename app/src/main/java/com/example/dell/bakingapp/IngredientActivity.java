package com.example.dell.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.example.dell.bakingapp.Adapter.IngredientsAdapter;
import com.example.dell.bakingapp.Model.Fields;
import com.example.dell.bakingapp.Model.Ingredients;
import com.example.dell.bakingapp.Widget.AppWidget;
import com.example.dell.bakingapp.databinding.ActivityIngredientBinding;

import java.util.ArrayList;

public class IngredientActivity extends AppCompatActivity {

    private IngredientsAdapter adapter;
    public static ArrayList<Fields> fieldsArrayList;
    private int index;
    public  static ArrayList<Ingredients> ingredientsArrayList;
    private LinearLayoutManager layoutManager;
    private Context context;
    private int appWidgetId;

    private static final String LOG_TAG = IngredientActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityIngredientBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_ingredient);
        context = this;
        layoutManager = new LinearLayoutManager(this);

        Intent intent = getIntent();
        fieldsArrayList = intent.getParcelableArrayListExtra("arrayList");
        index = intent.getIntExtra("index", -1);

        ingredientsArrayList = (ArrayList<Ingredients>) fieldsArrayList.get(index).getIngredients();

        binding.nameView.setText(fieldsArrayList.get(index).getName());
        adapter = new IngredientsAdapter(this, ingredientsArrayList);

        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);


        }



    public static ArrayList<Fields> getIngredientsArrayList(){
        Log.e(LOG_TAG, "53435");
        Log.e(LOG_TAG, String.valueOf(ingredientsArrayList));

        return fieldsArrayList;
    }



}
