package com.example.dell.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.dell.bakingapp.Adapter.FieldsAdapter;
import com.example.dell.bakingapp.Fragment.DetailFragment;
import com.example.dell.bakingapp.Model.Fields;
import com.example.dell.bakingapp.Model.Ingredients;
import com.example.dell.bakingapp.Model.Steps;
import com.example.dell.bakingapp.Networking.ClientService;
import com.example.dell.bakingapp.Networking.RetrofitClient;
import com.example.dell.bakingapp.databinding.ActivityMainBinding;
import com.example.dell.bakingapp.IdlingResource.SimpleIdlingResource;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements FieldsAdapter.clickOnListener {

    private FieldsAdapter adapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<Fields> fields;
    private RetrofitClient retrofitClient;
    private ClientService clientServiceInterface;
    private Context context;
    private ArrayList<Steps> steps = new ArrayList<>();
    @Nullable
    private SimpleIdlingResource mIdlingResource;
    public static final String test1 = "test";
    private boolean mTwoPane;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private DetailFragment fragment;

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        layoutManager = new LinearLayoutManager(this);
        recyclerView2 = findViewById(R.id.master_recycler_view);
        fragment = new DetailFragment();



        getIdlingResource();
        recyclerView = findViewById(R.id.recyclerView);

        context = this;
        fields = new ArrayList<>();


        clientServiceInterface = RetrofitClient.getApiClient().create(ClientService.class);

        Call<List<Fields>> call = clientServiceInterface.getName();


        call.enqueue(new Callback<List<Fields>>() {
            @Override
            public void onResponse(Call<List<Fields>> call, Response<List<Fields>> response) {
                if(response.isSuccessful()) {

                    fields = (ArrayList<Fields>) response.body();

                    steps = (ArrayList<Steps>) fields.get(0).getSteps();

                    if(!mTwoPane) {
                        adapter = new FieldsAdapter(context, fields, MainActivity.this);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(layoutManager);
                    }else{
                        adapter = new FieldsAdapter(context, fields, MainActivity.this);

                        recyclerView2.setLayoutManager(layoutManager);
                        recyclerView2.setAdapter(adapter);
                    }


                }else{
                    Log.e(LOG_TAG, "failed");
                }
            }

            @Override
            public void onFailure(Call<List<Fields>> call, Throwable t) {

                Log.e(LOG_TAG, "onFailure");
                Log.e(LOG_TAG, t.getMessage());

            }
        });

        LinearLayout linearLayout = findViewById(R.id.android_linear_layout);

        if(linearLayout != null){
            mTwoPane = true;

        }else {
            mTwoPane = false;
        }

    }


    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    public void onItemClick(int itemIndex) {

        if(mTwoPane){
            Log.e(LOG_TAG, "mTwoPane");
            Bundle arguments = new Bundle();
            arguments.putInt("index", itemIndex);
            arguments.putParcelableArrayList("arrayList", fields);
            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

        }else {

            Intent intent = new Intent(context, DetailActivity.class);
            Log.e(LOG_TAG, "onClick");
            intent.putExtra("index", itemIndex);
            intent.putParcelableArrayListExtra("arrayList", fields);
            Log.e(LOG_TAG, String.valueOf(itemIndex));
            Log.e(LOG_TAG, String.valueOf(fields));
            startActivity(intent);
        }
    }
}
