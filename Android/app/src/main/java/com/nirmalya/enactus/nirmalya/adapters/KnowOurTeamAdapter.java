package com.nirmalya.enactus.nirmalya.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nirmalya.enactus.nirmalya.R;
import com.nirmalya.enactus.nirmalya.model.KnowTeamItem;

import java.util.ArrayList;

public class KnowOurTeamAdapter extends RecyclerView.Adapter<KnowOurTeamAdapter.viewholder> {

    private Context context;
    private ArrayList<KnowTeamItem> knowteam;
    private ArrayList<Drawable> knowteamImages;

    public KnowOurTeamAdapter(Context context, ArrayList<KnowTeamItem> knowteam, ArrayList<Drawable> knowteamImages) {
        this.context = context;
        this.knowteam = knowteam;
        this.knowteamImages = knowteamImages;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewholder(LayoutInflater.from(context).inflate(R.layout.team_details_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        KnowTeamItem TeamItem = knowteam.get(position);
        holder.mDesignation.setText(TeamItem.getdesignation());
        holder.mName.setText(TeamItem.getname());
        holder.mNumber.setText(TeamItem.getnumber());
        holder.mEmail.setText(TeamItem.getemail());
        Glide
                .with(context)
                .load(knowteamImages.get(position))
                .into(holder.mImage);
        holder.setEmailAndPhoneIntents();
        }

    @Override
    public int getItemCount() {
        return knowteam.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        View mView;
        TextView mDesignation, mName, mNumber, mEmail;
        ImageView mImage;

        public viewholder(View itemView) {
            super(itemView);
            mView = itemView;
            mDesignation = itemView.findViewById(R.id.designation_of_the_member);
            mName = itemView.findViewById(R.id.name_of_the_member);
            mNumber = itemView.findViewById(R.id.phone_of_the_member);
            mEmail = itemView.findViewById(R.id.email_of_the_member);
            mImage = itemView.findViewById(R.id.image_of_the_member);
        }

        private void setEmailAndPhoneIntents() {
            mEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {knowteam.get(getAdapterPosition()).getemail()});
                    context.startActivity(emailIntent);
                }
            });
            mNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", knowteam.get(getAdapterPosition()).getnumber(), null));
                    context.startActivity(phoneIntent);
                }
            });
        }
    }
}
