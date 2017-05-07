package com.example.messaging;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adapters.SelectGM_Adapter;
import com.example.swolemates.R;
import com.example.swolemates.SwoleUser;
import com.example.ui.UserBox;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;

public class SelectGM_Activity extends AppCompatActivity {

    private ArrayList<UserBox> userList;
    SelectGM_Adapter dataAdapter = null;

    // Firebase keys commonly used with backend Servlet instances
    private static final String CONN = "connections";
    private static final String MSG = "messages";
    private static final String USR = "users";

    private static final int RC_SIGN_IN = 9001;

    private static String TAG = "GroupTextActivity";
    private static FirebaseLogger fbLog;
    private ListView connectionList;

    private FirebaseUser firebaseUser;
    private DatabaseReference firebase;
    private SwoleUser mySwoleUser;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_gm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.select_gm_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringBuffer responseText = new StringBuffer();
                String message_id = "";
                String userNames = "";
                responseText.append("The following were selected...\n");

                ArrayList<UserBox> matchList = dataAdapter.getMatchList();
                ArrayList<String> matchIdList = new ArrayList<String>();
                ArrayList<String> displayNameList = new ArrayList<String>();

                int count = 0;
                for (int i = 0; i < matchList.size(); i++) {
                    UserBox user = matchList.get(i);
                    if (user.isSelected()) {
                        count++;
                    }
                }

                if (count > 0) {
                    matchIdList.add(firebaseUser.getUid());
                    displayNameList.add(firebaseUser.getDisplayName());
                    for (int i = 0; i < matchList.size(); i++) {
                        String userID = matchList.get(i).getId();
                        String userName = matchList.get(i).getName();
                        matchIdList.add(userID);
                        displayNameList.add(userName);
                    }
                    Collections.sort(matchIdList);
                    Collections.sort(displayNameList);

                    message_id += matchIdList.get(0);
                    userNames += displayNameList.get(0);
                    for (int i = 1; i < matchIdList.size(); i++) {
                        String user = matchIdList.get(i);
                        message_id += "_" + user;
                        userNames += ", " + displayNameList.get(i);
                    }
                    Toast.makeText(getApplicationContext(),
                            responseText, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(view.getContext(), MessageActivity.class);
                    intent.putExtra("key", message_id);
                    intent.putExtra("names", userNames);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "None were selected", Toast.LENGTH_LONG).show();
                }
            }
        });

        firebase = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        email = firebaseUser.getEmail();
        displayListView();

        firebase.child(USR + "/" + firebaseUser.getUid() + "/" + CONN)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String prevKey) {
                        SwoleUser swoleUser = (SwoleUser) dataSnapshot.getValue(SwoleUser.class);
                        if (swoleUser != null && !email.equals(swoleUser.getEmail()))
                            new GetUserImg().execute(swoleUser);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String prevKey) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String prevKey) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void displayListView() {

        //Array list of countries
        userList = new ArrayList<UserBox>();

        //create an ArrayAdaptar from the String Array
        dataAdapter = new SelectGM_Adapter(this, userList);

        connectionList = (ListView) findViewById(R.id.gm_select);
        connectionList.setAdapter(dataAdapter);

    }

    private class GetUserImg extends AsyncTask<SwoleUser, Void, UserBox> {
        //get book thumbnail

        @Override
        protected UserBox doInBackground(SwoleUser... users) {
            //attempt to download image
            UserBox userBox = null;
            try {
                //try to download
                URL imgURL = new URL(users[0].getPhotoUrl());
                URLConnection imgConn = imgURL.openConnection();
                imgConn.connect();
                InputStream imgIn = imgConn.getInputStream();
                BufferedInputStream imgBuff = new BufferedInputStream(imgIn);
                Bitmap img = BitmapFactory.decodeStream(imgBuff);
                userBox = new UserBox(img, users[0], false);
                imgBuff.close();
                imgIn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return userBox;
        }

        protected void onPostExecute(UserBox userBox) {
            userList.add(userBox);
            dataAdapter.notifyDataSetChanged();
        }

    }

}
