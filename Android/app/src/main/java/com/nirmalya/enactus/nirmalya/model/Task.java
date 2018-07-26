package com.nirmalya.enactus.nirmalya.model;

public class Task {

    private String taskName;

    private String taskText;

    private boolean isComplete;

    public Task() {
        // Required empty constructor for Firebase
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(boolean complete) {
        isComplete = complete;
    }
}
