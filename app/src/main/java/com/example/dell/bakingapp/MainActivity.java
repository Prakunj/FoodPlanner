package com.example.dell.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.dell.bakingapp.Adapter.FieldsAdapter;
import com.example.dell.bakingapp.Model.Fields;
import com.example.dell.bakingapp.Model.Ingredients;
import com.example.dell.bakingapp.Model.Steps;
import com.example.dell.bakingapp.Networking.ClientService;
import com.example.dell.bakingapp.Networking.RetrofitClient;
import com.example.dell.bakingapp.databinding.ActivityMainBinding;

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

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        layoutManager = new LinearLayoutManager(this);

        binding.recyclerView.setLayoutManager(layoutManager);

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

                    adapter = new FieldsAdapter(context, fields, MainActivity.this);
                    binding.recyclerView.setAdapter(adapter);
                    binding.recyclerView.setLayoutManager(layoutManager);


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


    }


    @Override
    public void onItemClick(int itemIndex) {

        Intent intent = new Intent(this, DetailActivity.class);
        Log.e(LOG_TAG, "onClick");
        intent.putExtra("index", itemIndex);
        intent.putParcelableArrayListExtra("arrayList", fields);
        Log.e(LOG_TAG, String.valueOf(itemIndex));
        Log.e(LOG_TAG, String.valueOf(fields));
        startActivity(intent);
    }
}
