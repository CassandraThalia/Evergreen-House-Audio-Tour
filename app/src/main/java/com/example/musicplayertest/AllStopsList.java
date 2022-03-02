package com.example.musicplayertest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class AllStopsList extends AppCompatActivity implements recyclerAdapter.OnNoteListener {

    private ArrayList<ListItem> listItemList;
    private RecyclerView recyclerView;
    final String TAG = "ERROR";
    private final int numOfStops = 11;
    //private ArrayList<String> nameList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_stops_list);
        recyclerView = findViewById(R.id.recycler);
        listItemList = new ArrayList<>();

        setStopInfo();
        setAdapter();

    }

    private void setAdapter() {
        recyclerAdapter adapter = new recyclerAdapter(listItemList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setStopInfo() {

        //getStopName();

        for (int i = 0; i < numOfStops; i++){
            //int imgId = this.getResources().getIdentifier("stop_" + i, "drawable", this.getPackageName());
            int titleImgId = this.getResources().getIdentifier("title_stop_" + i, "drawable", this.getPackageName());
            listItemList.add(new ListItem(titleImgId));
        }
    }


    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this, MediaPlayer.class);
        Bundle extras = new Bundle();

        for (int i = 0; i < numOfStops; i++){
            int id = this.getResources().getIdentifier("stop_" + i, "raw", this.getPackageName());
            int imgId = this.getResources().getIdentifier("stop_" + i, "drawable", this.getPackageName());
            int titleImgId = this.getResources().getIdentifier("title_stop_" + i, "drawable", this.getPackageName());
            if (position == i) {
                extras.putInt("media", id);
                extras.putInt("imgId", imgId);
                extras.putInt("titleImgId", titleImgId);
                extras.putInt("stopNum", i);
            }
        }
        intent.putExtras(extras);
        startActivity(intent);
    }

//    public void getStopName(){
//        String str = null;
//        BufferedReader br = null;
//        try {
//            InputStream is = getResources().openRawResource(R.raw.audio_tour);
//            br = new BufferedReader(new InputStreamReader(is));
//            while ((str = br.readLine()) != null) {
//                nameList.add(str);
//            }
//        }
//        catch(IOException e) {
//            Log.e(TAG, "File Access Error");
//        }
//        catch (Exception e){
//            Log.e(TAG, "General Error");
//        }
//    }
}

