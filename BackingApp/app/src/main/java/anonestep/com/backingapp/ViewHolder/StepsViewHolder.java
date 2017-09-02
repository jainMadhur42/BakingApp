package anonestep.com.backingapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import anonestep.com.backingapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Madhur Jain on 7/15/2017.
 */

public class StepsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.step_count)
    public TextView stepsCount;
    @BindView(R.id.short_description)
    public TextView shortDescription;
    @BindView(R.id.steps_image)
    public ImageView stepImage;

    public StepsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
