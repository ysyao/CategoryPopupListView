package com.ysyao.categorypopuplistviewlibrary;

import android.view.View;
import android.widget.AdapterView;

public interface CategoryPopupWindowListViewDelegator<T extends BodyAdapterItem, V extends BodyAdapterChildItem> {
    /**
     * 当Parent View Item被点击的时候，调用此函数
     * @param t             被点击Parent Item数值
     * @param adapterView   被点击adapter
     * @param view          被点击的view
     * @param i             被点击item的位置
     * @param l             被点击item的id
     */
    void onParentItemClicked(T t, AdapterView<?> adapterView, View view, int i, long l);
    /**
     * 当Child View Item被点击的时候，调用此函数
     * @param v             被点击Parent Item数值
     * @param adapterView   被点击adapter
     * @param view          被点击的view
     * @param i             被点击item的位置
     * @param l             被点击item的id
     */
    void onChildItemClicked(V v, AdapterView<?> adapterView, View view, int i, long l);
}
