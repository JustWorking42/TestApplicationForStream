package com.example.testapplicationforstream.cashe;

import android.graphics.Bitmap;
import android.util.LruCache;

public class ImagesCache {
        private LruCache<String, Bitmap> imagesWarehouse;
        private static ImagesCache cache;

        public static ImagesCache getInstance() {
            if(cache == null) {
                cache = new ImagesCache();
            }
            return cache;
        }

        public void initializeCache() {
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() /1024);
            final int cacheSize = maxMemory / 8;

            imagesWarehouse = new LruCache<String, Bitmap>(cacheSize) {
                protected int sizeOf(String key, Bitmap value) {
                    int bitmapByteCount = value.getRowBytes() * value.getHeight();
                    return bitmapByteCount / 1024;
                }
            };
        }

        public void addImageToWarehouse(String key, Bitmap value) {
            if(imagesWarehouse != null && imagesWarehouse.get(key) == null) {
                imagesWarehouse.put(key, value);
            }
        }

        public Bitmap getImageFromWarehouse(String key) {
            synchronized (imagesWarehouse) {
                if (key != null) {
                    return imagesWarehouse.get(key);
                } else {
                    return null;
                }
            }
        }

        public void removeImageFromWarehouse(String key)
        {
            imagesWarehouse.remove(key);
        }

        public void clearCache() {
            if(imagesWarehouse != null)
            {
                imagesWarehouse.evictAll();
            }
        }
}
