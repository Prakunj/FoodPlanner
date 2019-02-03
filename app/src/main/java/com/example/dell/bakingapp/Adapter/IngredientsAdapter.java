package com.example.dell.bakingapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.bakingapp.Model.Ingredients;
import com.example.dell.bakingapp.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Ingredients> ingredientsArrayList;


    public IngredientsAdapter(Context context, ArrayList<Ingredients> ingredientsArrayList){

        this.context = context;
        this.ingredientsArrayList = ingredientsArrayList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item3, viewGroup, false);
        return new MainViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Ingredients result = ingredientsArrayList.get(i);
        ((MainViewHolder) viewHolder).quantity.setText(String.valueOf(result.getQuantity()));
        ((MainViewHolder) viewHolder).measure.setText(result.getMeasure());
        ((MainViewHolder) viewHolder).ingredient.setText(result.getIngredient());


    }

    @Override
    public int getItemCount() {
        return ingredientsArrayList.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.quantityView)
        TextView quantity;
        @BindView(R.id.measureView) TextView measure;
        @BindView(R.id.ingredientView) TextView ingredient;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
