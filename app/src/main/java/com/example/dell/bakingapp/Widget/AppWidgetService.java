package com.example.dell.bakingapp.Widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Filter;
import android.widget.RemoteViewsService;

import com.example.dell.bakingapp.Adapter.AppWidgetAdapter;
import com.example.dell.bakingapp.MainActivity;
import com.example.dell.bakingapp.Model.Fields;
import com.example.dell.bakingapp.Preferences;

import java.util.ArrayList;

public class AppWidgetService extends RemoteViewsService {
    private static final String LOG_TAG = AppWidgetService.class.getSimpleName();

    private Context context;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.e(LOG_TAG, "onGetFactory");

        intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        intent.getParcelableArrayListExtra("arrayList");

        return new AppWidgetAdapter(context);
    }

    public static void updateWidget(Context context, ArrayList<Fields> fields) {

        Preferences.saveRecipe(context, fields);
        Log.e(LOG_TAG, "updateWidget5");

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, AppWidget.class));
        AppWidget.updateAppWidgets(context, appWidgetManager, appWidgetIds);

    }



}
