package com.ysyao.categorypopuplistviewlibrary;

public interface BodyAdapterChildItem {
    /**
     * 此方法用于获取级联菜单当中二级菜单数据的id，当这个id设定了之后，在点击二级菜单的时候会将其值
     * 设置到回调函数当中，便于使用。
     * @return id
     */
    int getId();

    int getParentId();
}
