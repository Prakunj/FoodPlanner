package com.example.dell.bakingapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dell.bakingapp.Model.Fields;
import com.example.dell.bakingapp.Model.Steps;
import com.example.dell.bakingapp.databinding.ActivityVideoBinding;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

public class VideoActivity extends AppCompatActivity {

    private static final String LOG_TAG = VideoActivity.class.getSimpleName();


    private SimpleExoPlayer simpleExoPlayer;
    private ArrayList<Steps> stepsArrayList = new ArrayList<>();
    private String description, videoUrl;
    private int index;
    private ActivityVideoBinding binding;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video);


        Intent intent = getIntent();
        stepsArrayList = intent.getParcelableArrayListExtra("arrayList");
        index = intent.getIntExtra("index", -1);

        description = stepsArrayList.get(index).getDescription();

        binding.descView.setText(description);
        videoUrl = (stepsArrayList.get(index).getVideoUrl() == null) ? "null" : stepsArrayList.get(index).getVideoUrl();

        if (index == stepsArrayList.size() - 1) {
            binding.next.setVisibility(View.INVISIBLE);
        }
        if (index == 0) {
            binding.previous.setVisibility(View.INVISIBLE);

        }

        if (videoUrl.isEmpty()) {
            binding.playerView.setVisibility(View.GONE);
        } else {
            binding.playerView.setVisibility(View.VISIBLE);
            initializePlayer(Uri.parse(videoUrl));
        }
    }

    private void initializePlayer(Uri videoUrl) {


        if (simpleExoPlayer == null) {
            Log.e(LOG_TAG, "player");
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            binding.playerView.setPlayer(simpleExoPlayer);

            String userAgent = Util.getUserAgent(this, "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(videoUrl, new DefaultDataSourceFactory(
                    this, userAgent), new DefaultExtractorsFactory(), null, null);
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }

    public void next(View view) {

        releasePlayer();
        if (index < stepsArrayList.size() - 1) {
            binding.next.setVisibility(View.VISIBLE);
            binding.previous.setVisibility(View.VISIBLE);

            int index2 = ++index;

            videoUrl = (stepsArrayList.get(index2).getVideoUrl() == null) ? "null" : stepsArrayList.get(index2).getVideoUrl();
            description = stepsArrayList.get(index2).getDescription();
            binding.descView.setText(description);

            if (!videoUrl.isEmpty()) {
                binding.playerView.setVisibility(View.VISIBLE);
                initializePlayer(Uri.parse(videoUrl));
            }else{
                binding.playerView.setVisibility(View.GONE);
            }
        }
        if (index == stepsArrayList.size() - 1) {
            binding.next.setVisibility(View.INVISIBLE);


        }
    }


    public void previous(View view) {

        releasePlayer();
        if (index > 0) {
            binding.previous.setVisibility(View.VISIBLE);
            binding.next.setVisibility(View.VISIBLE);
            int index2 = --index;

            videoUrl = (stepsArrayList.get(index2).getVideoUrl() == null) ? "null" : stepsArrayList.get(index2).getVideoUrl();
            description = stepsArrayList.get(index2).getDescription();
            binding.descView.setText(description);

            if(!videoUrl.isEmpty()){
                binding.playerView.setVisibility(View.VISIBLE);
                initializePlayer(Uri.parse(videoUrl));
            }else {
                binding.playerView.setVisibility(View.GONE);
            }

            {
                if (index == 0) {
                    binding.previous.setVisibility(View.INVISIBLE);
                }
            }
        }

    }

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        releasePlayer();
    }
}
