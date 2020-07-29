package com.example.testapplicationforstream.entity;

import android.graphics.Bitmap;

public class NASAItem {
    private Bitmap image;
    private String imageUrl;
    private String itemName;
    private String itemDescription;
    private String imageDate;

    public NASAItem(String imageUrl, String itemName, String itemDescription, String imageDate) {
        this.imageUrl = imageUrl;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.imageDate = imageDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getImageDate() {
        return imageDate;
    }

    public void setImageDate(String imageDate) {
        this.imageDate = imageDate;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
