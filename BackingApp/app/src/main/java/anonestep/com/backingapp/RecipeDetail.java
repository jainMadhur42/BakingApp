package anonestep.com.backingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import anonestep.com.backingapp.Adapters.IngredientsAdapter;
import anonestep.com.backingapp.Adapters.StepsAdapter;
import anonestep.com.backingapp.DbHelper.DbContract;
import anonestep.com.backingapp.Fragments.RecipeDetailFragment;
import anonestep.com.backingapp.Fragments.RecipeDetailFragmentTablet;
import anonestep.com.backingapp.Listener.StepsClickListener;
import anonestep.com.backingapp.Model.Ingredients;
import anonestep.com.backingapp.Model.Recipe;
import anonestep.com.backingapp.Model.Steps;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeDetail extends AppCompatActivity {

    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.add_to_widget)
    FloatingActionButton mAddToWidget;
    ArrayList<Ingredients> ingredientsList;
    ArrayList<Steps> stepList;
    private static final String TAG = RecipeDetail.class.getSimpleName();
    Recipe recipe;
    Fragment mContent;
    public boolean isRestoredState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(getString(R.string.recipe));
            mContent = getSupportFragmentManager().getFragment(savedInstanceState, TAG);
            isRestoredState = true;
        } else {
            Bundle bundle = getIntent().getExtras();
            recipe = bundle.getParcelable(getString(R.string.recipe));
        }
        /*Not getting Data */

        ingredientsList = (ArrayList) recipe.getIngredients();
        stepList = (ArrayList) recipe.getSteps();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(recipe.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (isRestoredState == false) {
            if (getResources().getBoolean(R.bool.tablet) == false) {
                mContent = RecipeDetailFragment.newInstance(recipe);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.recipe_detail_container, mContent, TAG)
                        .commit();
            } /*else {
                mContent = RecipeDetailFragmentTablet.newInstance(recipe);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.recipe_detail_container, mContent, TAG)
                        .commit();
            }*/
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(getString(R.string.recipe), recipe);
        getSupportFragmentManager().putFragment(outState, TAG, mContent);
    }


    @OnClick(R.id.add_to_widget)
    public void addToWidget() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContract.RecipeTable.RECIPE_NAME, recipe.getName());
        contentValues.put(DbContract.RecipeTable.RECIPE_TIME_STAMP, "time('now')");
        Uri uri = getContentResolver().insert(DbContract.RecipeTable.CONTENT_URI, contentValues);
        if (uri != null) {
            int id = Integer.parseInt(uri.getPathSegments().get(1));
            for (Ingredients ingredients : ingredientsList) {
                contentValues = new ContentValues();
                contentValues.put(DbContract.Ingredients.Ingredients_Recipe_id, id);
                contentValues.put(DbContract.Ingredients.Ingredients_Name, ingredients.getIngredient());
                contentValues.put(DbContract.Ingredients.Ingredients_measure, ingredients.getMeasure());
                contentValues.put(DbContract.Ingredients.Ingredients_quantity, ingredients.getQuantity());
                uri = getContentResolver().insert(DbContract.Ingredients.CONTENT_URI,
                        contentValues);
                if (uri != null) {
                    Toast.makeText(getBaseContext(), "Widget has been Successfully Added", Toast.LENGTH_LONG).show();
                }
            }
            AppWidgetManager appWidgetManager =
                    AppWidgetManager.getInstance(getBaseContext());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName
                    (getBaseContext(),
                            RecipeWidget.class));
            int i = 0;
            for (int appWidgetId : appWidgetIds) {
                RecipeWidget.updateAppWidget(getBaseContext(), appWidgetManager, appWidgetId, i);
                i++;
            }
        }
    }


}
