package anonestep.com.backingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
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
import anonestep.com.backingapp.Listener.StepsClickListener;
import anonestep.com.backingapp.Model.Ingredients;
import anonestep.com.backingapp.Model.Recipe;
import anonestep.com.backingapp.Model.Steps;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeDetail extends AppCompatActivity  {

    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.add_to_widget)
    FloatingActionButton mAddToWidget;
    private static final String INGREDIENT_LIST = "INGREDIENT_LIST";
    ArrayList<Ingredients> ingredientsList;
    private static final String STEP_LIST = "STEP_LIST";
    ArrayList<Steps> stepList;
    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);
        recipe = getIntent().getParcelableExtra(getString(R.string.recipe));
        ingredientsList = (ArrayList) recipe.getIngredients();
        stepList = (ArrayList) recipe.getSteps();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(recipe.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.recipe_detail_container,RecipeDetailFragment.newInstance(recipe))
                .commit();

    }


/*    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STEP_LIST, stepList);
        outState.putParcelableArrayList(INGREDIENT_LIST, ingredientsList);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            stepList = savedInstanceState.getParcelableArrayList(STEP_LIST);
            ingredientsList = savedInstanceState.getParcelableArrayList(INGREDIENT_LIST);
        }

    }*/

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
