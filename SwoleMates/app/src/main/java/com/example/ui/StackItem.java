package com.example.ui;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

/**
 * Created by einwo on 3/30/2017.
 */

public class StackItem implements Comparable<StackItem> {

    private String imageName;
    private Bitmap image;

    // "image1","image2",..
    private String itemText;

    public StackItem(String text, String imageName) {
        this.imageName = imageName;
        this.itemText = text;
    }

    public StackItem(String text, String imageName, Bitmap img) {
        this.imageName = imageName;
        this.itemText = text;
        this.image = img;
    }

    public String getImageName() {
        return imageName;
    }

    public Bitmap getImage() { return image; }

    public String getItemText() {
        return itemText;
    }

    @Override
    public int compareTo(@NonNull StackItem o) {
        if(o.imageName.equals(this.imageName) && o.itemText.equals(this.itemText))
            return 0;
        return -1;
    }
}
