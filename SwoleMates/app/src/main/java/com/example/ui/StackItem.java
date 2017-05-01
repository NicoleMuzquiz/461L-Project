package com.example.ui;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.example.swolemates.SwoleUser;

/**
 * Created by einwo on 3/30/2017.
 */

public class StackItem implements Comparable<StackItem> {

    private Bitmap image;
    private String userName;
    private String userDesc;
    private String id;
    private String email;
    private SwoleUser user;

    public StackItem() {
        this.userDesc = "Buffering";
    }

    public StackItem(SwoleUser swoleUser) {
        this.userName = swoleUser.getName();
        this.userDesc = swoleUser.toString();
        this.id = swoleUser.getId();
        this.email = swoleUser.getEmail();
    }

    public StackItem(Bitmap img, SwoleUser user) {
        this.userName = user.getName();
        this.userDesc = user.toString();
        this.user = user;
        this.image = img;
        this.id = user.getId();
        this.email = user.getEmail();
    }

    public String getUserName() {
        return userName;
    }

    public String getUserDesc() { return userDesc; }

    public Bitmap getUserImg() { return image; }

    @Override
    public int compareTo(@NonNull StackItem o) {
        if(o.getUserName().equals(this.userName) && o.getUserDesc().equals(this.userDesc))
            return 0;
        return -1;
    }

    @Override
    public boolean equals(@NonNull Object o) {
        if(o instanceof StackItem) {
            StackItem p = (StackItem) o;
            if (p.getId().equals(this.id))
                return true;
        }
        return false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SwoleUser getUser() {
        return user;
    }

    public void setUser(SwoleUser user) {
        this.user = user;
    }
}
