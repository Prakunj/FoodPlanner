package com.example.dell.bakingapp.Networking;

import com.example.dell.bakingapp.Model.Fields;
import com.example.dell.bakingapp.Model.Steps;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FieldsResponse {

    @SerializedName("steps")
    private List<Steps> stepsList;

    public List<Steps> getStepsList() {
        return stepsList;
    }


    @Override
    public String toString() {
        return "MovieResponse{" +
                "movieList=" + stepsList + "\n" +
                '}';
    }


}
