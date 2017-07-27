package anonestep.com.backingapp.WidgetServices;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import java.util.ArrayList;

import anonestep.com.backingapp.DbHelper.DbContract;
import anonestep.com.backingapp.Model.Ingredients;
import anonestep.com.backingapp.R;

/**
 * Created by Madhur Jain on 7/21/2017.
 */

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private int appWidgetId;
    ArrayList<Ingredients> ingredientsList;

    public ListProvider(Context context, Intent intent) {
        this.context = context;
        this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        int id = intent.getIntExtra(DbContract.Ingredients.Ingredients_Recipe_id, 0);
        Log.d(ListProvider.class.getSimpleName(), id + " ");
        populateListItem(id);

    }


    private void populateListItem(int id) {
        Cursor ingredientCursor = context.getContentResolver().query(DbContract
                        .Ingredients.CONTENT_URI,
                null,
                DbContract.Ingredients.Ingredients_Recipe_id + "=?",
                new String[]{id + " "}, null);
        ingredientsList = new ArrayList<>();
        while (ingredientCursor.moveToNext()) {
            Ingredients ingredients = new Ingredients();
            ingredients.setIngredient(ingredientCursor.getString(ingredientCursor
                    .getColumnIndex(DbContract.Ingredients.Ingredients_Name)));
            ingredients.setMeasure(ingredientCursor.getString(ingredientCursor
                    .getColumnIndex(DbContract.Ingredients.Ingredients_measure)));
            ingredients.setQuantity(ingredientCursor.getFloat(ingredientCursor.
                    getColumnIndex(DbContract.Ingredients.Ingredients_quantity)));
            ingredientsList.add(ingredients);
        }
        ingredientCursor.close();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(ingredientsList!=null)
        return ingredientsList.size();
        else
            return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = getLoadingView();
        remoteViews.setTextViewText(R.id.ingredients_name,ingredientsList.get(position).getIngredient());
        remoteViews.setTextViewText(R.id.ingredients_quantity,ingredientsList.get(position)
                .getQuantity()+" "+ingredientsList.get(position).getMeasure());
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return new RemoteViews(context.getPackageName(), R.layout.ingredents_widget_layout);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
