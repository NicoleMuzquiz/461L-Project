package com.example.location;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * User class specifically used for location, stores data into firebase database
 */


public class LocUser {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("server/saving-data/fireblog");

    private String id;
    private String name;
    private double latitude;
    private double longitude;

    DatabaseReference usersRef = ref.child("LocUsers");
    Map<String, LocUser> users = new HashMap<String, LocUser>();


    public LocUser(String id, String name, double latitude, double longitude){
        this.id = id;
        this.name=name;
        this.latitude= latitude;
        this.longitude=longitude;

        //place LocUser into database
        users.put(name,this);
        usersRef.setValue(this);
    }

    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(){
        latitude = Coordinates.Latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(){
        longitude = Coordinates.Longitude;
    }



}
