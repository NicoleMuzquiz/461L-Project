package com.example.swolemates;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import com.example.settings.DefaultSettingsActivity;

public class UserHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);

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

        /* Button for moving to view matches page */
        Button matchButton = (Button) findViewById(R.id.matchbutton);
        selectSportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), SelectSport.class);
                startActivity(intent);
            }
        });

        /* button for moving to settings page */
        Button settingsButton = (Button) findViewById(R.id.settingsbutton);
        selectSportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), DefaultSettingsActivity.class);
                startActivity(intent);
            }
        });

        /* button for moving to logout page */
        Button logoutButton = (Button) findViewById(R.id.logoutbutton);
        selectSportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), SelectSport.class);
                startActivity(intent);
            }
        });
    }
}
