package anonestep.com.backingapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import anonestep.com.backingapp.Adapters.StepsAdapter;
import anonestep.com.backingapp.Listener.NavigationBarClickListener;
import anonestep.com.backingapp.Listener.StepsClickListener;
import anonestep.com.backingapp.Model.Ingredients;
import anonestep.com.backingapp.Model.Recipe;
import anonestep.com.backingapp.Model.Steps;
import anonestep.com.backingapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeDetailFragmentTablet extends Fragment implements StepsClickListener, NavigationBarClickListener {


    @BindView((R.id.steps_recycler_view))
    RecyclerView stepsRecyclerView;
    @BindView(R.id.ingredients_button)
    Button mIngredientButton;

    private static final String RECIPE = "RECIPE";
    static ArrayList<Ingredients> ingredientsList;
    static ArrayList<Steps> stepList;
    private LinearLayoutManager stepsLayoutManager;
    private static final String CURRENT_STEP_POSITION = "CURRENT_STEP_POSITION";
    private static final String CURRENT_VIDEO_POSITION = "CURRENT_VIDEO_POSITION";
    private static final String VIDEO_VISIBLE = "VIDEO_VISIBLE";
    private static int currentStepPosition = 0;
    Fragment stepDetailFragment;
    private static int position;
    private static boolean isVideoVisible = false;
    private static final String TAG = RecipeDetailFragmentTablet.class.getSimpleName();
    Recipe recipe;


    public RecipeDetailFragmentTablet() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RecipeDetailFragmentTablet newInstance(Recipe recipe) {
        RecipeDetailFragmentTablet fragment = new RecipeDetailFragmentTablet();
        Bundle bundle = new Bundle();
        bundle.putParcelable("RECIPE", recipe);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            currentStepPosition = savedInstanceState.getInt(CURRENT_STEP_POSITION);
            position = savedInstanceState.getInt(CURRENT_VIDEO_POSITION);
            isVideoVisible = savedInstanceState.getBoolean(VIDEO_VISIBLE);
            /*if (isVideoVisible == true)
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.step_detail_fragment, StepDetailFragment.newInstance(stepList, position))
                        .commit();
            else
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.step_detail_fragment, IngredientFragment.newInstance(recipe))
                        .commit();*/
            stepDetailFragment = getFragmentManager().getFragment(savedInstanceState, TAG);
        }
    }


    @OnClick(R.id.ingredients_button)
    public void onClick() {
        getFragmentManager().beginTransaction().replace(R.id.step_detail_fragment, IngredientFragment.newInstance(recipe)).commit();
        isVideoVisible = false;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipe = getArguments().getParcelable(RECIPE);
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
        stepsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        stepsRecyclerView.setLayoutManager(stepsLayoutManager);
        stepsRecyclerView.setAdapter(stepsAdapter);
        stepsRecyclerView.smoothScrollToPosition(position);
        if (isVideoVisible == false)
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.step_detail_fragment, IngredientFragment.newInstance(recipe))
                    .commit();
        return view;
    }

    @Override
    public void stepClickListener(ArrayList<Steps> steps, int position) {
        this.position = position;
        isVideoVisible = true;
        stepsRecyclerView.smoothScrollToPosition(position);
        stepDetailFragment = StepDetailFragment.newInstance(stepList, position);
        //stepDetailFragment.setNavigationBarClickListener(this);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.step_detail_fragment, stepDetailFragment)
                .commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_STEP_POSITION, stepsLayoutManager.findFirstVisibleItemPosition());
        outState.putInt(CURRENT_VIDEO_POSITION, position);
        outState.putBoolean(VIDEO_VISIBLE, isVideoVisible);
        if(stepDetailFragment!=null)
        getFragmentManager().putFragment(outState, TAG, stepDetailFragment);
    }


    @Override
    public void setClickedVideoPosition(int position) {
        this.position = position;
        stepsRecyclerView.smoothScrollToPosition(position);
        Toast.makeText(getContext(), position + " ", Toast.LENGTH_LONG).show();
    }
}
