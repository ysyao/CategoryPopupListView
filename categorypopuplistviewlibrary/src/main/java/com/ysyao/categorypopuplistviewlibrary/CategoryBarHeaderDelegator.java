package com.ysyao.categorypopuplistviewlibrary;

import android.view.View;

public interface CategoryBarHeaderDelegator {
    /**
     * 这个delegator是专门为了categorybar而设定的，为的是当popup window展示的时候有一个回调
     * 函数可以调用category bar上面所对应的方法，譬如当展示popup window的时候category bar上面的标题
     * 可能会用不同颜色来表示被选中，这个时候就需要在此方法下做出相对应的行为。
     * @param view 标题view，其实是TextView可以直接转型，设置图片或者修改文字颜色。
     */
    void onShowing(View view);

    /**
     * 这个delegator是专门为了categorybar而设定的，为的是当popup window消失的时候有一个回调
     * 函数可以调用category bar上面所对应的方法，譬如当popup window消失的时候category bar上面的标题
     * 可能从选中颜色变回以前的颜色，这个时候就需要在此方法下做出相对应的行为。
     * @param view 标题view，其实是TextView可以直接转型，设置图片或者修改文字颜色。
     */
    void onDismissing(View view);

    void setChoosedParentId(int parentId);
    void setChoosedChildId(int childId);
    int getChoosedParentId();
    int getChoosedChildId();
}
