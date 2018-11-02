package com.careeranna.careeranna.data;

public class Banner {
    private String mId,mName,mLink;

    public Banner(String id, String name, String link) {
        mId = id;
        mName = name;
        mLink = link;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmLink() {
        return mLink;
    }

    public void setmLink(String mLink) {
        this.mLink = mLink;
    }
}
