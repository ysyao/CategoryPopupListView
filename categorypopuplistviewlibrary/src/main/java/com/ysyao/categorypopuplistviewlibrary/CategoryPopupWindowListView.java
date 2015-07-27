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
    private CategoryPopupWindowListViewDelegator<T, V> categoryPopupWindowListViewDelegator;
    private CategoryBarHeaderDelegator categoryBarHeaderDelegator;

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

    public void setCategoryPopupWindowListViewDelegator(CategoryPopupWindowListViewDelegator categoryPopupWindowListViewDelegator) {
        this.categoryPopupWindowListViewDelegator = categoryPopupWindowListViewDelegator;
    }

    public void setCategoryBarHeaderDelegator(CategoryBarHeaderDelegator categoryBarHeaderDelegator) {
        this.categoryBarHeaderDelegator = categoryBarHeaderDelegator;
        if (mAdapter != null) {
            this.mAdapter.setCategoryBarHeaderDelegator(categoryBarHeaderDelegator);
        }
    }

    public void scrollToSelectedItem() {
        int parentId = categoryBarHeaderDelegator.getChoosedParentId();
        int parentPosition = 0;
        List<T> parentItems = mAdapter.getParentAdapter().getDatas();
        for (T parentItem : parentItems) {
            if (parentItem.getId() == parentId) {
                parentPosition = parentItems.indexOf(parentItem);
            }
        }
        parentsList.smoothScrollToPosition(parentPosition);

        int childId = categoryBarHeaderDelegator.getChoosedChildId();
        int childPosition = 0;
        List<V> childItems = mAdapter.getChildAdapter(parentPosition).getDatas();
        for (V childItem : childItems) {
            if (childItem.getId() == childId) {
                childPosition = childItems.indexOf(childItem);
            }
        }
        childrenList.smoothScrollToPosition(childPosition);
    }

    public void setAdapter(CategoryBodyAdapter<T, V> adapter) {
        this.mAdapter = adapter;
        if (categoryBarHeaderDelegator != null) {
            this.mAdapter.setCategoryBarHeaderDelegator(categoryBarHeaderDelegator);
        }
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
                T item = (T)adapterView.getAdapter().getItem(i);
                if (lastSelected == item.getId()) {
                    return;
                }

                lastSelected = item.getId();
                if (childAdapter == null) {
                    childAdapter = mAdapter.getChildAdapter(i);
                    childrenList.setAdapter(childAdapter);
                } else {
                    List<V> children = new ArrayList<V>(item.getChildrenItems());
                    childAdapter.updateAdapter(children);
                }

                mAdapter.getCategoryBarHeaderDelegator().setChoosedParentId(item.getId());
                V child = (V)item.getChildrenItems().get(0);
                mAdapter.getCategoryBarHeaderDelegator().setChoosedChildId(child.getId());
                mAdapter.getParentAdapter().notifyDataSetChanged();
                if (categoryPopupWindowListViewDelegator != null) {
                    categoryPopupWindowListViewDelegator.onParentItemClicked(item, adapterView, view, i , l);
                }
                scrollToSelectedItem();
            }
        });
        childrenList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                V item = (V)adapterView.getAdapter().getItem(i);

                if (categoryPopupWindowListViewDelegator != null) {
                    categoryPopupWindowListViewDelegator.onChildItemClicked(item, adapterView, view, i, l);
                }
                if (categoryBarHeaderDelegator != null) {
                    mAdapter.getCategoryBarHeaderDelegator().setChoosedChildId(item.getId());
                }

                mAdapter.getCategoryBarHeaderDelegator().setChoosedChildId(item.getId());
                mAdapter.getChildAdapter(mAdapter.getCategoryBarHeaderDelegator().getChoosedParentId()).notifyDataSetChanged();
            }
        });
        addView(content);
    }
}

