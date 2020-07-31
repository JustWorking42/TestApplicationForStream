package com.example.testapplicationforstream;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.testapplicationforstream.adapters.MainActivityAdapter;
import com.example.testapplicationforstream.cashe.ImagesCache;
import com.example.testapplicationforstream.entity.NASAItem;
import com.example.testapplicationforstream.util.HttpClient;
import com.example.testapplicationforstream.util.JSONParser;
import com.example.testapplicationforstream.callbacks.LoadCallback;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity implements MainActivityAdapter.OnItemListener, LoadCallback {
    private ImageView imageView;
    private static MainActivityAdapter adapter;
    private AnimationDrawable animation;
    private ImagesCache cache = ImagesCache.getInstance();
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<NASAItem> nasaItems = new ArrayList<>();
    private  RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeActivity();
    }

    private void initializeActivity() {
        imageView = findViewById(R.id.mainActivityImage);
        animation = (AnimationDrawable)imageView.getDrawable();
        animation.start();
        cache.initializeCache();
        HttpClient httpClient = new HttpClient(this);
        httpClient.setConnection();
        nasaItems = JSONParser.getNasa();
        recyclerView = findViewById(R.id.mainActivityRecycler);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MainActivityAdapter(this, cache, this, nasaItems);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnScrollListener(scrollListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, ItemActivity.class);
        NASAItem nasaItem = nasaItems.get(position);
        intent.putExtra("nasaItem", nasaItem);
        startActivity(intent);
    }

    @Override
    public void onDataLoad() {
        adapter.setData();
        adapter.notifyDataSetChanged();
        imageView.setVisibility(View.INVISIBLE);
        animation.stop();

    }

   private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            setData();

         }
    };
    private void setData() {
        int first = linearLayoutManager.findFirstVisibleItemPosition();
        int last = linearLayoutManager.findLastVisibleItemPosition();

        HashSet<Integer> integers = new HashSet<>();

        for (int i = first; i <= last; i++) {
            RecyclerView.ViewHolder holder = recyclerView.findViewHolderForLayoutPosition(i);
            Log.d("Pos", String.valueOf(holder));
            if (holder != null) {
                Log.d("Pos", String.valueOf(holder.getLayoutPosition()));
                integers.add(holder.getLayoutPosition());
            }
        }
        adapter.setIntegers(integers);
        adapter.setData();
        adapter.notifyDataSetChanged();
    }

    public static MainActivityAdapter getAdapter() {
        return adapter;
    }
}