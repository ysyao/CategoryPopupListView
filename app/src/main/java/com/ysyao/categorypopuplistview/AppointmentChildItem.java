package com.ysyao.categorypopuplistview;


import com.ysyao.categorypopuplistviewlibrary.BodyAdapterChildItem;

public class AppointmentChildItem implements BodyAdapterChildItem {
    private String description;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
