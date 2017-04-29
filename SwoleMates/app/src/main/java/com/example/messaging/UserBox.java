package com.example.messaging;

import android.graphics.Bitmap;

/**
 * Created by einwo on 4/29/2017.
 */

public class UserBox {

    private Bitmap image = null;
    private String name = null;
    private String id = null;
    private boolean selected = false;

    public UserBox(Bitmap image, String name, boolean selected, String id) {
        this.image = image;
        this.name = name;
        this.selected = selected;
        this.id = id;
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
}
