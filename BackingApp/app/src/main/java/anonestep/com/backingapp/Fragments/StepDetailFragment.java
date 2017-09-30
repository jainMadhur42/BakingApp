package anonestep.com.backingapp.Fragments;

import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import anonestep.com.backingapp.Listener.NavigationBarClickListener;
import anonestep.com.backingapp.Model.Steps;
import anonestep.com.backingapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class StepDetailFragment extends Fragment {


    @BindView(R.id.step_description)
    TextView mStepDescription;
    @BindView(R.id.player_view)
    SimpleExoPlayerView playerView;
    @BindView(R.id.previous)
    Button previous;
    @BindView(R.id.next)
    Button next;
    private ArrayList<Steps> stepsArrayList;
    public static int position;
    SimpleExoPlayer exoPlayer;
    private static final String STEP_LIST = "STEP_LIST";
    private static final String POSITION = "POSITION";
    private static final String CURRENT_POSITION = "CURRENT_POSITION";
    public long current_position;
    String url;
    private static final String TAG = StepDetailFragment.class.getSimpleName();
    private NavigationBarClickListener mNavigationBarClickListener;

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


    public void setNavigationBarClickListener(NavigationBarClickListener mNavigationBarClickListener) {
        this.mNavigationBarClickListener = mNavigationBarClickListener;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            current_position = savedInstanceState.getLong(CURRENT_POSITION);
            Log.d(TAG, current_position + "onActivity");
        }
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

        if (savedInstanceState != null) {
            current_position = savedInstanceState.getLong(CURRENT_POSITION);
            Log.d(TAG, current_position + "oNCreateView");
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step_detail, container, false);
        ButterKnife.bind(this, view);
        initializePlayer(stepsArrayList.get(position), current_position);
        return view;
    }

    @OnClick(R.id.previous)
    public void setPrevious() {
        if (position == 0) {
            position = stepsArrayList.size() - 1;
        } else
            position--;
        if (mNavigationBarClickListener != null)
            mNavigationBarClickListener.setClickedVideoPosition(position);
        releasePlayer();
        current_position = 0;
        initializePlayer(stepsArrayList.get(position), current_position);
    }

    @OnClick(R.id.next)
    public void setNext() {
        if (position < stepsArrayList.size() - 1)
            position++;
        else
            position = 0;
        if (mNavigationBarClickListener != null)
            mNavigationBarClickListener.setClickedVideoPosition(position);
        current_position = 0;
        releasePlayer();
        initializePlayer(stepsArrayList.get(position), current_position);
    }

    private void initializePlayer(Steps steps, long current_position) {
        url = steps.getVideoURL();
        
        Log.d(TAG, "initializePlayer" + current_position);
        if (url != null) {
            mStepDescription.setText(steps.getDescription());
            exoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()), new DefaultTrackSelector(), new DefaultLoadControl());
            playerView.setPlayer(exoPlayer);
            exoPlayer.setPlayWhenReady(true);
            Uri uri = Uri.parse(url);
            MediaSource mediaSource = buildMediaSource(uri);
            exoPlayer.seekTo(current_position);
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
        if (exoPlayer != null) {
            current_position = exoPlayer.getCurrentPosition();
            Log.d(TAG, current_position + " OnPause");
        }
        releasePlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        releasePlayer();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (exoPlayer != null) {
            current_position = exoPlayer.getCurrentPosition();
            Log.d(TAG, current_position + " OnResume");
        }
        //initializePlayer(stepsArrayList.get(position));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong(CURRENT_POSITION, current_position);
        Log.d(TAG, current_position + "ON_SAVED_STATE");
    }

}
