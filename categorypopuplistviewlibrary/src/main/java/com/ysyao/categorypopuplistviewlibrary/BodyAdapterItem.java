package com.ysyao.categorypopuplistviewlibrary;

import java.util.List;

public interface BodyAdapterItem<T extends BodyAdapterChildItem> {
   List<T> getChildrenItems();
   int getId();
}
