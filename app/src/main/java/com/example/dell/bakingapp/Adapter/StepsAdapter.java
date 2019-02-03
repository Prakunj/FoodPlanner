package com.example.dell.bakingapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.bakingapp.Model.Fields;
import com.example.dell.bakingapp.Model.Steps;
import com.example.dell.bakingapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Steps> stepsArrayList;
    private static final String LOG_TAG = StepsAdapter.class.getSimpleName();
    private clickOnListener clickOnListener;

    public interface clickOnListener{
        void onItemClicked(int itemIndex);
    }


    public StepsAdapter(Context context, ArrayList<Steps> stepsArrayList, clickOnListener clickOnListener){
        this.context = context;
        this.stepsArrayList = stepsArrayList;
        this.clickOnListener = clickOnListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item2, viewGroup, false);
        return new MainViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final Steps result = stepsArrayList.get(i);
        ((MainViewHolder) viewHolder).descView.setText(result.getShortDescription());
        Log.e(LOG_TAG, String.valueOf(result));


    }

    public class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.descView)
        TextView descView;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            clickOnListener.onItemClicked(position);
        }
    }

    public void setData(ArrayList<Steps> arrayList){
        this.stepsArrayList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return stepsArrayList.size();
    }
}
