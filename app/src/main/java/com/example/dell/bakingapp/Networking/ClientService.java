package com.example.dell.bakingapp.Networking;

import android.util.Log;

import com.example.dell.bakingapp.Model.Fields;
import com.example.dell.bakingapp.Model.Steps;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ClientService {



    @GET("baking.json")
    Call<List<Fields>>getName();

    @GET("baking.json")
    Call<List<Steps>>getSteps();



}
