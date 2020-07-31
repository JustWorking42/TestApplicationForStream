package com.example.testapplicationforstream.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.example.testapplicationforstream.callbacks.ImageLoadSingleCallback;
import com.example.testapplicationforstream.cashe.ImagesCache;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SingleDownload extends AsyncTask<String, Void, Bitmap> {
    private int inSampleSize = 0;
    private String imageUrl;
    private ImageLoadSingleCallback loadCallback;
    private ImagesCache cache;
    private int desiredWidth, desiredHeight;
    private Bitmap image = null;

    public SingleDownload(ImagesCache cache,
                             int desiredWidth,
                             int desiredHeight,
                             ImageLoadSingleCallback loadCallback) {

        this.cache = cache;
        this.loadCallback = loadCallback;
        this.desiredWidth = desiredWidth;
        this.desiredHeight = desiredHeight;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        imageUrl = params[0];

        return getImage(imageUrl);
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);

        if (result != null) {
            cache.addImageToWarehouse(imageUrl, result);

            if (loadCallback != null) {
                Log.e("getImage", "adapter");
                loadCallback.onDownload();
            }
        }
    }

    private Bitmap getImage(String imageUrl) {
        if (cache.getImageFromWarehouse(imageUrl) == null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inSampleSize = inSampleSize;

            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream stream = connection.getInputStream();
                image = BitmapFactory.decodeStream(stream, null, options);
                int imageWidth = options.outWidth;
                int imageHeight = options.outHeight;

                if (imageWidth > desiredWidth || imageHeight > desiredHeight) {
                    System.out.println("imageWidth:" + imageWidth + ", imageHeight:" + imageHeight);
                    inSampleSize = inSampleSize + 2;
                    getImage(imageUrl);

                } else {
                    options.inJustDecodeBounds = false;
                    connection = (HttpURLConnection) url.openConnection();
                    stream = connection.getInputStream();
                    image = BitmapFactory.decodeStream(stream, null, options);

                    return image;
                }
            } catch (Exception e) {
                Log.e("getImage", e.toString());
            }
        }

        return image;
    }
}

