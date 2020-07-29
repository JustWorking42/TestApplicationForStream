package com.example.testapplicationforstream;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.testapplicationforstream.adapters.MainActivityAdapter;
import com.example.testapplicationforstream.entity.NASAItem;
import com.example.testapplicationforstream.util.HttpClient;
import com.example.testapplicationforstream.util.JSONParser;
import com.example.testapplicationforstream.util.LoadCallback;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainActivityAdapter.OnItemListener, LoadCallback {
    private RecyclerView recyclerView;
    private MainActivityAdapter adapter;
    private ArrayList<NASAItem> nasaItems = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeActivity();
    }
    private void initializeActivity() {
        HttpClient httpClient = new HttpClient();
        httpClient.setConnection();
        setData();
        recyclerView = findViewById(R.id.mainActivityRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MainActivityAdapter(this, this, nasaItems);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void setData() {
        nasaItems = JSONParser.getNasa();
        Log.d("TAG", nasaItems.size() + " size");
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onDataLoad() {
        adapter.notifyDataSetChanged();
    }
}