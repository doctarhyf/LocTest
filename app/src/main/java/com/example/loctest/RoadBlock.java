package com.example.loctest;

import androidx.annotation.NonNull;

public class RoadBlock {

    private String key = "no_key";
    private double latitude;
    private double longitude;

    public RoadBlock(){

    }


    public RoadBlock(double latitude, double longitude){
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }

    public RoadBlock(double latitude, double longitude, String key){
        this.setLatitude(latitude);
        this.setLongitude(longitude);
        this.setKey(key);
    }

    @NonNull
    @Override
    public String toString() {
        return "Lat : " + this.getLatitude() + ", Long : " + this.getLongitude() + ", key : " + key;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
