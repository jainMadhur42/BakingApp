package anonestep.com.backingapp;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import anonestep.com.backingapp.Adapters.RecipeAdapter;
import anonestep.com.backingapp.Listener.RecipeClickListener;
import anonestep.com.backingapp.Model.Recipe;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecipeClickListener {

    private static final String url = "https://d17h27t6h515a5.cloudfront" +
            ".net/topher/2017/May/59121517_baking/baking.json";
    private String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.no_internet_text)
    TextView mNoInternetText;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    RecyclerView recipeRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        final RecipeAdapter recipeAdapter = new RecipeAdapter(null, this);

        recipeRecycler = (RecyclerView) findViewById(R.id.recipe_list);
        if (recipeRecycler != null) {
            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getBaseContext(),
                    DividerItemDecoration.VERTICAL);
            recipeRecycler.addItemDecoration(itemDecoration);
            recipeRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        } else {
            recipeRecycler = (RecyclerView) findViewById(R.id.recipe_list_sw600dp);
            recipeRecycler.setLayoutManager(new GridLayoutManager(getBaseContext(), 2));

        }
        recipeRecycler.setAdapter(recipeAdapter);

        if (isOnline()) {
            final RequestQueue requestQueue
                    = Volley.newRequestQueue(getBaseContext());
            mProgressBar.setVisibility(View.VISIBLE);
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Type listType = new TypeToken<List<Recipe>>() {
                    }.getType();
                    ArrayList<Recipe> recipeArrayList = new Gson().fromJson(response.toString(),
                            listType);
                    mProgressBar.setVisibility(View.INVISIBLE);
                    recipeAdapter.swapRecipeList(recipeArrayList);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, error.toString());
                }
            });

            requestQueue.add(jsonArrayRequest);
        } else {
            mNoInternetText.setVisibility(View.VISIBLE);
        }

    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService
                (CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public void onClick(Recipe recipe) {
        Intent intent = new Intent(getBaseContext(), RecipeDetail.class);
        intent.putExtra("RECIPE", recipe);
        startActivity(intent);
    }
}
