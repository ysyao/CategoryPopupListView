package com.ysyao.categorypopuplistviewlibrary;

import android.view.View;
import android.widget.AdapterView;

public interface CategoryPopupWindowBodyDelegator<T extends BodyAdapterItem, V extends BodyAdapterChildItem> {
    void onParentItemClicked(T t, AdapterView<?> adapterView, View view, int i, long l);
    void onChildItemClicked(V v, AdapterView<?> adapterView, View view, int i, long l);
}
