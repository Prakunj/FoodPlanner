package com.example.dell.bakingapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.bakingapp.DetailActivity;
import com.example.dell.bakingapp.MainActivity;
import com.example.dell.bakingapp.Model.Fields;
import com.example.dell.bakingapp.Networking.FieldsResponse;
import com.example.dell.bakingapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FieldsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String LOG_TAG = FieldsAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<Fields> fieldsArrayList;
    private clickOnListener clickOnListener;

    public FieldsAdapter(Context context, ArrayList<Fields> fieldsArrayList, clickOnListener clickOnListener){
        this.context = context;
        this.fieldsArrayList = fieldsArrayList;
        this.clickOnListener = clickOnListener;
    }

    public interface clickOnListener{
        void onItemClick(int itemIndex);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
            return new MainViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        final Fields result = fieldsArrayList.get(i);
        if(result != null) {
            ((MainViewHolder) viewHolder).name.setText(result.getName());
        }
    }

    @Override
    public int getItemCount() {
        return fieldsArrayList.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.textView) TextView name;




        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            clickOnListener.onItemClick(clickedPosition);
        }
    }

}
