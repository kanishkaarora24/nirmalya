package com.nirmalya.enactus.nirmalya.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nirmalya.enactus.nirmalya.R;

public class TasksViewHolder extends RecyclerView.ViewHolder {

    public View taskCard;
    public Button taskCompleteButton;
    public TextView taskTextView;

    public TasksViewHolder(View itemView) {
        super(itemView);
        taskCard = itemView;
        taskCompleteButton = itemView.findViewById(R.id.task_complete_button);
        taskTextView = itemView.findViewById(R.id.task_text_textview);
    }
}
