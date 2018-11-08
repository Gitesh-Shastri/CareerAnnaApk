package com.careeranna.careeranna.data;

import android.graphics.drawable.Drawable;

import com.careeranna.careeranna.R;

public class Topic {

    private String Name;
    private Drawable icon;

    public Topic(String name, Drawable icon) {
        Name = name;
        this.icon = icon;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
