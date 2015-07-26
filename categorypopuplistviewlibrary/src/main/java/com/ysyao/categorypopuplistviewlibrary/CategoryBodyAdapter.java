package com.ysyao.categorypopuplistviewlibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class CategoryBodyAdapter<T extends BodyAdapterItem, V extends BodyAdapterChildItem> {
    private Context context;
    private List<T> items;
    private BaseListAdapter<T> parentAdapter;
    private BaseListAdapter<V> childAdapter;

    public CategoryBodyAdapter(Context context, List<T> items) {
        this.context = context;
        this.items = items;
    }

    public BaseListAdapter<T> getParentAdapter() {
        if (parentAdapter == null) {
            parentAdapter = new BaseListAdapter<T>(context, new ArrayList<>(items)) {

                @Override
                public View getView(int i, View view, ViewGroup viewGroup) {
                    return getParentView(getItem(i), getInflater(), i, view, viewGroup);
                }
            };
        }
        return parentAdapter;
    }

    public BaseListAdapter<V> getChildAdapter(int position) {
        if (childAdapter == null) {
            List<V> children = new ArrayList<>(items.get(position).getChildrenItems());
            childAdapter = new BaseListAdapter<V>(context, children) {
                @Override
                public View getView(int i, View view, ViewGroup viewGroup) {
                    return getChildView(getItem(i), getInflater(), i, view, viewGroup);
                }
            };
        }
        return childAdapter;
    }

    public void updateAdapter( List<T> items) {
        this.items.clear();
        this.items.addAll(items);
        parentAdapter.updateAdapter(new ArrayList<T>(items));
        childAdapter.updateAdapter(new ArrayList<V>(items.get(0).getChildrenItems()));
    }

    public T getParentItem(int position) {
        return items.get(position);
    }

    public V getChildItem(int i, int j) {
        return (V)items.get(i).getChildrenItems().get(j);
    }

    public abstract View getParentView(T t, LayoutInflater inflater, int position, View view, ViewGroup parent);
    public abstract View getChildView(V v, LayoutInflater inflater, int position, View view, ViewGroup parent);
    protected Context getContext() {
        return context;
    }
}
