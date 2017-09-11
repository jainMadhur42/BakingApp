package anonestep.com.backingapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.ArrayList;

import anonestep.com.backingapp.Fragments.RecipeDetailFragment;
import anonestep.com.backingapp.Fragments.StepDetailFragment;
import anonestep.com.backingapp.Model.Steps;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StepsDetail extends AppCompatActivity {

    @BindView(R.id.toolBar)
    Toolbar toolbar;
    private ArrayList<Steps> stepsArrayList;
    private int position;
    private static String STEP_LIST = "STEP_LIST";
    private static String POSITION = "POSITION";
    private static final String TAG = StepDetailFragment.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_detail);
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            stepsArrayList = savedInstanceState.getParcelableArrayList(STEP_LIST);
            position = savedInstanceState.getInt(POSITION, 0);
            Log.d(TAG, "Details");
        } else {
            stepsArrayList = getIntent().getParcelableArrayListExtra(getString(R.string.steps));
            position = getIntent().getIntExtra("POSITION", 0);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.step_detail_fragment, StepDetailFragment.newInstance(stepsArrayList, position)).commit();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Step ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STEP_LIST, stepsArrayList);
        outState.putInt(POSITION, StepDetailFragment.position);
    }


}
