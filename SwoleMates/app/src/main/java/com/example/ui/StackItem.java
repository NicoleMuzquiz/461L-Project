package com.example.ui;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

/**
 * Created by einwo on 3/30/2017.
 */

public class StackItem implements Comparable<StackItem> {

    private Bitmap image;
    private String userName;
    private String userDesc;

    public StackItem(String userName, String userDesc) {
        this.userName = userName;
        this.userDesc = userDesc;
    }

    public StackItem(String userName, String userDesc, Bitmap img) {
        this.userName = userName;
        this.userDesc = userDesc;
        this.image = img;
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
            if (p.getUserName().equals(this.userName) && p.getUserDesc().equals(this.userDesc))
                return true;
        }
        return false;
    }
}
