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

        next = findViewById(R.id.nextRoom1);
        seekBar = findViewById(R.id.seekbar);

        handler = new Handler();

        Bundle extras = getIntent().getExtras();
        currentStop = extras.getInt("stopNum");

        int media = extras.getInt("media");
        mediaPlayer = android.media.MediaPlayer.create(getApplicationContext(), media);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        stopImgFrame = findViewById(R.id.stop_img);
        int imgId = extras.getInt("imgId");
        stopImgFrame.setImageResource(imgId);

        stopTitleFrame = findViewById(R.id.stop_title);
        int titleImgId = extras.getInt("titleImgId");
        stopTitleFrame.setImageResource(titleImgId);


        seekBar.setMax(mediaPlayer.getDuration());

        play = findViewById(R.id.play_button1);
        pause = findViewById(R.id.pause_button1);

        all = findViewById(R.id.allRooms1);

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MediaPlayer.this, AllStopsList.class));
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                playNext();
                currentStop++;

                if (currentStop == 10) {
                    next.setEnabled(false);
                }
            }
        });

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
        mediaPlayer.start();

        MediaPlayer.UpdateSeekbar updateSeekbar = new MediaPlayer.UpdateSeekbar();

        handler.post(updateSeekbar);
    }

    private void pauseAudio() {
        mediaPlayer.pause();
    }

    @Override
    protected void onPause() {
        mediaPlayer.stop();
        super.onPause();
    }

    private void playNext(){
        int id = this.getResources().getIdentifier("stop_" + (currentStop + 1), "raw", this.getPackageName());
        mediaPlayer = android.media.MediaPlayer.create(getApplicationContext(), id);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        int imgId = this.getResources().getIdentifier("stop_" + (currentStop + 1), "drawable", this.getPackageName());
        stopImgFrame.setImageResource(imgId);

        int titleImgId = this.getResources().getIdentifier("title_stop_" + (currentStop + 1), "drawable", this.getPackageName());
        stopTitleFrame.setImageResource(titleImgId);
    }
}

