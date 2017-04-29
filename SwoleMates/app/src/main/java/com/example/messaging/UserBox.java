package com.example.messaging;

import android.graphics.Bitmap;

/**
 * Created by einwo on 4/29/2017.
 */

public class UserBox {

    Bitmap image = null;
    String name = null;
    boolean selected = false;

    public UserBox(Bitmap image, String name, boolean selected) {
        super();
        this.image = image;
        this.name = name;
        this.selected = selected;
    }

    public Bitmap getCode() {
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

}
