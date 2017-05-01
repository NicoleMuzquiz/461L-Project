package com.example.swolemates;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.login.FacebookLoginActivity;
import com.example.messaging.MessageActivity;
import com.example.messaging.SelectGM_Activity;
import com.example.settings.DefaultSettingsActivity;
import com.google.firebase.auth.FirebaseAuth;

public class UserHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);

        /*ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000")));*/


        /* Button for moving to select sport page */
        Button selectSportButton = (Button) findViewById(R.id.sportbutton);
        selectSportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), SelectSport.class);
                startActivity(intent);
            }
        });

        /* Button for moving to view connections page */
        Button matchButton = (Button) findViewById(R.id.matchbutton);
        matchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), SelectGM_Activity.class);
                startActivity(intent);
            }
        });

        /* button for moving to settings page */
        Button settingsButton = (Button) findViewById(R.id.settingsbutton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), DefaultSettingsActivity.class);
                startActivity(intent);
            }
        });

        /* button for moving to logout page */
        Button logoutButton = (Button) findViewById(R.id.logoutbutton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), FacebookLoginActivity.class);
                startActivity(intent);
            }
        });

        /* button for moving to message */
        Button messageButton = (Button) findViewById(R.id.messagebutton);
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
                startActivity(intent);
            }
        });
    }
}
