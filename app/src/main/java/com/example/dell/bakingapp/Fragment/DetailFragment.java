package com.example.dell.bakingapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.bakingapp.Adapter.StepsAdapter;
import com.example.dell.bakingapp.DetailActivity;
import com.example.dell.bakingapp.IdlingResource.SimpleIdlingResource;
import com.example.dell.bakingapp.Model.Fields;
import com.example.dell.bakingapp.Model.Ingredients;
import com.example.dell.bakingapp.Model.Steps;
import com.example.dell.bakingapp.Networking.ClientService;
import com.example.dell.bakingapp.R;
import com.example.dell.bakingapp.databinding.ActivityDetailBinding;

import java.util.ArrayList;


public class DetailFragment extends Fragment implements StepsAdapter.clickOnListener {

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
    private RecyclerView recyclerView;
    private int index;

    private static final String LOG_TAG = DetailFragment.class.getSimpleName();
    public static final String test = "test";


    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(LOG_TAG,"onCreate");

        if(getArguments() != null){
            Log.e(LOG_TAG, "arguments;");
            fieldsArrayList = getArguments().getParcelableArrayList("arrayList");
            index = getArguments().getInt("index", -1);
            Log.e(LOG_TAG, String.valueOf(index));


        }

        context = getActivity();

        idlingResource = new SimpleIdlingResource();

        layoutManager = new LinearLayoutManager(context);


        stepsArrayList = (ArrayList<Steps>) fieldsArrayList.get(index).getSteps();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Log.e(LOG_TAG, "onCreateView");

        recyclerView = rootView.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(context);

        adapter = new StepsAdapter(context, stepsArrayList, this);
        recyclerView.setAdapter(adapter);
        Log.e(LOG_TAG, String.valueOf(stepsArrayList));


        return rootView;
    }


    @Override
    public void onItemClicked(int itemIndex) {

    }
}
