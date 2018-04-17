package com.example.oryossipof.alphahotal;

import java.io.Serializable;



public class Trip implements Serializable {

    public int tripId;
    public String tripName ;
    public String tripImg ;

    public Trip(int tripId, String tripName, String tripImg) {
        this.tripId = tripId;
        this.tripName = tripName;
        this.tripImg = tripImg;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getTripImg() {
        return tripImg;
    }

    public void setTripImg(String tripImg) {
        this.tripImg = tripImg;
    }
}
