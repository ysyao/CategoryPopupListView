package com.ysyao.categorypopuplistviewlibrary;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class CategoryPopupWindowListView<T extends BodyAdapterItem, V extends BodyAdapterChildItem> extends LinearLayout {
    private Context context;
    private LayoutInflater inflater;
    private View content;
    private ListView parentsList;
    private ListView childrenList;
    private BaseListAdapter<T> parentAdapter;
    private BaseListAdapter<V> childAdapter;
    private CategoryBodyAdapter<T, V> mAdapter;
    private int lastSelected = -1;
    private CategoryPopupWindowBodyDelegator<T, V> categoryPopupWindowBodyDelegator;

    public CategoryPopupWindowListView(Context context) {
        super(context);
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        initContent();
    }

    public CategoryPopupWindowListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        initContent();
    }


    public void setCategoryPopupWindowBodyDelegator(CategoryPopupWindowBodyDelegator categoryPopupWindowBodyDelegator) {
        this.categoryPopupWindowBodyDelegator = categoryPopupWindowBodyDelegator;
    }

    public void setAdapter(CategoryBodyAdapter<T, V> adapter) {
        this.mAdapter = adapter;
        this.parentAdapter = adapter.getParentAdapter();
        this.childAdapter = adapter.getChildAdapter(0);
        parentsList.setAdapter(parentAdapter);
        childrenList.setAdapter(childAdapter);
    }

    protected void initContent() {
        content = inflater.inflate(R.layout.layout_appointment_popupwindow, null);
        parentsList = (ListView) content.findViewById(R.id.listview_left);
        childrenList = (ListView) content.findViewById(R.id.listview_right);
        parentsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,final int i, long l) {
                if (lastSelected == i) {
                    return;
                }

                lastSelected = i;
                if (childAdapter == null) {
                    childAdapter = mAdapter.getChildAdapter(i);
                    childrenList.setAdapter(childAdapter);
                } else {
                    T item = (T)adapterView.getAdapter().getItem(i);
                    List<V> children = new ArrayList<V>(item.getChildrenItems());
                    childAdapter.updateAdapter(children);
                }

                if (categoryPopupWindowBodyDelegator != null) {
                    categoryPopupWindowBodyDelegator.onParentItemClicked((T)adapterView.getAdapter().getItem(i), adapterView, view, i , l);
                }
            }
        });
        childrenList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context, i + "", Toast.LENGTH_SHORT).show();
                if (categoryPopupWindowBodyDelegator != null) {
                    categoryPopupWindowBodyDelegator.onChildItemClicked((V) adapterView.getAdapter().getItem(i), adapterView, view, i, l);
                }
            }
        });
        addView(content);
    }
}
