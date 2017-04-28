package com.example.swolemates;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.example.messaging.MessageActivity;
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
    private String sport, playStyle, rank, email;

    private FirebaseUser firebaseUser;
    private DatabaseReference firebase;
    private StackAdapter adapt;
    private ArrayList<StackItem> items, matchUsers, ignoreUsers;
    private SwoleUser swoleUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
        sport = i.getStringExtra("sport");
        playStyle = i.getStringExtra("playStyle");
        rank = i.getStringExtra("rank");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        email = firebaseUser.getEmail();
        this.stackView = (StackView) findViewById(R.id.stackView);

        items = new ArrayList<StackItem>();
        matchUsers = new ArrayList<StackItem>();
        ignoreUsers = new ArrayList<StackItem>();

        for (int p = 0; p < NUMBER_OF_FRAGMENTS; p++) {
            items.add(new StackItem("Buffering", "Android Photo"));
        }
        firebase = FirebaseDatabase.getInstance().getReference().child("users");

        SwoleUser user = new SwoleUser();
        user.setPlayStyle(playStyle);
        user.setBasketball_rank(Integer.parseInt(rank));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(firebaseUser.getUid(), user);
        firebase.updateChildren(map);

        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevKey) {
                swoleUser = (SwoleUser) dataSnapshot.getValue(SwoleUser.class);
                if (swoleUser != null && email.equals(swoleUser.getEmail()))
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
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


    public void homePageButton(View v) {
        int id = v.getId();

        if (id == R.id.match) {
            stackView.showNext();

            StackItem i = items.remove(0);
            matchUsers.add(i);
            adapt.notifyDataSetChanged();

            // split up data from StackItem and create SwoleUser from it
            Map<String, Object> map = new HashMap<String, Object>();
            SwoleUser user = new SwoleUser();
            // set swoleUser data
            user.setBaseball_rank(8);
            user.setName("Unwana Nwoko");

            map.put(i.getId(), user);
            firebase.child(firebaseUser.getUid() + "/matches")
                    .updateChildren(map);

        } else if (id == R.id.ignore) {
            stackView.showNext();

            StackItem i = items.remove(0);
            ignoreUsers.add(i);
            adapt.notifyDataSetChanged();

            // split up data from StackItem and create SwoleUser from it
            Map<String, Object> map = new HashMap<String, Object>();
            SwoleUser user = new SwoleUser();
            map.put(firebaseUser.getEmail(), user);
            firebase.child("users/" + firebaseUser.getUid() + "/rejections")
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
