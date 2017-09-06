package anonestep.com.backingapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import anonestep.com.backingapp.Adapters.IngredientsAdapter;
import anonestep.com.backingapp.Model.Ingredients;
import anonestep.com.backingapp.Model.Recipe;
import anonestep.com.backingapp.Model.Steps;
import anonestep.com.backingapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;


public class IngredientFragment extends Fragment {

    private static final String RECIPE = "RECIPE";
    static ArrayList<Ingredients> ingredientsList;
    static ArrayList<Steps> stepList;
    private LinearLayoutManager  ingredientsLayoutManager;
    @BindView(R.id.ingredients_recycler_view)
    RecyclerView ingredientsRecyclerView;


    public IngredientFragment() {
        // Required empty public constructor
    }

    public static IngredientFragment newInstance(Recipe recipe) {
        IngredientFragment fragment = new IngredientFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(RECIPE, recipe);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Recipe recipe = getArguments().getParcelable(RECIPE);
            ingredientsList = (ArrayList<Ingredients>) recipe.getIngredients();
            stepList = (ArrayList<Steps>) recipe.getSteps();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredient, container, false);
        ButterKnife.bind(this, view);
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(ingredientsList);
        ingredientsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        ingredientsRecyclerView.setLayoutManager(ingredientsLayoutManager);
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);
        return view;
    }


}
