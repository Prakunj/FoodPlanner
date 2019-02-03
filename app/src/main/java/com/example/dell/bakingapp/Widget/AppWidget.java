package com.example.dell.bakingapp.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.dell.bakingapp.IngredientActivity;
import com.example.dell.bakingapp.MainActivity;
import com.example.dell.bakingapp.Model.Fields;
import com.example.dell.bakingapp.Model.Ingredients;
import com.example.dell.bakingapp.Preferences;
import com.example.dell.bakingapp.R;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {

    private static final String LOG_TAG = AppWidget.class.getSimpleName();


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Log.e(LOG_TAG, "updateAppWidget");



        ArrayList<Fields> fields = Preferences.loadRecipe(context);
        Log.e(LOG_TAG, String.valueOf(fields));
        if(fields != null) {

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);

            Intent intent1 = new Intent(context, AppWidgetService.class);
            intent1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent1.putParcelableArrayListExtra("arrayList", fields);
            intent1.setData(Uri.parse(intent1.toUri(Intent.URI_INTENT_SCHEME)));

            views.setRemoteAdapter(R.id.tvList, intent1);
            views.setEmptyView(R.id.tvList, R.id.emptyView);
            Log.e(LOG_TAG, "updateAppWidget2");



            appWidgetManager.updateAppWidget(appWidgetId, views);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.tvList);

        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.e(LOG_TAG, "onUpdate");
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.e(LOG_TAG, "updateAppWidgets");
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

