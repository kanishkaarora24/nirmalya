package com.nirmalya.enactus.nirmalya.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.model.Image;
import com.nirmalya.enactus.nirmalya.R;

import java.util.List;

public class QueryImagesAdapter extends RecyclerView.Adapter<QueryImagesAdapter.ViewHolder> {

    private Context mContext;
    private List<Image> mImages;

    public QueryImagesAdapter(Context context, List<Image> images) {
        this.mContext = context;
        this.mImages = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.query_image_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide
                .with(mContext)
                .load(mImages.get(position).getPath())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.query_item_imageview);
        }
    }

}
