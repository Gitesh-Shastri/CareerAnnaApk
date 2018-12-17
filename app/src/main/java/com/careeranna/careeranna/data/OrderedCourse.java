package com.careeranna.careeranna.data;

import java.io.Serializable;

public class OrderedCourse implements Serializable {

    String course_id, price, image, name;

    public OrderedCourse(String course_id, String name, String price, String image) {
        this.course_id = course_id;
        this.price = price;
        this.image = image;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}