package com.example.swolemates;


import android.content.Intent;
import android.support.test.espresso.intent.*;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import com.example.login.FacebookLoginActivity;
import com.example.startup.EntryActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;



@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityTestRule<EntryActivity> mActivityRule = new ActivityTestRule<>(EntryActivity.class);

    @Test
    public void noLoggedInUser(){
        Intents.init();

        mActivityRule.launchActivity(new Intent());
        intended(hasComponent(FacebookLoginActivity.class.getName()));

        Intents.release();
    }

    @Test
    public void UserLoggedIn(){
        Intents.init();

        mActivityRule.launchActivity(new Intent());
        intended(hasComponent(HomePage.class.getName()));

        Intents.release();

    }
}
