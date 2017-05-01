package com.example.swolemates;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int NUMBER_OF_FRAGMENTS = 1;

    private StackView stackView;
    private Bitmap img;
    private ImageView userImg;
    private int currItem = 0;
    private String sport, playStyle, email;
    private Integer rank;

    private FirebaseUser firebaseUser;
    private DatabaseReference firebase;
    private StackAdapter adapt;
    private ArrayList<StackItem> items, matchUsers, ignoreUsers;
    private ArrayList<SwoleUser> potentials, matches;
    private SwoleUser swoleUser, mySwoleUser;

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
        sport = prefs.getString("user_sport", null);
        playStyle = prefs.getString("user_play_style", null);
        rank = Integer.parseInt(prefs.getString("user_rank", null));

        items = new ArrayList<StackItem>();
        matchUsers = new ArrayList<StackItem>();
        ignoreUsers = new ArrayList<StackItem>();

        potentials = new ArrayList<SwoleUser>();
        matches = new ArrayList<SwoleUser>();

        for (int p = 0; p < NUMBER_OF_FRAGMENTS; p++) {
            items.add(new StackItem("Buffering", "Android Photo"));
        }
        firebase = FirebaseDatabase.getInstance().getReference();

        mySwoleUser = new SwoleUser();
        Map<String, Object> map = new HashMap<String, Object>();
        mySwoleUser.setBasketball_skill(rank);
        mySwoleUser.setEmail(firebaseUser.getEmail());
        mySwoleUser.setName(firebaseUser.getDisplayName());
        mySwoleUser.setPhotoUrl(firebaseUser.getPhotoUrl().toString());
        mySwoleUser.setId(firebaseUser.getUid());
        mySwoleUser.setPlayStyle(playStyle);

        map.put(firebaseUser.getUid(), mySwoleUser);
        firebase.child("rooms/" + sport)
                .updateChildren(map);
        firebase.child("users/" + firebaseUser.getUid() + "/data")
                .updateChildren(map);

        addListeners();

        /* UI set-up */
        adapt = new StackAdapter(this, items);
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
        // Inflate the menu; this adds items to the action bar if it is present.
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void homePageButton(View v) {
        int id = v.getId();

        if (id == R.id.match) {
            stackView.showNext();

            StackItem otherUserStackItem = items.remove(0);
            matchUsers.add(otherUserStackItem);
            adapt.notifyDataSetChanged();

            String otherEmail = otherUserStackItem.getEmail();
            String otherId = otherUserStackItem.getId();

            // split up data from StackItem and create SwoleUser from it
            Map<String, Object> map = new HashMap<String, Object>();
            SwoleUser otherUser = new SwoleUser();
            otherUser.setBasketball_rank(10);
            otherUser.setBasketball_skill(8);
            otherUser.setEmail(otherEmail);
            otherUser.setName(otherEmail.substring(0, otherEmail.indexOf("@")));
            otherUser.setPhotoUrl(firebaseUser.getPhotoUrl().toString());
            otherUser.setId(otherUserStackItem.getId());

            if (!potentials.contains(otherUser)) {
                map.put(firebaseUser.getUid(), mySwoleUser);
                firebase.child("users/" + otherUserStackItem.getId() + "/potential")
                        .updateChildren(map);
                firebase.child("rooms/" + sport + "/" + otherUserStackItem.getId() + "/potential")
                        .updateChildren(map);
            } else {
                // delete myself from other user's potential matches
                map.put(otherUserStackItem.getId(), null);
                firebase.child("users/" + firebaseUser.getUid() + "/potential")
                        .setValue(map);
                firebase.child("rooms/" + sport + "/" + firebaseUser.getUid() + "/potential")
                        .setValue(map);
                map.clear();

                // add other user to my matches
                map.put(otherUserStackItem.getId(), otherUser);
                firebase.child("users/" + firebaseUser.getUid() + "/matches")
                        .updateChildren(map);
                firebase.child("rooms/" + sport + "/" + firebaseUser.getUid() + "/matches")
                        .updateChildren(map);
                map.clear();

                // add myself to other user's matches
                map.put(firebaseUser.getUid(), mySwoleUser);
                firebase.child("users/" + otherUserStackItem.getId() + "/matches")
                        .updateChildren(map);
                firebase.child("rooms/" + sport + "/" + otherUserStackItem.getId() + "/matches")
                        .updateChildren(map);

            }

        } else if (id == R.id.ignore) {
            stackView.showNext();

            StackItem i = items.remove(0);
            ignoreUsers.add(i);
            adapt.notifyDataSetChanged();

            String otherEmail = i.getEmail();
            String otherId = i.getId();

            // split up data from StackItem and create SwoleUser from it
            Map<String, Object> map = new HashMap<String, Object>();
            SwoleUser swoleUser = new SwoleUser();
            swoleUser.setBasketball_rank(10);
            swoleUser.setBasketball_skill(8);
            swoleUser.setEmail(otherEmail);
            swoleUser.setName(otherEmail.substring(0, otherEmail.indexOf("@")));
            swoleUser.setPhotoUrl(firebaseUser.getPhotoUrl().toString());
            swoleUser.setId(i.getId());

            map.put(i.getId(), swoleUser);
            firebase.child("users/" + i.getId() + "/rejections")
                    .updateChildren(map);
            firebase.child("rooms/" + sport + "/" + i.getId() + "/rejections")
                    .updateChildren(map);

        }

    }

    private class GetUserImg extends AsyncTask<String, Void, String> {
        //get book thumbnail

        @Override
        protected String doInBackground(String... imgURLs) {
            //attempt to download image
            try {
                //try to download
                img = null;
                URL imgURL = new URL(imgURLs[0]);
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
            return "";
        }

        protected void onPostExecute(String result) {
//            items.remove(new StackItem("Buffering", "Android Photo"));
            StackItem stack = new StackItem(swoleUser.getName(), swoleUser.toString(), img);
            stack.setId(swoleUser.getId());
            stack.setEmail(swoleUser.getEmail());
            items.add(0, stack);
            adapt.notifyDataSetChanged();
        }


    }

    private void addListeners() {
        /* Room Listener */
        firebase.child("rooms/" + sport).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevKey) {
                swoleUser = (SwoleUser) dataSnapshot.getValue(SwoleUser.class);
                if (swoleUser != null && !email.equals(swoleUser.getEmail()))
                    new GetUserImg().execute(swoleUser.getPhotoUrl());
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
                swoleUser = (SwoleUser) dataSnapshot.getValue(SwoleUser.class);
                potentials.add(swoleUser);
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
