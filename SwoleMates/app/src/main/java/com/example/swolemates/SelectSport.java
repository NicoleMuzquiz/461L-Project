package com.example.swolemates;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class SelectSport extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    public final static String EXTRA_MESSAGE = "sport";
    private String sport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setContentView(R.layout.activity_select_sport);

        Spinner spinner = (Spinner) findViewById(R.id.sports_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sports_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(this);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // Example of a call to a native method
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        Button nextButton = (Button) findViewById(R.id.nextbutton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), EnterSportAbility.class);
                startActivity(intent);
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        sport = (String) parent.getItemAtPosition(pos);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString("user_sport", sport);

//        String APP_PREFERENCES = "sport";
//        SharedPreferences settings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
//        SharedPreferences.Editor prefEditor = settings.edit();
//        prefEditor.putString("UserName", "John Doe");
//        prefEditor.putInt("UserAge", 22);
//        prefEditor.commit();

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public Context getActivity() {
        return this;
    }
}
