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

import anonestep.com.backingapp.Model.Steps;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StepsDetail extends AppCompatActivity {

    @BindView(R.id.toolBar)
     Toolbar toolbar;
    @BindView(R.id.step_description)
    TextView mStepDescription;
    @BindView(R.id.player_view)
    SimpleExoPlayerView playerView;
    @BindView(R.id.previous)
    Button previous;
    @BindView(R.id.next)
    Button next;
    private ArrayList<Steps> stepsArrayList;
    private int position;
    SimpleExoPlayer exoPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_detail);
        ButterKnife.bind(this);
        stepsArrayList = getIntent().getParcelableArrayListExtra(getString(R.string.steps));
        position = getIntent().getIntExtra("POSITION", 0);
        initializePlayer(stepsArrayList.get(position));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Step "+position+1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (exoPlayer != null)
            releasePlayer();
    }


    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    private void initializePlayer(Steps steps) {
        String url = steps.getVideoURL();
        if (url != null) {
            mStepDescription.setText(steps.getDescription());
            exoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getBaseContext()), new DefaultTrackSelector(), new DefaultLoadControl());
            playerView.setPlayer(exoPlayer);
            exoPlayer.setPlayWhenReady(true);
            Uri uri = Uri.parse(url);
            MediaSource mediaSource = buildMediaSource(uri);
            exoPlayer.prepare(mediaSource);
        } else {
            Toast.makeText(getBaseContext(), "No Video Found", Toast.LENGTH_SHORT).show();
        }
    }

    public MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri, new DefaultHttpDataSourceFactory("ua"), new
                DefaultExtractorsFactory(), null, null);
    }

    @OnClick(R.id.previous)
    public void setPrevious() {
        if (position == 0) {
            position = stepsArrayList.size() - 1;
        } else
            position--;
        releasePlayer();
        getSupportActionBar().setTitle("Step "+position+1);
        initializePlayer(stepsArrayList.get(position));
    }

    @OnClick(R.id.next)
    public void setNext() {
        if (position < stepsArrayList.size() - 1)
            position++;
        else
            position = 0;
        releasePlayer();
        getSupportActionBar().setTitle("Step "+position);
        initializePlayer(stepsArrayList.get(position));
    }


}
