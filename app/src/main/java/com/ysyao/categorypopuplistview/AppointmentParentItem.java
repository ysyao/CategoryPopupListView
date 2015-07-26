package com.ysyao.categorypopuplistview;

import com.ysyao.categorypopuplistviewlibrary.BodyAdapterItem;

import java.util.List;

public class AppointmentParentItem implements BodyAdapterItem<AppointmentChildItem> {
    private List<AppointmentChildItem> items;
    private String name;
    private int id;

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<AppointmentChildItem> getItems() {
        return items;
    }

    public void setItems(List<AppointmentChildItem> items) {
        this.items = items;
    }

    @Override
    public List<AppointmentChildItem> getChildrenItems() {
        return items;
    }

    @Override
    public int getId() {
        return id;
    }
}
