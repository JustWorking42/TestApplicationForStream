package com.example.testapplicationforstream;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testapplicationforstream.adapters.MainActivityAdapter;
import com.example.testapplicationforstream.callbacks.ImageLoadSingleCallback;
import com.example.testapplicationforstream.cashe.ImagesCache;
import com.example.testapplicationforstream.util.SingleDownload;
import com.example.testapplicationforstream.entity.NASAItem;

public class ItemActivity extends AppCompatActivity implements ImageLoadSingleCallback {
    private TextView description;
    private TextView itemName;
    private ImageView icon;
    private NASAItem nasaItem;
    private MainActivityAdapter adapter;
    private ImagesCache cache = ImagesCache.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        initializationActivity();
    }

    private void initializationActivity() {
        adapter = MainActivity.getAdapter();
        nasaItem = getIntent().getParcelableExtra("nasaItem");
        description = findViewById(R.id.itemDescriptionActivity);
        icon = findViewById(R.id.itemImageActivity);
        itemName = findViewById(R.id.itemNameActivity);
        setData();
    }

    private void setData() {
        Bitmap bitmap = cache.getImageFromWarehouse(nasaItem.getImageUrl());
        description.setText(nasaItem.getItemDescription());
        itemName.setText(nasaItem.getItemName());
        if (bitmap == null) {
            SingleDownload singleDownload = new SingleDownload(cache,
                    200, 200, this);
            singleDownload.execute(nasaItem.getImageUrl());
        } else {
            icon.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onDownload() {
        Log.d("OK", "DOWN");
        nasaItem.setImage(cache.getImageFromWarehouse(nasaItem.getImageUrl()));
        icon.setImageBitmap(nasaItem.getImage());
        adapter.notifyDataSetChanged();
    }
}