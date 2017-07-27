package anonestep.com.backingapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import anonestep.com.backingapp.Listener.RecipeClickListener;
import anonestep.com.backingapp.Model.Recipe;
import anonestep.com.backingapp.R;
import anonestep.com.backingapp.ViewHolder.RecipeViewHolder;

/**
 * Created by Madhur Jain on 7/10/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private ArrayList<Recipe> recipeArrayList;
    private RecipeClickListener recipeClickListener;
    int[] imageArray = {R.drawable.nutella_pie, R.drawable.brownies, R.drawable.yellow_cake, R
            .drawable.cheese_cake};

    public RecipeAdapter(ArrayList<Recipe> recipeArrayList, RecipeClickListener recipeClickListener) {
        this.recipeArrayList = recipeArrayList;
        this.recipeClickListener = recipeClickListener;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card, parent,
                false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, final int position) {
        holder.mRecipeName.setText(recipeArrayList.get(position).getName());
        holder.recipeImage.setImageResource(imageArray[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recipeClickListener != null) {
                    recipeClickListener.onClick(recipeArrayList.get(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (recipeArrayList == null)
            return 0;
        return recipeArrayList.size();
    }


    public void swapRecipeList(ArrayList<Recipe> newRecipeList) {
        this.recipeArrayList = newRecipeList;
        notifyDataSetChanged();
    }


}
