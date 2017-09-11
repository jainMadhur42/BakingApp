package anonestep.com.backingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
    Context context;
    String TAG = StepsAdapter.class.getSimpleName();


    public StepsAdapter(Context context, List<Steps> stepsList, StepsClickListener stepsClickListener) {
        this.stepList = (ArrayList) stepsList;
        this.stepsClickListener = stepsClickListener;
        this.context = context;
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.steps_card, parent,
                false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, final int position) {
        holder.stepsCount.setText("Step " + (position));
        holder.shortDescription.setText(stepList.get(position).getShortDescription());

        if (!stepList.get(position).getThumbnailURL().equals("")) {
            Picasso.with(context).load(stepList.get(position).getThumbnailURL()).fit().into(holder.stepImage);
            Log.d(TAG, stepList.get(position).getThumbnailURL());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepsClickListener.stepClickListener(stepList, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stepList == null ? 0 : stepList.size();
    }
}
