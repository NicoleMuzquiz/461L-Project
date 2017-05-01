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
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.login.FacebookLoginActivity;
import com.example.settings.DefaultSettingsActivity;
import com.example.swolemates.HomePage;
import com.example.swolemates.R;
import com.example.swolemates.SelectSport;
import com.example.swolemates.SwoleUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Main activity to select a channel and exchange messages with other users
 * The app expects users to authenticate with Google ID. It also sends user
 * activity logs to a Servlet instance through Firebase.
 */
public class MessageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnKeyListener,
        View.OnClickListener {

    // Firebase keys commonly used with backend Servlet instances
    private static final String IBX = "inbox";
    private static final String CHS = "channels";
    private static final String REQLOG = "requestLogger";

    private static final int RC_SIGN_IN = 9001;

    private static String TAG = "MessageActivity";
    private static FirebaseLogger fbLog;

    private FirebaseUser firebaseUser;
    private DatabaseReference firebase;
    private String key;
    private String inbox;
    private String currentChannel;
    private List<String> channels;
    private ChildEventListener channelListener;
    private SimpleDateFormat fmt;

    private TextView channelLabel;
    private ListView messageHistory;
    private List<Map<String, String>> messages;
    private SimpleAdapter messageAdapter;
    private EditText messageText;
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_play);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.message_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        initChannels(getResources().getString(R.string.channels));

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            updateUI(true);
        }

        channelLabel = (TextView) findViewById(R.id.channelLabel);
        Button signOutButton = (Button) findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(this);

        messages = new ArrayList<Map<String, String>>();
        messageAdapter = new SimpleAdapter(this, messages, android.R.layout.simple_list_item_2,
                new String[]{"message", "meta"}, new int[]{android.R.id.text1, android.R.id.text2});
        messageHistory = (ListView) findViewById(R.id.messageHistory);
        messageHistory.setAdapter(messageAdapter);
        messageText = (EditText) findViewById(R.id.messageText);
        messageText.setOnKeyListener(this);
        fmt = new SimpleDateFormat("MM.dd.yy HH:mm z");

        status = (TextView) findViewById(R.id.status);

        // Switching a listener to the selected channel.
        initFirebase();
        currentChannel = getIntent().getStringExtra("key");
        String names = getIntent().getStringExtra("names");
        firebase.child(CHS + "/" + currentChannel + "/history").addChildEventListener(channelListener);

        Map<String, Object> map = new HashMap<>();
        map.put("names", names);
        firebase.child(CHS + "/" + currentChannel).updateChildren(map);

        channelLabel.setText(names);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_message_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() { super.onStop(); }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
            firebase.child(CHS + "/" + currentChannel + "/history")
                    .push()
                    .setValue(new Message(messageText.getText().toString(), firebaseUser.getDisplayName()));
            Map<String, Object> map = new HashMap<String, Object>();
            SwoleUser swoleUser = new SwoleUser();
            swoleUser.setBasketball_rank(10);
            swoleUser.setBasketball_skill(8);
            swoleUser.setEmail(firebaseUser.getEmail());
            swoleUser.setName(firebaseUser.getDisplayName());
            swoleUser.setPhotoUrl(firebaseUser.getPhotoUrl().toString());
            swoleUser.setId(firebaseUser.getUid());

            map.put(firebaseUser.getUid(), swoleUser);
            firebase.child("users/" + firebaseUser.getUid() + "/data")
                    .updateChildren(map);
            firebase.child("users/" + firebaseUser.getUid() + "/connections")
                    .updateChildren(map);
            return true;
        }
        return false;
    }

    private void addMessage(String msgString, String meta) {
        Map<String, String> message = new HashMap<String, String>();
        message.put("message", msgString);
        message.put("meta", meta);
        messages.add(message);

        messageAdapter.notifyDataSetChanged();
        messageText.setText("");
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
            findViewById(R.id.channelLabel).setVisibility(View.VISIBLE);
            findViewById(R.id.messageText).setVisibility(View.VISIBLE);
            findViewById(R.id.messageHistory).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_button).setVisibility(View.GONE);
            findViewById(R.id.channelLabel).setVisibility(View.INVISIBLE);
            findViewById(R.id.messageText).setVisibility(View.INVISIBLE);
            findViewById(R.id.messageHistory).setVisibility(View.INVISIBLE);
            ((TextView) findViewById(R.id.status)).setText("");
        }
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
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_teammates) {
            Intent intent = new Intent(this, SelectGM_Activity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_messages) {
            Intent intent = new Intent(this, GroupTextActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_leave_sport) {
            Intent intent = new Intent(this, SelectSport.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, DefaultSettingsActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(this, FacebookLoginActivity.class);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.message_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initFirebase() {
//        channels = new ArrayList<String>();
        firebase = FirebaseDatabase.getInstance().getReference();
        requestLogger();
    }

    // [START requestLogger]
    /*
     * Request that a Servlet instance be assigned.
     */
    private void requestLogger() {
        firebase.child(IBX + "/" + inbox).removeValue();
        firebase.child(IBX + "/" + inbox).addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    fbLog = new FirebaseLogger(firebase, IBX + "/" + snapshot.getValue().toString()
                            + "/logs");
                    firebase.child(IBX + "/" + inbox).removeEventListener(this);
                    fbLog.log(inbox, "Signed in");
                }
            }

            public void onCancelled(DatabaseError error) {
                Log.e(TAG, error.getDetails());
            }
        });

        firebase.child(REQLOG).push().setValue(inbox);
    }
// [END requestLogger]

    /*
     * Initialize pre-defined channels as Activity menu.
     * Once a channel is selected, ChildEventListener is attached and
     * waits for messages.
     */
    private void initChannels(String channelString) {
        Log.d(TAG, "Channels : " + channelString);
        channels = new ArrayList<String>();
        String[] topicArr = channelString.split(",");
        for (int i = 0; i < topicArr.length; i++) {
            channels.add(i, topicArr[i]);
        }

        channelListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String prevKey) {
                Message message = (Message) snapshot.getValue(Message.class);
                // Extract attributes from Message object to display on the screen.
                addMessage(message.getText(), fmt.format(new Date(message.getTimeLong())) + "\n"
                        + message.getDisplayName());
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
