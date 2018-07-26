package com.nirmalya.enactus.nirmalya.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nirmalya.enactus.nirmalya.R;

public class ProposalViewHolder extends RecyclerView.ViewHolder {

    public View cardView;
    public TextView proposalNameView;
    public TextView numberOfMeshesView;
    public TextView numberOfPitsView;
    public TextView numberOfHomeCompostersView;
    public ImageView selectedImageView;
    public Button downloadButton;
    public Button selectProposalButton;

    public ProposalViewHolder(View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.proposal_card);
        proposalNameView = itemView.findViewById(R.id.proposal_name_textview);
        numberOfMeshesView = itemView.findViewById(R.id.number_of_meshes_textview);
        numberOfPitsView = itemView.findViewById(R.id.number_of_pits_textview);
        numberOfHomeCompostersView = itemView.findViewById(R.id.number_of_home_composters_textview);
        selectedImageView = itemView.findViewById(R.id.selected_image_view);
        downloadButton = itemView.findViewById(R.id.download_button);
        selectProposalButton = itemView.findViewById(R.id.select_proposal_button);
    }
}
