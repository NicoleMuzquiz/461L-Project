package com.example.swolemates;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import android.widget.StackView;
import android.widget.Toast;

import com.example.messaging.MessageActivity;
import com.example.ui.StackAdapter;
import com.example.ui.StackItem;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int NUMBER_OF_FRAGMENTS = 15;

    private StackView stackView;
    private int currItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.stackView = (StackView) findViewById(R.id.stackView);

        final ArrayList<StackItem> items = new ArrayList<StackItem>();
        final ArrayList<StackItem> removedItems = new ArrayList<StackItem>();

        for(int i = 0; i < NUMBER_OF_FRAGMENTS; i++){

            items.add(new StackItem("image_"+i+".pngllllllllllllllllllllllllllllllllllll", "Android Photo"));
        }

        final StackAdapter adapt = new StackAdapter(this, items);
        stackView.setAdapter(adapt);
        stackView.setHorizontalScrollBarEnabled(true);
        stackView.setBackgroundColor(getResources().getColor(R.color.grey_100));

        stackView.setOnTouchListener(new OnSwipeTouchListener(HomePage.this) {
            public void onSwipeTop() {
                stackView.setBackgroundColor(getResources().getColor(R.color.com_facebook_blue));
                Toast.makeText(HomePage.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                stackView.setBackgroundColor(getResources().getColor(R.color.com_facebook_blue));
                Toast.makeText(HomePage.this, "right", Toast.LENGTH_SHORT).show();
                items.add(0, removedItems.remove(removedItems.size() - 1));
                adapt.setItems(items);
                stackView.setAdapter(adapt);
            }
            public void onSwipeLeft() {
                stackView.setBackgroundColor(getResources().getColor(R.color.com_facebook_blue));
                Toast.makeText(HomePage.this, "left", Toast.LENGTH_SHORT).show();
                removedItems.add(items.remove(0));
                adapt.setItems(items);
                stackView.setAdapter(adapt);
            }
            public void onSwipeBottom() {
                stackView.setBackgroundColor(getResources().getColor(R.color.com_facebook_blue));
                Toast.makeText(HomePage.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });
        adapt.notifyDataSetChanged();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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

    private static class OnSwipeTouchListener implements View.OnTouchListener {

        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener (Context ctx){
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
                    }
                    else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
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
