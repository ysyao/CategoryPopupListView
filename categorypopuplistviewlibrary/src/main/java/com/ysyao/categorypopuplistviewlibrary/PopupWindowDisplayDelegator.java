package com.ysyao.categorypopuplistviewlibrary;

import android.view.View;

public interface PopupWindowDisplayDelegator {
    /**
     * 当popup window展示的时候，Activity中其他ui可能会改变，比如背景变灰色，这里设定了onShowing()来回调。
     */
    void onShowing(View view);
    /**
     * 当popup window消失的时候，Activity中其他ui可能会改变，比如背景变亮，这里设定了onDismissing()来回调。
     */
    void onDismissing(View view);
}
