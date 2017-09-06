package anonestep.com.backingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import anonestep.com.backingapp.DbHelper.DbContract;
import anonestep.com.backingapp.Model.Ingredients;
import anonestep.com.backingapp.WidgetServices.WidgetServices;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, int widgetCount) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredents_app_widget);
        Cursor mCursor = context.getContentResolver().query(DbContract.RecipeTable.CONTENT_URI,
                null, null,
                null,
                DbContract.RecipeTable._ID + " DESC ");
        // Construct the RemoteViews object
        if (mCursor.getCount() > 0) {
            if (mCursor.moveToPosition(widgetCount)) {
                views.setTextViewText(R.id.recipe_name, mCursor.getString(mCursor.getColumnIndex
                        (DbContract.RecipeTable.RECIPE_NAME)));
                int id = mCursor.getInt(mCursor.getColumnIndex(DbContract.RecipeTable._ID));
                Intent intent = new Intent(context, WidgetServices.class);
                intent.putExtra(DbContract.Ingredients.Ingredients_Recipe_id, id);
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
                views.setRemoteAdapter(R.id.ingredients_list, intent);
            }
        } else {
            views.setTextViewText(R.id.recipe_name, "Go to Recipe and Click on the Widget button To Show the Ingredients");
        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        int i = 0;
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, i);
            i++;
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

