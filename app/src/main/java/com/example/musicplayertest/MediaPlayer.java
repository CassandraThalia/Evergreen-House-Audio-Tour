package com.example.musicplayertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;

public class MediaPlayer extends AppCompatActivity{

    ImageButton play, pause, next, all;;
    android.media.MediaPlayer mediaPlayer;
    SeekBar seekBar;
    Handler handler;
    ImageView stopImgFrame, stopTitleFrame;
    int currentStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_player);

        //Set next and all stops buttons
        next = findViewById(R.id.nextRoom1);
        all = findViewById(R.id.allRooms1);

        //Set Handler (needed for media player functionality below)
        handler = new Handler();

        //Get info from bundle
        Bundle extras = getIntent().getExtras();
        currentStop = extras.getInt("stopNum");
        //Set media player info from bundle
        int media = extras.getInt("media");
        mediaPlayer = android.media.MediaPlayer.create(getApplicationContext(), media);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //Set images from bundle
        stopImgFrame = findViewById(R.id.stop_img);
        int imgId = extras.getInt("imgId");
        stopImgFrame.setImageResource(imgId);
        stopTitleFrame = findViewById(R.id.stop_title);
        int titleImgId = extras.getInt("titleImgId");
        stopTitleFrame.setImageResource(titleImgId);

        //Set media player elements
        seekBar = findViewById(R.id.seekbar);
        seekBar.setMax(mediaPlayer.getDuration());
        play = findViewById(R.id.play_button1);
        pause = findViewById(R.id.pause_button1);

        //All Stops button functionality
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    startActivity(new Intent(MediaPlayer.this, AllStopsList.class));
                }
                catch (Exception e) {
                    System.out.println("Error loading all stops list: " + e);
                }
            }
        });

        //Next button functionality
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Stop and release current audio from media player
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    //Use playNext method to reset media player, image & title
                    playNext();
                    //Increase current stop var
                    currentStop++;

                    //If at stop 10 (last stop, should not be hard coded I know), disable Next button
                    if (currentStop == 10) {
                        next.setEnabled(false);
                    }
                }
                catch (Exception e){
                    System.out.println("Error moving to next stop media: " + e);
                }
            }
        });

        //Media Player Functionality
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudio();
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseAudio();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public class UpdateSeekbar implements Runnable {
        @Override
        public void run() {
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            handler.postDelayed(this, 100);
        }
    }

    private void playAudio() {
        try {
            mediaPlayer.start();
            MediaPlayer.UpdateSeekbar updateSeekbar = new MediaPlayer.UpdateSeekbar();
            handler.post(updateSeekbar);
        }
        catch (Exception e) {
            System.out.println("Error playing media: " + e);
        }
    }

    private void pauseAudio() {
        try {
            mediaPlayer.pause();
        }
        catch (Exception e) {
            System.out.println("Error pausing media: " + e);
        }
    }

    @Override
    protected void onPause() {
        try {
            mediaPlayer.stop();
            super.onPause();
        }
        catch (Exception e) {
            System.out.println("Error stopping media: " + e);
        }
    }

    private void playNext(){
        try {
            //Use currentStop var to reset media player, image and title
            int id = this.getResources().getIdentifier("stop_" + (currentStop + 1), "raw", this.getPackageName());
            mediaPlayer = android.media.MediaPlayer.create(getApplicationContext(), id);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            int imgId = this.getResources().getIdentifier("stop_" + (currentStop + 1), "drawable", this.getPackageName());
            stopImgFrame.setImageResource(imgId);

            int titleImgId = this.getResources().getIdentifier("title_stop_" + (currentStop + 1), "drawable", this.getPackageName());
            stopTitleFrame.setImageResource(titleImgId);
        }
        catch (Exception e) {
            System.out.println("Error playing next media: " + e);
        }
    }
}

