package com.exchange.almulla.adapter;

import java.io.Serializable;

public class ToDoPOJO implements Serializable {
    String mTitle = "";

    public String getMdate() {
        return mdate;
    }

    public void setMdate(String mdate) {
        this.mdate = mdate;
    }

    String mdate;

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

}
