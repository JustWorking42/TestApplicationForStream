package com.example.testapplicationforstream.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplicationforstream.callbacks.ImageLoadCallback;
import com.example.testapplicationforstream.R;
import com.example.testapplicationforstream.util.DownloadImageTask;
import com.example.testapplicationforstream.cashe.ImagesCache;
import com.example.testapplicationforstream.entity.NASAItem;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder>
        implements ImageLoadCallback {

    private OnItemListener onItemListener;
    private LayoutInflater inflater;
    private ArrayList<NASAItem> nasaItems;
    private ImagesCache cache;
    private HashSet<Integer> integers = new HashSet<>();
    private  boolean isLoaded = true;


    public MainActivityAdapter(Context context,
                               ImagesCache cache,
                               OnItemListener onOrderListener, ArrayList<NASAItem> nasaItems) {
        this.cache = cache;
        this.nasaItems = nasaItems;
        this.onItemListener = onOrderListener;
        inflater = LayoutInflater.from(context);
        onCreate();
    }
    private void onCreate() {
        for (int i = 0; i < 10; i++ ) {
            integers.add(i);
        }
        Log.d("ONCREATE", "ON");
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_nasa, parent, false);

        return new ViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bitmap bitmap = nasaItems.get(position).getImage();
        holder.itemName.setText(nasaItems.get(position).getItemName());
        if (bitmap != null) {
            holder.icon.setImageBitmap(bitmap);
            holder.icon.setVisibility(View.VISIBLE);
        } else {
            holder.icon.setVisibility(View.GONE);
        }
    }

    public void setData() {
        Log.d("getImage", String.valueOf(integers.size()));
            for (int n : integers) {
                String img = nasaItems.get(n).getImageUrl();
                Bitmap bm = cache.getImageFromWarehouse(img);
                if (bm == null) {
                    if (isLoaded) {
                        DownloadImageTask imgTask = new DownloadImageTask(cache,
                                250, 250, this);
                        imgTask.execute(img);
                        isLoaded = false;
                        Log.d("getImage", "START");
                    }
                } else {
                    Log.d("getImage", "ELSE");
                    nasaItems.get(n).setImage(bm);
                }
            }
    }

    @Override
    public int getItemCount() {
        return nasaItems.size();
    }

    @Override
    public void onImageLoad() {
        notifyDataSetChanged();
        isLoaded = true;
        setData();
    }

    public HashSet<Integer> getIntegers() {
        return integers;
    }

    public void setIntegers(HashSet<Integer> integers) {
        this.integers = integers;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName;
        private ImageView icon;
        private OnItemListener listener;

        public ViewHolder(@NonNull View view, OnItemListener listener) {
            super(view);
            this.listener = listener;
            itemName = view.findViewById(R.id.itemName);
            icon = view.findViewById(R.id.itemImage);
            itemView.setOnClickListener(itemListener);
            setData();
        }

        private View.OnClickListener itemListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                        notifyDataSetChanged();
                    }
                }
            }
        };
    }


    public interface OnItemListener {
        void onItemClick(int position);
    }
}
