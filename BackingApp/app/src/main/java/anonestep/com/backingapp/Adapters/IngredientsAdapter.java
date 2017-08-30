package anonestep.com.backingapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import anonestep.com.backingapp.Model.Ingredients;
import anonestep.com.backingapp.R;
import anonestep.com.backingapp.ViewHolder.IngredientsViewHolder;

/**
 * Created by Madhur Jain on 7/14/2017.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsViewHolder> {

    List<Ingredients> ingredientsList;

    public IngredientsAdapter(List<Ingredients> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredents_card, parent,
                false);
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsViewHolder holder, int position) {
        holder.ingredientCount.setText("Ingredient " + (position + 1));
        holder.ingredientsName.setText(ingredientsList.get(position).getIngredient());
        holder.ingredientsQuantity.setText(ingredientsList.get(position).getQuantity() + " " +
                "" + ingredientsList.get(position).getMeasure());
    }

    @Override
    public int getItemCount() {
        if (ingredientsList != null) {
            return ingredientsList.size();
        }
        return 0;
    }
}
