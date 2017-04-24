package com.example.backendLoc;



/**
 * Using GeoFire addon of Firebase to find users within a 20km radius of each user
 */
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FindFriends {
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User_Location");
    GeoFire geoFire = new GeoFire(ref);
    
}
