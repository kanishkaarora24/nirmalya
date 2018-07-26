package com.nirmalya.enactus.nirmalya.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nirmalya.enactus.nirmalya.R;

public class ManufacturerOrderViewHolder extends RecyclerView.ViewHolder {

    public TextView numberOfPitsTextView;
    public TextView numberOfMeshesTextView;
    public TextView numberOfHomeCompostersTextView;
    public TextView customerAddressTextView;
    public TextView customerPhoneNumberTextView;
    public TextView customerNameTextView;
    public TextView orderNameTextView;
    public EditText estimatedDateEditText;
    public Button acceptOrderButton;

    public ManufacturerOrderViewHolder(View itemView) {
        super(itemView);
        numberOfPitsTextView = itemView.findViewById(R.id.number_of_pits_in_order_texview);
        numberOfMeshesTextView = itemView.findViewById(R.id.number_of_meshes_in_order_textview);
        numberOfHomeCompostersTextView = itemView.findViewById(R.id.number_of_composters_in_order_textview);
        customerAddressTextView = itemView.findViewById(R.id.customer_address_in_order_textview);
        customerPhoneNumberTextView = itemView.findViewById(R.id.customer_contact_in_order_textview);
        customerNameTextView = itemView.findViewById(R.id.customer_name_in_order_textview);
        estimatedDateEditText = itemView.findViewById(R.id.estimated_date_edit_text);
        acceptOrderButton = itemView.findViewById(R.id.accept_order_button);
        orderNameTextView = itemView.findViewById(R.id.order_name_textview);
    }
}
