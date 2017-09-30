package anonestep.com.backingapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import anonestep.com.backingapp.Adapters.IngredientsAdapter;
import anonestep.com.backingapp.Adapters.StepsAdapter;
import anonestep.com.backingapp.Listener.StepsClickListener;
import anonestep.com.backingapp.Model.Ingredients;
import anonestep.com.backingapp.Model.Recipe;
import anonestep.com.backingapp.Model.Steps;
import anonestep.com.backingapp.R;
import anonestep.com.backingapp.StepsDetail;
import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeDetailFragment extends Fragment implements StepsClickListener {

    @BindView(R.id.ingredients_recycler_view)
    RecyclerView ingredientsRecyclerView;
    @BindView((R.id.steps_recycler_view))
    RecyclerView stepsRecyclerView;
    @BindView(R.id.close_step_detail)
    ImageView mCloseStepDetail;
    private static final String CURRENT_STEP_POSITION = "CURRENT_STEP_POSITION";
    private static int currentStepPosition;
    private static final String CURRENT_INGREDIENT_POSITION = "CURRENT_RECIPE_POSITION";
    private int currentIngredientsPosition;
    private LinearLayoutManager stepsLayoutManager, ingredientsLayoutManager;
    static ArrayList<Ingredients> ingredientsList;
    static ArrayList<Steps> stepList;
    private static final String RECIPE = "RECIPE";
    private static final String TAG = RecipeDetailFragment.class.getSimpleName();

    public RecipeDetailFragment() {
        // Required empty public constructor
    }


    public static RecipeDetailFragment newInstance(Recipe recipe) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(RECIPE, recipe);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            currentIngredientsPosition = savedInstanceState.getInt(CURRENT_INGREDIENT_POSITION);
            currentStepPosition = savedInstanceState.getInt(CURRENT_STEP_POSITION);
            Log.d(TAG, currentIngredientsPosition + " " + currentStepPosition);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Recipe recipe = getArguments().getParcelable(RECIPE);
            ingredientsList = (ArrayList<Ingredients>) recipe.getIngredients();
            stepList = (ArrayList<Steps>) recipe.getSteps();
            currentIngredientsPosition = getArguments().getInt(CURRENT_INGREDIENT_POSITION);
            currentStepPosition = getArguments().getInt(CURRENT_STEP_POSITION);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, view);
        StepsAdapter stepsAdapter = new StepsAdapter(getContext(), stepList, this);
        stepsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        stepsRecyclerView.setLayoutManager(stepsLayoutManager);
        stepsRecyclerView.setAdapter(stepsAdapter);
        stepsRecyclerView.smoothScrollToPosition(currentStepPosition);
        if (savedInstanceState != null){
            currentIngredientsPosition = savedInstanceState.getInt(CURRENT_INGREDIENT_POSITION);
            currentStepPosition = savedInstanceState.getInt(CURRENT_STEP_POSITION);
            Log.d(TAG, currentIngredientsPosition + " " + currentStepPosition + " CREATE_VIEW");
        }
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(ingredientsList);
        ingredientsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        ingredientsRecyclerView.setLayoutManager(ingredientsLayoutManager);
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);
        ingredientsRecyclerView.smoothScrollToPosition(currentIngredientsPosition);
        return view;
    }


    @Override
    public void stepClickListener(ArrayList<Steps> steps, int position) {
        Intent intent = new Intent(getContext(), StepsDetail.class);
        intent.putParcelableArrayListExtra(getString(R.string.steps), steps);
        intent.putExtra("POSITION", position);
        startActivity(intent);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_STEP_POSITION, stepsLayoutManager.findLastVisibleItemPosition());
        outState.putInt(CURRENT_INGREDIENT_POSITION, ingredientsLayoutManager.findLastVisibleItemPosition());
        Log.d(TAG, currentIngredientsPosition + " " + currentStepPosition + " ON_SAVE");
    }


}
