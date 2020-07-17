package com.example.justfe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;

public class MainActivity extends AppCompatActivity implements IOptions {

    ImageView btnOptions;

    //    Class Instance
    VideoOptions videoOptions;

    //    Video instance
    PlayerView playerView;
    ProgressBar progressBar;
    ImageView btFullscreen;
    public SimpleExoPlayer simpleExoPlayer;
    boolean flag = false;
    MediaSource mediaSource;
    Uri videoUrl = Uri.parse("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4");
    DataSource.Factory mediaDataSourceFactory;
    HttpDataSource.Factory manifestDataSourceFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Setting for video options button
        videoOptions = new VideoOptions();
        videoOptions.setIOptions(this);

//         Set instance
        btnOptions = findViewById(R.id.iv_options);

//        Event on click

        btnOptions.setOnClickListener((View view) -> {
            runFragment(videoOptions).setCustomAnimations(R.anim.slide_up_enter, R.anim.slide_down_exit)
                    .addToBackStack(null).commit();
        });


//        Set video instance
        manifestDataSourceFactory = buildHttpDataSourceFactory();
        mediaDataSourceFactory = new DefaultDataSourceFactory(MainActivity.this, null, manifestDataSourceFactory);
//        Find button
        playerView = findViewById(R.id.player_view);
        progressBar = findViewById(R.id.progress_bar);
        btFullscreen = playerView.findViewById(R.id.bt_fullscreen);
//         Don't know how to call
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        LoadControl loadControl = new DefaultLoadControl();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
                MainActivity.this, trackSelector, loadControl
        );
        mediaSource = buildMediaSource(videoUrl);
        playerView.setPlayer(simpleExoPlayer);
        playerView.setKeepScreenOn(true);
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(true);
//        Input media source
        mediaSource = buildMediaSource(videoUrl);
        playerView.setPlayer(simpleExoPlayer);
        playerView.setKeepScreenOn(true);
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(true);

//        Run media
        simpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == Player.STATE_BUFFERING) {
                    progressBar.setVisibility(View.VISIBLE);
                } else if (playbackState == Player.STATE_READY) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });
//        Some on click event
        if (btFullscreen != null) {
            btFullscreen.setOnClickListener((View v) -> {
                if (flag) {
                    btFullscreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen));
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    flag = false;
                } else {
                    btFullscreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen_exit));
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    flag = true;
                }
//            videoOptions = new VideoOptions();
//            videoOptions.setFragmentInterface(this);
//            speedOptions = new SpeedOptions();
//            speedOptions.setFragmentInterface(this);
//            btnOptions = findViewById(R.id.iv_test_options);
//            btnOptions.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    runFragment(videoOptions).setCustomAnimations(R.anim.slide_up_enter, R.anim.slide_down_exit)
//                            .addToBackStack(null)
//                            .commit();
//                }
//            });
            });
        }
    }

    //    Some life cycle
    @Override
    protected void onPause() {
        super.onPause();
        simpleExoPlayer.setPlayWhenReady(false);
        simpleExoPlayer.getPlaybackState();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.getPlaybackState();
    }

    //        Class for running fragments
    @Override
    public FragmentTransaction runFragment(Fragment nextClass) {
        return getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_options, nextClass);
    }

    //        Set thing for video input
    MediaSource buildMediaSource(Uri uri) {
        @C.ContentType int type = Util.inferContentType(uri);
        switch (type) {
            case C.TYPE_DASH:
                return new DashMediaSource.Factory(new DefaultDashChunkSource.Factory(mediaDataSourceFactory),
                        manifestDataSourceFactory).createMediaSource(uri);
            case C.TYPE_HLS:
                return new HlsMediaSource.Factory(mediaDataSourceFactory).createMediaSource(uri);
            case C.TYPE_OTHER:
                return new ProgressiveMediaSource.Factory(mediaDataSourceFactory).createMediaSource(uri);
            default:
                throw new IllegalStateException("Unsupported type: " + type);
        }
    }

    public HttpDataSource.Factory buildHttpDataSourceFactory() {
        return new DefaultHttpDataSourceFactory("exoplayer_video", null, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
                DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true);
    }

    //    Testing, must delete or cmt
    Fragment currentFragment;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //and only is the ListFragment shown.
//            if (isListFragmentShown) {
            // create a rect for storing the fragment window rect
            Rect r = new Rect(0, 0, 0, 0);
            // retrieve the fragment's windows rect
            currentFragment.getView().getHitRect(r);
            // check if the event position is inside the window rect
            boolean intersects = r.contains((int) event.getX(), (int) event.getY());
            // if the event is not inside then we can close the fragment
            if (!intersects) {
                Log.d("TAG", "pressed outside the listFragment");
                getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
                // notify that we consumed this event
                return true;
            }
//            }
        }
        return super.onTouchEvent(event);
    }
}