package com.ysyao.categorypopuplistviewlibrary;

import java.util.List;

public interface BodyAdapterItem<T extends BodyAdapterChildItem> {
   /**
    * 用于获取当前一级菜单选中值所对应的所有二级菜单数据。
    * @return 二级菜单数据POJO
    */
   List<T> getChildrenItems();
   /**
    * 此方法用于获取级联菜单当中一级菜单数据的id，当这个id设定了之后，在点击一级菜单的时候会将其值
    * 设置到回调函数当中，便于使用。
    * @return id
    */
   int getId();
}
