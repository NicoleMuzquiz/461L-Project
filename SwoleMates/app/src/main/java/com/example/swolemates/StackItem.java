package com.example.swolemates;

/**
 * Created by einwo on 3/30/2017.
 */

public class StackItem {

    private String imageName;

    // "image1","image2",..
    private String itemText;

    public StackItem(String text, String imageName) {
        this.imageName = imageName;
        this.itemText = text;
    }

    public String getImageName() {
        return imageName;
    }


    public String getItemText() {
        return itemText;
    }
}
