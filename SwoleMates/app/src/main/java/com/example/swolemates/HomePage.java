package com.example.swolemates;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.StackView;
import android.widget.Toast;

import com.example.login.FacebookLoginActivity;
import com.example.messaging.GroupTextActivity;
import com.example.messaging.SelectGM_Activity;
import com.example.settings.DefaultSettingsActivity;
import com.example.ui.StackAdapter;
import com.example.ui.StackItem;
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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int NUMBER_OF_FRAGMENTS = 1;

    private StackView stackView;
    private Bitmap img;
    private ImageView userImg;
    private int currItem = 0;
    private String sport, playStyle, email;
    private Integer skill, age, weight, height;

    private FirebaseUser firebaseUser;
    private DatabaseReference firebase;
    private StackAdapter adapt;
    private ArrayList<StackItem> usersInRoom, matchUsers, ignoreUsers;
    private ArrayList<SwoleUser> potentials, connections, rejections;
    private SwoleUser swoleUser, mySwoleUser;

    private Lock swoleUserLock;
    private Condition swoleUserAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        email = firebaseUser.getEmail();
        this.stackView = (StackView) findViewById(R.id.stackView);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        age = Integer.parseInt(prefs.getString("user_age", null));
        weight = Integer.parseInt(prefs.getString("user_weight", null));
        height = Integer.parseInt(prefs.getString("user_height", null));

        sport = prefs.getString("user_sport", null);
        playStyle = prefs.getString("user_play_style", null);
        skill = Integer.parseInt(prefs.getString("user_skill", null));

        usersInRoom = new ArrayList<StackItem>();
        matchUsers = new ArrayList<StackItem>();
        ignoreUsers = new ArrayList<StackItem>();

        potentials = new ArrayList<SwoleUser>();
        connections = new ArrayList<SwoleUser>();
        rejections = new ArrayList<SwoleUser>();

        swoleUserLock = new ReentrantLock();

        for (int p = 0; p < NUMBER_OF_FRAGMENTS; p++) {
            usersInRoom.add(new StackItem());
        }
        firebase = FirebaseDatabase.getInstance().getReference();

        mySwoleUser = new SwoleUser();
        Map<String, Object> map = new HashMap<String, Object>();

        mySwoleUser.setAge(age);
        mySwoleUser.setWeight(weight);
        mySwoleUser.setHeight(height);
        mySwoleUser.setEmail(firebaseUser.getEmail());
        mySwoleUser.setName(firebaseUser.getDisplayName());
        mySwoleUser.setPhotoUrl(firebaseUser.getPhotoUrl().toString());
        mySwoleUser.setId(firebaseUser.getUid());
        mySwoleUser.setPlayStyle(playStyle);
        setSport();

        map.put(firebaseUser.getUid(), mySwoleUser);
        firebase.child("rooms/" + sport)
                .updateChildren(map);
        firebase.child("users/" + firebaseUser.getUid() + "/data")
                .updateChildren(map);

        addListeners();

        /* UI set-up */
        adapt = new StackAdapter(this, usersInRoom);
        stackView.setAdapter(adapt);
        stackView.setHorizontalScrollBarEnabled(true);
        stackView.setBackgroundColor(getResources().getColor(R.color.grey_100));

        stackView.setOnTouchListener(new OnSwipeTouchListener(HomePage.this) {
            public void onSwipeTop() {
                Toast.makeText(HomePage.this, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
                Toast.makeText(HomePage.this, "right", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeLeft() {
                Toast.makeText(HomePage.this, "left", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeBottom() {
                Toast.makeText(HomePage.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });
        adapt.notifyDataSetChanged();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds usersInRoom to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void homePageButton(View v) {
        int id = v.getId();

        if (id == R.id.match) {
            stackView.showNext();

            StackItem otherUserStackItem = usersInRoom.remove(0);
            matchUsers.add(otherUserStackItem);
            adapt.notifyDataSetChanged();

            // split up data from StackItem and create SwoleUser from it
            Map<String, Object> map = new HashMap<String, Object>();

            SwoleUser otherUser = otherUserStackItem.getUser();

            if (!potentials.contains(otherUser)) {
                map.put(firebaseUser.getUid(), mySwoleUser);
                firebase.child("users/" + otherUser.getId() + "/potential")
                        .updateChildren(map);
            } else {
                // delete myself from other user's potential connections
                map.put(otherUser.getId(), null);
                firebase.child("users/" + firebaseUser.getUid() + "/potential")
                        .setValue(map);
                map.clear();

                // add other user to my connections
                map.put(otherUser.getId(), otherUser);
                firebase.child("users/" + firebaseUser.getUid() + "/connections")
                        .updateChildren(map);
                map.clear();

                // add myself to other user's connections
                map.put(firebaseUser.getUid(), mySwoleUser);
                firebase.child("users/" + otherUser.getId() + "/connections")
                        .updateChildren(map);

            }

        } else if (id == R.id.ignore) {
            stackView.showNext();

            StackItem otherUserStackItem = usersInRoom.remove(0);
            ignoreUsers.add(otherUserStackItem);
            adapt.notifyDataSetChanged();

            // split up data from StackItem and create SwoleUser from it
            Map<String, Object> map = new HashMap<String, Object>();
            SwoleUser user = otherUserStackItem.getUser();

            map.put(otherUserStackItem.getId(), user);
            firebase.child("users/" + user.getId() + "/rejections")
                    .updateChildren(map);

        }

    }

    private void setSport() {

 /*     Basketball
        Weightlifting
        Football
        Volleyball
        Swimming
        Baseball
        Soccer
        Running */
        if (sport.equals("Basketball")) {
            mySwoleUser.setBasketball_skill(skill);
        } else if (sport.equals("Weightlifting")) {
            mySwoleUser.setWeightlifting_skill(skill);
        } else if (sport.equals("Football")) {
            mySwoleUser.setFootball_skill(skill);
        } else if (sport.equals("Volleyball")) {
            mySwoleUser.setVolleyball_skill(skill);
        } else if (sport.equals("Swimming")) {
            mySwoleUser.setSwimming_skill(skill);
        } else if (sport.equals("Baseball")) {
            mySwoleUser.setBaseball_skill(skill);
        } else if (sport.equals("Soccer")) {
            mySwoleUser.setSoccer_skill(skill);
        } else if (sport.equals("Running")) {
            mySwoleUser.setRunning_skill(skill);
        }
    }

    private void getUserImg(String imgURLs) {
        //attempt to download image
        try {
            //try to download
            img = null;
            URL imgURL = new URL(imgURLs);
            URLConnection imgConn = imgURL.openConnection();
            imgConn.connect();
            InputStream imgIn = imgConn.getInputStream();
            BufferedInputStream imgBuff = new BufferedInputStream(imgIn);
            img = BitmapFactory.decodeStream(imgBuff);
            imgBuff.close();
            imgIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        StackItem stack = new StackItem(img, swoleUser);

        if (connections.contains(swoleUser) || rejections.contains(swoleUser)) return;

        stack.setId(swoleUser.getId());
        stack.setEmail(swoleUser.getEmail());

        usersInRoom.add(0, stack);
        adapt.notifyDataSetChanged();
    }

//    private class getUserImg extends AsyncTask<String, Void, String> {
//        //get book thumbnail
//
//        @Override
//        protected String doInBackground(String... imgURLs) {
//            //attempt to download image
//            try {
//                //try to download
//                img = null;
//                URL imgURL = new URL(imgURLs[0]);
//                URLConnection imgConn = imgURL.openConnection();
//                imgConn.connect();
//                InputStream imgIn = imgConn.getInputStream();
//                BufferedInputStream imgBuff = new BufferedInputStream(imgIn);
//                img = BitmapFactory.decodeStream(imgBuff);
//                imgBuff.close();
//                imgIn.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return "";
//        }
//
//        protected void onPostExecute(String result) {
////            usersInRoom.remove(new StackItem("Buffering", "Android Photo"));
//            StackItem stack = new StackItem(img, swoleUser);
//
//            if (connections.contains(swoleUser) || rejections.contains(swoleUser)) return;
//
//            stack.setId(swoleUser.getId());
//            stack.setEmail(swoleUser.getEmail());
//            swoleUserLock.notifyAll();
//            swoleUserLock.unlock();
//
//            usersInRoom.add(0, stack);
//            adapt.notifyDataSetChanged();
//        }
//
//
//    }

    private void addListeners() {
        /* Room Listener */
        firebase.child("rooms/" + sport).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevKey) {
                synchronized (swoleUserLock) {
                    swoleUser = (SwoleUser) dataSnapshot.getValue(SwoleUser.class);
                    if (swoleUser != null && !email.equals(swoleUser.getEmail()))
                        getUserImg(swoleUser.getPhotoUrl());
                    swoleUserLock.notifyAll();
                }
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

        /* Potential Matches Listener */
        firebase.child("users/" + firebaseUser.getUid() + "/potential").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevKey) {
                synchronized (swoleUserLock) {
                    swoleUser = (SwoleUser) dataSnapshot.getValue(SwoleUser.class);
                    potentials.add(swoleUser);
                    swoleUserLock.notifyAll();
                }
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

        /* Matched Teammates Listener */
        firebase.child("users/" + firebaseUser.getUid() + "/connections").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevKey) {
                synchronized (swoleUserLock) {
                    swoleUser = (SwoleUser) dataSnapshot.getValue(SwoleUser.class);

                    // remove user from stack view if you've already connected with them
                    StackItem temp = new StackItem();
                    temp.setId(swoleUser.getId());
                    usersInRoom.remove(temp);

                    connections.add(swoleUser);
                    swoleUserLock.notifyAll();
                }
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

        /* Rejected Users Listener */
        firebase.child("users/" + firebaseUser.getUid() + "/rejections").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevKey) {
                synchronized (swoleUserLock) {
                    swoleUser = (SwoleUser) dataSnapshot.getValue(SwoleUser.class);

                    // remove user from stack view if you've already rejected them
                    StackItem temp = new StackItem();
                    temp.setId(swoleUser.getId());
                    usersInRoom.remove(temp);

                    rejections.add(swoleUser);
                    swoleUserLock.notifyAll();
                }
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

    private static class OnSwipeTouchListener implements View.OnTouchListener {

        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener(Context ctx) {
            gestureDetector = new GestureDetector(ctx, new GestureListener());
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                            result = true;
                        }
                    } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                        result = true;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }

        public void onSwipeRight() {
        }

        public void onSwipeLeft() {
        }

        public void onSwipeTop() {
        }

        public void onSwipeBottom() {
        }
    }
}
