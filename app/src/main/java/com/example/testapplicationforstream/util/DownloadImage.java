package com.example.testapplicationforstream.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
    private Bitmap bitmapMain;

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11;
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
            bitmap  = Bitmap.createScaledBitmap(mIcon11, 300, 300, false);
        } catch (Exception e) {
            Log.d("Error", e.getStackTrace().toString());

        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap result) {
        bitmapMain = result;
        Log.d("TAG", "OK");
    }

    public Bitmap getBmImage() {
        return bitmapMain;
    }
}