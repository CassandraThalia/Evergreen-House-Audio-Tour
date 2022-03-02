package com.example.musicplayertest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageButton next, all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        next = findViewById(R.id.nextRoom);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MediaPlayer.class);

                Bundle extras = new Bundle();
                extras.putInt("media", R.raw.stop_0);
                extras.putInt("imgId", R.drawable.stop_0);
                extras.putInt("titleImgId", R.drawable.title_stop_0);
                intent.putExtras(extras);

                startActivity(intent);
            }
        });

        all = findViewById(R.id.allRooms);

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AllStopsList.class));
            }
        });
    }
}