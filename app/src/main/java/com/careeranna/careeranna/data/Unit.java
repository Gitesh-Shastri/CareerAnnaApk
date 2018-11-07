package com.careeranna.careeranna.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Unit implements Serializable {

    public String Name;
    public ArrayList<Topic> topics = new ArrayList<>();

    public Unit(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return Name;
    }
}
