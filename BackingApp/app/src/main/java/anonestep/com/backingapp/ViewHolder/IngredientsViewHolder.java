package anonestep.com.backingapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import anonestep.com.backingapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Madhur Jain on 7/14/2017.
 */

public class IngredientsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.ingredients_name)
    public TextView ingredientsName;
    @BindView(R.id.ingredients_quantity)
    public TextView ingredientsQuantity;

    public IngredientsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
