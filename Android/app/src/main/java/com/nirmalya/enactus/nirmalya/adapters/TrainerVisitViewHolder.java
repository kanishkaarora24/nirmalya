package com.nirmalya.enactus.nirmalya.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nirmalya.enactus.nirmalya.R;

public class TrainerVisitViewHolder extends RecyclerView.ViewHolder{

    public TextView visitNameTextView;
    public TextView customerNameTextView;
    public TextView customerAddressTextView;
    public TextView customerContactTextView;
    public TextView visitDateTextView;
    public TextView visitTimeTextView;
    public Button acceptVisitButton;


    public TrainerVisitViewHolder(View itemView) {
        super(itemView);
        visitNameTextView = itemView.findViewById(R.id.trainer_visit_title_textview);
        customerNameTextView = itemView.findViewById(R.id.customer_name_textview);
        customerAddressTextView = itemView.findViewById(R.id.customer_address_textview);
        customerContactTextView = itemView.findViewById(R.id.customer_contact_textview);
        visitDateTextView = itemView.findViewById(R.id.visit_date_textview);
        visitTimeTextView = itemView.findViewById(R.id.visit_time_textview);
        acceptVisitButton = itemView.findViewById(R.id.accept_visit_button);
    }
}
