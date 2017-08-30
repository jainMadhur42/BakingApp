package anonestep.com.backingapp.Listener;

import java.util.ArrayList;
import java.util.List;

import anonestep.com.backingapp.Model.Steps;

/**
 * Created by Madhur Jain on 7/15/2017.
 */

public interface StepsClickListener {
    void stepClickListener(ArrayList<Steps> steps, int position);
}
