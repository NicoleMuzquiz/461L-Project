package com.example.messaging;

import android.graphics.Bitmap;

import com.example.swolemates.SwoleUser;

/**
 * Created by einwo on 4/29/2017.
 */

public class UserBox {

    private Bitmap image = null;
    private String name = null;
    private String id = null;
    private SwoleUser user = null;
    private boolean selected = false;

    public UserBox(Bitmap image, SwoleUser user, boolean selected) {
        this.image = image;
        this.name = user.getName();
        this.selected = selected;
        this.id = user.getId();
        this.user = user;
    }

    public Bitmap getImage() {
        return image;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public SwoleUser getUser() { return user; }
    public void setUser(SwoleUser user) { this.user = user; }
}
