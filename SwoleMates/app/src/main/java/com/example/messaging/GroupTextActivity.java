/**
 * # Copyright Google Inc. 2016
 * # Licensed under the Apache License, Version 2.0 (the "License");
 * # you may not use this file except in compliance with the License.
 * # You may obtain a copy of the License at
 * #
 * # http://www.apache.org/licenses/LICENSE-2.0
 * #
 * # Unless required by applicable law or agreed to in writing, software
 * # distributed under the License is distributed on an "AS IS" BASIS,
 * # WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * # See the License for the specific language governing permissions and
 * # limitations under the License.
 **/

package com.example.messaging;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.login.FacebookLoginActivity;
import com.example.swolemates.HomePage;
import com.example.swolemates.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Main activity to select a channel and exchange groupMessages with other users
 * The app expects users to authenticate with Google ID. It also sends user
 * activity logs to a Servlet instance through Firebase.
 */
public class GroupTextActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {

    private int NUMBER_OF_GROUPS;

    private LinearLayout groupTextView;

    // Firebase keys commonly used with backend Servlet instances
    private static final String MSG = "messages";
    private static final String USR = "users";
    private static final String REQLOG = "requestLogger";

    private static final int RC_SIGN_IN = 9001;

    private static String TAG = "GroupTextActivity";
    private static FirebaseLogger fbLog;

    private FirebaseUser firebaseUser;
    private DatabaseReference firebase;
    private String key, names;
    private String inbox;
    private String currentChannel;
    private List<String> channels;
    private ChildEventListener channelListener;
    private SimpleDateFormat fmt;

    private TextView channelLabel;
    private ListView gm_list;
    private List<Map<String, String>> groupMessages;
    private SimpleAdapter gmAdapter;
    private EditText messageText;
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.gm_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        initUI(getResources().getString(R.string.channels));

        firebaseUser = FacebookLoginActivity.getmAuth().getCurrentUser();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.gm_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SelectGM_Activity.class);
                startActivity(intent);
            }
        });

        groupMessages = new ArrayList<Map<String, String>>();
        gmAdapter = new SimpleAdapter(this, groupMessages, R.layout.grouptext_item,
                new String[]{"names", "message", "key"},
                new int[]{R.id.members, R.id.message, R.id.key});
        gm_list = (ListView) findViewById(R.id.all_gms);
        gm_list.setAdapter(gmAdapter);
        gm_list.setOnItemClickListener(new OnItemClickListenerListViewItem());

        fmt = new SimpleDateFormat("MM.dd.yy HH:mm z");

        Intent selectGM = getIntent();
        key = selectGM.getStringExtra("key");
        names = selectGM.getStringExtra("names");

        // Switching a listener to the selected channel.
        initFirebase();
        firebase.child("channels").addChildEventListener(channelListener);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_gm_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {

    }

    private void addGM(String members, String message, String key) {
        Map<String, String> groupMessage = new HashMap<String, String>();
        groupMessage.put("names", members);
        groupMessage.put("message", message);
        groupMessage.put("key", key);
        groupMessages.add(groupMessage);

        gmAdapter.notifyDataSetChanged();
        channels.add(key);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(this, HomePage.class);
            finish();
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_messages) {
            Intent intent = new Intent(this, MessageActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_rooms) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initFirebase() {
        channels = new ArrayList<String>();
        firebase = FirebaseDatabase.getInstance().getReference();
    }

    /*
     * Initialize pre-defined channels as Activity menu.
     * Once a channel is selected, ChildEventListener is attached and
     * waits for groupMessages.
     */
    private void initUI(String channelString) {
        Log.d(TAG, "Channels : " + channelString);

        channelListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String prevKey) {
//                return;
                if (snapshot.getKey().contains(firebaseUser.getUid())) {
                    GroupTextItem gm = new GroupTextItem();
                    Iterable<DataSnapshot> dataSnapshots = snapshot.getChildren();
                    Iterable<DataSnapshot> messageSnapshots;
                    for (DataSnapshot dataSnapshot : dataSnapshots) {
                        if (dataSnapshot.getKey().equals("names")) {
                            gm.setGroupMemberNames((String) dataSnapshot.getValue());
                        } else if (dataSnapshot.getKey().equals("history")) {
                            messageSnapshots = dataSnapshot.getChildren();
                            for (DataSnapshot messageSnapshot : messageSnapshots) {
                                for (DataSnapshot message : messageSnapshot.getChildren()) {
                                    if (message.getKey().equals("text")) {
                                        gm.setLastMessageSent((String) message.getValue());
                                    } else if (message.getKey().equals("time")) {
                                        gm.setTime((Long) message.getValue());
                                    }
                                }
                                break;
                            }
                        }
                    }
                    // Extract attributes from Message object to display on the screen.
                    addGM(gm.getGroupMemberNames(), gm.getLastMessageSent() + "\t" +
                            fmt.format(new Date(gm.getTimeOfLastMessage())), snapshot.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, error.getDetails());
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String prevKey) {
            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String prevKey) {
            }
        };
    }
}
