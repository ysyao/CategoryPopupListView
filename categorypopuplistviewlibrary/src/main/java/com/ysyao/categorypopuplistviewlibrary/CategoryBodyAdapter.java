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
    private int parentSelectedViewId = 0;
    private int childSelectedViewId = 0;

    public CategoryBodyAdapter(Context context, List<T> items) {
        this.context = context;
        this.items = items;
    }

    /**
     * 创建parentAdapter，adapter具体的layout由用户自行确定
     * @return Parent Adapter
     */
    public BaseListAdapter<T> getParentAdapter() {
        if (parentAdapter == null) {
            parentAdapter = new BaseListAdapter<T>(context, new ArrayList<>(items)) {

                @Override
                public View getView(int i, View view, ViewGroup viewGroup) {
                    return getParentView(getItem(i), getInflater(), i, view, viewGroup);
                }
            };
            parentSelectedViewId = parentAdapter.getItem(0).getId();
        }
        return parentAdapter;
    }

    /**
     * 创建child adapter,adapter具体的layout由用户自行确定
     * @param parentViewId  被选中的parent view id
     * @return              Child Adapter
     */
    public BaseListAdapter<V> getChildAdapter(int parentViewId) {
        if (childAdapter == null) {
            List<V> children = new ArrayList<>(items.get(parentViewId).getChildrenItems());
            childAdapter = new BaseListAdapter<V>(context, children) {
                @Override
                public View getView(int i, View view, ViewGroup viewGroup) {
                    return getChildView(getItem(i), getInflater(), i, view, viewGroup);
                }
            };
            childSelectedViewId = childAdapter.getItem(0).getId();
        }
        return childAdapter;
    }

    /**
     * 当展示数据需要更新的时候，调用此方法
     * @param items 更新的数据
     */
    public void updateAdapter( List<T> items) {
        this.items.clear();
        this.items.addAll(items);
        parentAdapter.updateAdapter(new ArrayList<T>(items));
        childAdapter.updateAdapter(new ArrayList<V>(items.get(0).getChildrenItems()));

        parentSelectedViewId = parentAdapter.getItem(0).getId();
        childSelectedViewId = childAdapter.getItem(0).getId();
    }

    public T getParentItem(int position) {
        return items.get(position);
    }

    public V getChildItem(int i, int j) {
        return (V)items.get(i).getChildrenItems().get(j);
    }

    /**
     * 创建parent adapter每一行的layout，与一般listview中adapter的getView方法大同小异，这里只不过是将
     * 当前位置的数据(某个POJO类型的item)作为参数设置到了方法之中，简化用户调用getItem(int position)的
     * 过程。
     * @param t         在某个位置（position)上Parent Adapter的item数据
     * @param inflater  LayoutInflater
     * @param position  当前位置
     * @param view      view
     * @param parent    根viewGroup
     * @return          listview当中每个单位的item
     */
    public abstract View getParentView(T t, LayoutInflater inflater, int position, View view, ViewGroup parent);

    /**
     * 创建child adapter每一行的layout，与一般listview中adapter的getView方法大同小异，这里只不过是将
     * 当前位置的数据(某个POJO类型的item)作为参数设置到了方法之中，简化用户调用getItem(int position)的
     * 过程。
     * @param v         在某个位置（position)上Child Adapter的item数据
     * @param inflater  LayoutInflater
     * @param position  当前位置
     * @param view      view
     * @param parent    根viewGroup
     * @return           listview当中每个单位的item
     */
    public abstract View getChildView(V v, LayoutInflater inflater, int position, View view, ViewGroup parent);
    protected Context getContext() {
        return context;
    }
    public void setParentAdapterSelectedViewId(int id) {
        this.parentSelectedViewId = id;
    }

    public int getParentSelectedViewId() {
        return parentSelectedViewId;
    }

    public void setChildAdapterSelectedViewId( int childId) {
        this.childSelectedViewId = childId;
    }

    public int getChildSelectedViewId() {
        return childSelectedViewId;
    }
}
