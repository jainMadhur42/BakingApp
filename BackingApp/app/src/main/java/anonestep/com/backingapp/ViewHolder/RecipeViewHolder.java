package anonestep.com.backingapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import anonestep.com.backingapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Madhur Jain on 7/10/2017.
 */

public class RecipeViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.recipe_name)
    public TextView mRecipeName;
    @BindView(R.id.recipe_image)
    public ImageView recipeImage;


    public RecipeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
