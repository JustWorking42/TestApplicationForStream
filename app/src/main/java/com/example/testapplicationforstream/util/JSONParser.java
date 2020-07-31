package com.example.testapplicationforstream.util;

import android.util.Log;

import com.example.testapplicationforstream.entity.NASAItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONParser {
    private static ArrayList<NASAItem> nasa = new ArrayList<>();

    public void parse(String in) {
        try {
            JSONObject jsonIn = new JSONObject(in);
            JSONObject jsonObject = jsonIn.getJSONObject("collection");
            JSONArray jsonArray = jsonObject.getJSONArray("items");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsoObject = jsonArray.getJSONObject(i);

                JSONArray ar = jsoObject.getJSONArray("links");
                JSONObject obj = ar.getJSONObject(0);
                String imageUrl = obj.getString("href");

                JSONArray arr = jsoObject.getJSONArray("data");
                JSONObject data = arr.getJSONObject(0);

                String name = data.getString("title");
                String description = data.getString("description");
                String imageDate = data.getString("date_created");
                NASAItem nasaItem = new NASAItem(imageUrl, name, description, imageDate);

                nasa.add(nasaItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("SIZE", nasa.size() + "size");
    }

    public static ArrayList<NASAItem> getNasa() {
        return nasa;
    }

    public static void setNasa(ArrayList<NASAItem> nasa) {
        JSONParser.nasa = nasa;
    }
}
