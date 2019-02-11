package com.example.dell.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.test.espresso.IdlingResource;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.dell.bakingapp.Adapter.StepsAdapter;
import com.example.dell.bakingapp.IdlingResource.SimpleIdlingResource;
import com.example.dell.bakingapp.Model.Fields;
import com.example.dell.bakingapp.Model.Ingredients;
import com.example.dell.bakingapp.Model.Steps;
import com.example.dell.bakingapp.Networking.ClientService;
import com.example.dell.bakingapp.Widget.AppWidget;
import com.example.dell.bakingapp.Widget.AppWidgetService;
import com.example.dell.bakingapp.databinding.ActivityDetailBinding;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements StepsAdapter.clickOnListener {

    private StepsAdapter adapter;
    private LinearLayoutManager layoutManager;
    private Context context;
    private ArrayList<Steps> stepsArrayList = new ArrayList<>();
    private ArrayList<Fields> fieldsArrayList;
    private ClientService retrofitClientService;
    private int id;
    private ActivityDetailBinding binding;
    private ArrayList<Ingredients> ingredientsArrayList;
    private SimpleIdlingResource idlingResource;
    private ActionBar actionBar;

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    public static final String test = "test";




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                binding.drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

         idlingResource = new SimpleIdlingResource();

        layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);
        setSupportActionBar(binding.toolbar);
        context = getApplicationContext();

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        fieldsArrayList = new ArrayList<>();

        Intent intent = getIntent();
        fieldsArrayList = intent.getParcelableArrayListExtra("arrayList");
        final int index = intent.getIntExtra("index", -1);

        stepsArrayList = (ArrayList<Steps>) fieldsArrayList.get(index).getSteps();


        adapter = new StepsAdapter(this, stepsArrayList, this);
        binding.recyclerView.setAdapter(adapter);

        actionBar.setTitle(fieldsArrayList.get(index).getName());

        binding.ingredientsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, IngredientActivity.class);
                intent.putParcelableArrayListExtra("arrayList", fieldsArrayList);
                intent.putExtra("index", index);
                startActivity(intent);
            }
        });




        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                String title = (String) menuItem.getTitle();
                Log.e(LOG_TAG, String.valueOf(title));

                switch (title){
                    case "Nutella Pie":
                        id = 0;
                        break;
                    case "Brownies":
                        id = 1;
                        break;
                     case "Yellow Cake":
                         id = 2;
                         break;
                    case "Cheese Cake":
                        id = 3;

                }

                stepsArrayList = (ArrayList<Steps>) fieldsArrayList.get(id).getSteps();
                Log.e(LOG_TAG, String.valueOf(id));
                adapter.setData(stepsArrayList);
                binding.recyclerView.setAdapter(adapter);
                actionBar.setTitle(title);


                binding.drawerLayout.closeDrawers();

                return true;
            }
        });




    }
    public IdlingResource getIdlingResource(){
        return idlingResource;
    }


    @Override
    public void onItemClicked(int itemIndex) {
        Log.e(LOG_TAG, "here");
        Intent intent = new Intent(this, VideoActivity.class);
        intent.putExtra("index", itemIndex);
        Log.e(LOG_TAG, String.valueOf(itemIndex));
        intent.putParcelableArrayListExtra("arrayList", stepsArrayList);
        Log.e(LOG_TAG, String.valueOf(stepsArrayList));


        startActivity(intent);

    }
}
