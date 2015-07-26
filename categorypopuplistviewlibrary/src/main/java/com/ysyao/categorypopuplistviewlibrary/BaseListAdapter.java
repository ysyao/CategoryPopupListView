package com.ysyao.categorypopuplistviewlibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 一个根据List和view生成的adapter泛型类
 * Created by yishiyao on 2015/3/6.
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {
    private Context context;
    private List<T> items;
    private LayoutInflater inflater;

    public BaseListAdapter(Context context, List<T> items) {
        this.context = context;
        this.items = items;
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * 更新adapter，将其中的List数据换成给定数据
     * @param newItems 给定的要更新的List数据
     */
    public void updateAdapter(List<T> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return items;
    }

    protected LayoutInflater getInflater() {
        return inflater;
    }

    protected Context getApplicationContext() {
        return context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public T getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

}
