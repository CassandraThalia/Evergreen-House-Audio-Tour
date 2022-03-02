package com.example.musicplayertest;


import java.net.URI;

public class ListItem {
    //private int image, stopName;

    private int stopName;

    public ListItem(int stopName){
        this.setStopName(stopName);
        //this.setImage(image);
    }

//    public int getImage() { return image; }
//    public void setImage(int image) { this.image = image;  }

    public int getStopName() { return stopName; }
    public void setStopName(int stopName) {
        this.stopName = stopName;
    }

}
