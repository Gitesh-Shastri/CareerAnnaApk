package com.careeranna.careeranna.data;

public class Topic {

    private String Name;
    private boolean isComplete;

    public Topic(String name, boolean isComplete) {
        Name = name;
        this.isComplete = isComplete;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}
