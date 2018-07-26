package com.nirmalya.enactus.nirmalya.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.nirmalya.enactus.nirmalya.R;
import com.nirmalya.enactus.nirmalya.activities.AuthenticateActivity;
import com.nirmalya.enactus.nirmalya.activities.KnowOurTeam;
import com.nirmalya.enactus.nirmalya.activities.ManufacturerActivity;
import com.nirmalya.enactus.nirmalya.activities.OrderActivity;
import com.nirmalya.enactus.nirmalya.activities.OurImpact;
import com.nirmalya.enactus.nirmalya.activities.OurResources;
import com.nirmalya.enactus.nirmalya.activities.ProductsAndServicesActivity;
import com.nirmalya.enactus.nirmalya.activities.TrainerActivity;
import com.nirmalya.enactus.nirmalya.model.ServiceItem;
import com.nirmalya.enactus.nirmalya.utilities.Constants;

import java.util.ArrayList;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ServiceItem> mItems = new ArrayList<>();
    private ArrayList<Integer> mBackgrounds = new ArrayList<>();

    private SharedPreferences sharedPreferences;

    public ServicesAdapter (Context context, ArrayList<ServiceItem> items, ArrayList<Integer> backgrounds) {
        mContext = context;
        mItems = items;
        mBackgrounds = backgrounds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.service_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServiceItem serviceItem = mItems.get(position);
        holder.mTitleView.setText(serviceItem.getTitle());
        holder.mSubtitleView.setText(serviceItem.getSubtitle());
        Glide
                .with(mContext)
                .load(mBackgrounds.get(position))
                .into(holder.mImageView);
        
        if (serviceItem.getTitle().equals("Orders")) {
            holder.mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                        Toast.makeText(mContext, "Please login first!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(mContext, AuthenticateActivity.class);
                        mContext.startActivity(intent);
                    } else {
                        sharedPreferences = mContext.getSharedPreferences(Constants.SHARED_PREFS, 0);
                        String accountType = sharedPreferences.getString(Constants.SHARED_PREFS_ACCOUNT_TYPE, "customer");
                        Intent intent = new Intent();
                        if (accountType.equals("customer")) {
                            intent = new Intent(mContext, OrderActivity.class);
                        } else if (accountType.equals("manufacturer")) {
                            intent = new Intent(mContext, ManufacturerActivity.class);
                        } else if (accountType.equals("trainer")) {
                            intent = new Intent(mContext, TrainerActivity.class);
                        }
                        mContext.startActivity(intent);

                    }
                }
            });
        }

        if (serviceItem.getTitle().equals("Our Products")) {
            holder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ProductsAndServicesActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }

        if (serviceItem.getTitle().equals("Our Impact")) {
            holder.mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, OurImpact.class);
                    mContext.startActivity(intent);
                }
            });
        }
        if (serviceItem.getTitle().equals("Our Team")) {
            holder.mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, KnowOurTeam.class);
                    mContext.startActivity(intent);
                }
            });
        }
        if (serviceItem.getTitle().equals("Our Resources")) {
            holder.mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, OurResources.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public View mItemView;
        public ImageView mImageView;
        public TextView mTitleView;
        public TextView mSubtitleView;

        public ViewHolder(View itemView) {

            super(itemView);
            mItemView = itemView;
            mImageView = itemView.findViewById(R.id.service_item_image);
            mTitleView = itemView.findViewById(R.id.service_item_title);
            mSubtitleView = itemView.findViewById(R.id.service_item_subtitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Those categories will be added soon!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}
