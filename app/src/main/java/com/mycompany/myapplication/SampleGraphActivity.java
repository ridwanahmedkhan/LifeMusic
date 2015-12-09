package com.mycompany.myapplication;

import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;
import android.widget.MediaController;

public class SampleGraphActivity extends AppCompatActivity {
    private Button show_Sample;
    private Button skip_Sample;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_sample_graph);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*
       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        */
    }

    public void showSample(View v)
    {
        show_Sample = (Button) findViewById(R.id.show_sample);
        skip_Sample = (Button) findViewById(R.id.skip_sample);
        videoView = (VideoView) findViewById(R.id.videoView);
//Use a media controller so that you can scroll the video contents
//and also to pause, start the video.
        videoView.setVisibility(View.VISIBLE);
        show_Sample.setVisibility(View.INVISIBLE);
        skip_Sample.setVisibility(View.INVISIBLE);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse("android.resource://com.mycompany.myapplication/" + R.raw.sample));
        videoView.start();
    }

    public void skipSample(View v)
    {
        Intent intent = new Intent(this, GraphActivity.class);
        startActivity(intent);
    }

}
