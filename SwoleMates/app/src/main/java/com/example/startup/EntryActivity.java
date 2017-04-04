package com.example.startup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.login.FacebookLoginActivity;
import com.example.swolemates.HomePage;
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
            Intent intent = new Intent(this, HomePage.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, FacebookLoginActivity.class);
            startActivity(intent);
        }

        finish();
    }
}
