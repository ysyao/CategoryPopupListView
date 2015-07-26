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
     * ����parentAdapter��adapter�����layout���û�����ȷ��
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
     * ����child adapter,adapter�����layout���û�����ȷ��
     * @param parentViewId  ��ѡ�е�parent view id
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
     * ��չʾ������Ҫ���µ�ʱ�򣬵��ô˷���
     * @param items ���µ�����
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
     * ����parent adapterÿһ�е�layout����һ��listview��adapter��getView������ͬС�죬����ֻ�����ǽ�
     * ��ǰλ�õ�����(ĳ��POJO���͵�item)��Ϊ�������õ��˷���֮�У����û�����getItem(int position)��
     * ���̡�
     * @param t         ��ĳ��λ�ã�position)��Parent Adapter��item����
     * @param inflater  LayoutInflater
     * @param position  ��ǰλ��
     * @param view      view
     * @param parent    ��viewGroup
     * @return          listview����ÿ����λ��item
     */
    public abstract View getParentView(T t, LayoutInflater inflater, int position, View view, ViewGroup parent);

    /**
     * ����child adapterÿһ�е�layout����һ��listview��adapter��getView������ͬС�죬����ֻ�����ǽ�
     * ��ǰλ�õ�����(ĳ��POJO���͵�item)��Ϊ�������õ��˷���֮�У����û�����getItem(int position)��
     * ���̡�
     * @param v         ��ĳ��λ�ã�position)��Child Adapter��item����
     * @param inflater  LayoutInflater
     * @param position  ��ǰλ��
     * @param view      view
     * @param parent    ��viewGroup
     * @return           listview����ÿ����λ��item
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
