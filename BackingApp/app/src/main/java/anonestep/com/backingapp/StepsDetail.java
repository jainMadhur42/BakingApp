package anonestep.com.backingapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import anonestep.com.backingapp.Model.Steps;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsDetail extends AppCompatActivity {

    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.step_description)
    TextView mStepDescription;
    @BindView(R.id.player_view)
    SimpleExoPlayerView playerView;
    SimpleExoPlayer exoPlayer;
    private String TAG = StepsDetail.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_detail);
        ButterKnife.bind(this);
        Steps steps = getIntent().getParcelableExtra("STEPS");
        String url = steps.getVideoURL();
        Log.d(TAG, url);
        initializePlayer(url);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(steps.getShortDescription());

        mStepDescription.setText(steps.getDescription());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(exoPlayer!=null)
        exoPlayer.release();
    }

    private void initializePlayer(String url) {
        if (!url.isEmpty()) {
            exoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getBaseContext
                    ()), new DefaultTrackSelector(), new DefaultLoadControl());
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


}
