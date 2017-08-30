package anonestep.com.backingapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import anonestep.com.backingapp.Listener.StepsClickListener;
import anonestep.com.backingapp.Model.Steps;
import anonestep.com.backingapp.R;
import anonestep.com.backingapp.ViewHolder.StepsViewHolder;

/**
 * Created by Madhur Jain on 7/15/2017.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsViewHolder> {

    ArrayList<Steps> stepList;
    StepsClickListener stepsClickListener;

    public StepsAdapter(List<Steps> stepsList, StepsClickListener stepsClickListener) {
        this.stepList = (ArrayList)stepsList;
        this.stepsClickListener = stepsClickListener;
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.steps_card, parent,
                false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, final int position) {
        holder.stepsCount.setText("Step " + (position + 1));
        holder.shortDescription.setText(stepList.get(position).getShortDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepsClickListener.stepClickListener(stepList,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (stepList != null) {
            return stepList.size();
        }
        return 0;
    }
}
