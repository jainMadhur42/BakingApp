package anonestep.com.backingapp.Fragments;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import anonestep.com.backingapp.Adapters.IngredientsAdapter;
import anonestep.com.backingapp.Adapters.StepsAdapter;
import anonestep.com.backingapp.Interface.OnCloseButtonClickListener;
import anonestep.com.backingapp.Listener.StepsClickListener;
import anonestep.com.backingapp.Model.Ingredients;
import anonestep.com.backingapp.Model.Recipe;
import anonestep.com.backingapp.Model.Steps;
import anonestep.com.backingapp.R;
import anonestep.com.backingapp.StepsDetail;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RecipeDetailFragment extends Fragment implements StepsClickListener {

    @BindView(R.id.ingredients_recycler_view)
    RecyclerView ingredientsRecyclerView;
    @BindView((R.id.steps_recycler_view))
    RecyclerView stepsRecyclerView;
    @BindView(R.id.parent_layout)
    LinearLayout mParentLayout;
    @BindView(R.id.ingredients_list)
    LinearLayout mIngredientsList;
    @BindView(R.id.step_list)
    LinearLayout mStepList;
    @BindView(R.id.step_detail_layout)
    LinearLayout mStepDetailLayout;
    @BindView(R.id.close_step_detail)
    ImageView mCloseStepDetail;
    private static boolean isLayoutPresent = false;
    static ArrayList<Ingredients> ingredientsList;
    static ArrayList<Steps> stepList;
    private static final String RECIPE = "RECIPE";
    private StepDetailFragment mStepDetailFragment;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }


    public static RecipeDetailFragment newInstance(Recipe recipe) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("RECIPE", recipe);
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
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, view);
        StepsAdapter stepsAdapter = new StepsAdapter(getContext(), stepList, this);
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        stepsRecyclerView.setAdapter(stepsAdapter);

        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(ingredientsList);

        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);
        return view;
    }


    @Override
    public void stepClickListener(ArrayList<Steps> steps, int position) {
        boolean isTablet = getContext().getResources().getBoolean(R.bool.tablet);
        Toast.makeText(getContext(), position + " ", Toast.LENGTH_LONG).show();
        if (isTablet == true) {
            if (isLayoutPresent == false) {
                LayoutTransition transition = new LayoutTransition();
                mParentLayout.setLayoutTransition(transition);
                mParentLayout.removeView(mIngredientsList);
                mStepList.setLayoutTransition(transition);
                mStepDetailLayout.setVisibility(View.VISIBLE);
                mStepDetailFragment = StepDetailFragment.newInstance(steps, position);
                getFragmentManager().beginTransaction()
                        .replace(R.id.step_detail_fragment, mStepDetailFragment, "TAG")
                        .commit();
                isLayoutPresent = true;
            } else {
                mStepDetailFragment = StepDetailFragment.newInstance(steps, position);
                getFragmentManager().beginTransaction()
                        .replace(R.id.step_detail_fragment, mStepDetailFragment)
                        .commit();
            }
        } else {
            Intent intent = new Intent(getContext(), StepsDetail.class);
            intent.putParcelableArrayListExtra(getString(R.string.steps), steps);
            intent.putExtra("POSITION", position);
            startActivity(intent);
        }

    }

    @OnClick(R.id.close_step_detail)
    public void onClick() {
        LayoutTransition transition = new LayoutTransition();
        mStepList.setLayoutTransition(transition);
        if (getFragmentManager() != null) {
            Toast.makeText(getContext(), "NUll", Toast.LENGTH_LONG).show();
            getFragmentManager().beginTransaction().remove(mStepDetailFragment).commit();
        }
        mStepDetailLayout.setVisibility(View.INVISIBLE);
        mParentLayout.setLayoutTransition(transition);
        mParentLayout.addView(mIngredientsList, 0);
        if (mStepDetailFragment != null) {
            mStepDetailFragment.onCLoseButtonCLicked();
        }
        isLayoutPresent = false;
    }
}
