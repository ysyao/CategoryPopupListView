package com.ysyao.categorypopuplistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.ysyao.categorypopuplistviewlibrary.CategoryBar;

public class MyCategoryBar extends CategoryBar {
    public MyCategoryBar(Context context) {
        super(context);
    }

    public MyCategoryBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onShowing(View view) {
        TextView tv = (TextView) view;
        tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, com.ysyao.categorypopuplistviewlibrary.R.drawable.ic_menu_arrow_up, 0);
        tv.setTextColor(getResources().getColor(com.ysyao.categorypopuplistviewlibrary.R.color.ghc_green_color));

    }

    @Override
    public void onDismissing(View view) {
        TextView tv = (TextView) view;
        tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, com.ysyao.categorypopuplistviewlibrary.R.drawable.ic_menu_arrow_down, 0);
        tv.setTextColor(getResources().getColor(com.ysyao.categorypopuplistviewlibrary.R.color.category_bar_header_text_color));

    }
}
