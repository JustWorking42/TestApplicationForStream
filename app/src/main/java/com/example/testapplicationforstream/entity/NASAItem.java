package com.example.testapplicationforstream.entity;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class NASAItem implements Parcelable {
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

    public NASAItem(Parcel in) {
        imageUrl = in.readString();
        itemName = in.readString();
        itemDescription = in.readString();
        imageDate = in.readString();
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
    public static final Creator<NASAItem> CREATOR = new Creator<NASAItem>() {
        @Override
        public NASAItem createFromParcel(Parcel in) {
            return new NASAItem(in);
        }

        @Override
        public NASAItem[] newArray(int size) {
            return new NASAItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
        dest.writeString(itemName);
        dest.writeString(itemDescription);
        dest.writeString(imageDate);
    }
}
