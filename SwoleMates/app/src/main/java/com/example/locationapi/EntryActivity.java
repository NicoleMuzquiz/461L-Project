package com.example.locationapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.swolemates.DefaultSettingsActivity;
import com.example.swolemates.FacebookLoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EntryActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private FirebaseUser user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FacebookLoginActivity.getmAuth();

        user = mAuth.getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(this, DefaultSettingsActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, FacebookLoginActivity.class);
            startActivity(intent);
        }

        finish();
    }
}
