package com.example.dell.bakingapp.Adapter;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.dell.bakingapp.MainActivity;
import com.example.dell.bakingapp.Model.Fields;
import com.example.dell.bakingapp.Model.Ingredients;
import com.example.dell.bakingapp.Preferences;
import com.example.dell.bakingapp.R;

import java.util.ArrayList;

public class AppWidgetAdapter implements RemoteViewsService.RemoteViewsFactory {

    private static final String LOG_TAG = AppWidgetAdapter.class.getSimpleName();


    Context context;
    private ArrayList<Fields> fields;
    private int appWidgetId;

    public AppWidgetAdapter(Context context){
        Log.e(LOG_TAG, "appWidgetAdapter");
        this.context = context;
    }
    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        Log.e(LOG_TAG, "datasetChagged");
        fields = Preferences.loadRecipe(context);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return fields.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        Log.e(LOG_TAG, "getViewAt");

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);
        views.setTextViewText(R.id.textView, fields.get(position).getIngredients().get(position).getIngredient());

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
