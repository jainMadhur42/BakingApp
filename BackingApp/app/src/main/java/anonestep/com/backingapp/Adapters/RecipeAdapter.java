package anonestep.com.backingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

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
    private Context context;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipeArrayList, RecipeClickListener recipeClickListener) {
        this.recipeArrayList = recipeArrayList;
        this.recipeClickListener = recipeClickListener;
        this.context = context;
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
        String imagePath = recipeArrayList.get(position).getImage();
        if (imagePath.equals(""))
            Picasso.with(context).load(R.drawable.cheese_cake).into(holder.recipeImage);
        else
            Picasso.with(context).load(imagePath).into(holder.recipeImage);

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
