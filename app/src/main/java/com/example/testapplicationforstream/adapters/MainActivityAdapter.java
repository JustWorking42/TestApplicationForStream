package com.example.testapplicationforstream.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplicationforstream.R;
import com.example.testapplicationforstream.entity.NASAItem;
import com.example.testapplicationforstream.util.DownloadImage;
import com.example.testapplicationforstream.util.HttpClient;

import java.util.ArrayList;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder> {
    private Context context;
    private OnItemListener onItemListener;
    private LayoutInflater inflater;
    private ArrayList<NASAItem> nasaItems ;

    public MainActivityAdapter(Context context, OnItemListener onOrderListener, ArrayList<NASAItem> nasaItems) {
        this.context = context;
        this.nasaItems = nasaItems;
        this.onItemListener = onOrderListener;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_nasa, parent, false);
        return new ViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemName.setText(nasaItems.get(position).getItemName());

        DownloadImage downloadImage = new DownloadImage();
        downloadImage.execute(nasaItems.get(position).getImageUrl());
        nasaItems.get(position).setImage(downloadImage.getBmImage());

        holder.icon.setImageBitmap(nasaItems.get(position).getImage());
        Log.d("TAG", String.valueOf(nasaItems.get(position).getImage()));
    }


    @Override
    public int getItemCount() {
        return nasaItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        ImageView icon;

        public ViewHolder(@NonNull View view, final OnItemListener listener) {
            super(view);
            itemName = view.findViewById(R.id.itemName);
            icon = view.findViewById(R.id.itemImage);
            itemView.setOnClickListener(new View.OnClickListener() {
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
            });
        }
    }

    public interface OnItemListener {
        void onItemClick(int position);
    }
}
