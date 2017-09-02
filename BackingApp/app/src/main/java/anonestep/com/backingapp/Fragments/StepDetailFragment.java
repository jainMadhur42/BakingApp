package anonestep.com.backingapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import anonestep.com.backingapp.Interface.OnCloseButtonClickListener;
import anonestep.com.backingapp.Model.Steps;
import anonestep.com.backingapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class StepDetailFragment extends Fragment implements OnCloseButtonClickListener {


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
    private static final String STEP_LIST = "STEP_LIST";
    private static final String POSITION = "POSITION";


    public StepDetailFragment() {
        // Required empty public constructor
    }

    public static StepDetailFragment newInstance(ArrayList<Steps> stepsArrayList, int position) {
        StepDetailFragment fragment = new StepDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(STEP_LIST, stepsArrayList);
        bundle.putInt(POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            stepsArrayList = getArguments().getParcelableArrayList(STEP_LIST);
            position = getArguments().getInt(POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step_detail, container, false);
        ButterKnife.bind(this, view);
        initializePlayer(stepsArrayList.get(position));
        return view;
    }

    @OnClick(R.id.previous)
    public void setPrevious() {
        if (position == 0) {
            position = stepsArrayList.size() - 1;
        } else
            position--;
        releasePlayer();
        initializePlayer(stepsArrayList.get(position));
    }

    @OnClick(R.id.next)
    public void setNext() {
        if (position < stepsArrayList.size() - 1)
            position++;
        else
            position = 0;
        releasePlayer();
        initializePlayer(stepsArrayList.get(position));
    }

    private void initializePlayer(Steps steps) {
        String url = steps.getVideoURL();
        if (url != null) {
            mStepDescription.setText(steps.getDescription());
            exoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()), new DefaultTrackSelector(), new DefaultLoadControl());
            playerView.setPlayer(exoPlayer);
            exoPlayer.setPlayWhenReady(true);
            Uri uri = Uri.parse(url);
            MediaSource mediaSource = buildMediaSource(uri);
            exoPlayer.prepare(mediaSource);
        } else {
            Toast.makeText(getContext(), "No Video Found", Toast.LENGTH_SHORT).show();
        }
    }

    public MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri, new DefaultHttpDataSourceFactory("ua"), new
                DefaultExtractorsFactory(), null, null);
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
            Toast.makeText(getContext(), "PLayer Released", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        releasePlayer();
    }

    @Override
    public void onCLoseButtonCLicked() {
        releasePlayer();
    }
}
