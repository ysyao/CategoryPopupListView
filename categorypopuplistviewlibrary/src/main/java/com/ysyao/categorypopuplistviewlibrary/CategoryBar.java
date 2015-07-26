package com.ysyao.categorypopuplistviewlibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by YSYAO on 15/7/17.
 * 门类BAR
 */
public abstract class CategoryBar extends LinearLayout implements View.OnClickListener, CategoryBarHeaderDelegator{

    public interface OnCategoryBarItemClicked {
        void onItemClicked(View viewGroup, View view, int position);
    }

    private LinearLayout content;
    private OnCategoryBarItemClicked listener;
    private int lastClickedItemPosition = -1;
    public CategoryBar(Context context) {
        super(context);
        init();
    }

    public CategoryBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setOnCategroyBarItemClickedListener(OnCategoryBarItemClicked listener) {
        this.listener = listener;
    }

//    public void setPopupWindowBody(final CategoryPopupWindowBody popupWindowDelegator) {
//        this.categoryPopupWindowBody = popupWindowDelegator;
//    }

    public void setTitles(String[] titles) {
        for (int i=0; i<titles.length;i++) {
            TextView tv = new TextView(getContext());

            //设定TEXTVIE的参数
            LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            params.gravity = Gravity.CENTER;
            params.setMargins(12, 0, 12, 0);
            tv.setLayoutParams(params);
            tv.setGravity(Gravity.CENTER);

            //设置PADDING
            int padding = (int)getResources().getDimension(R.dimen.card_padding_bottom);
            tv.setPadding(0, padding, 0, padding);

            //设置文字颜色
            int color = getResources().getColor(R.color.category_bar_header_text_color);
            tv.setTextColor(color);

            tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_menu_arrow_down, 0);

            //设置文字大小
            int textSize = (int)getResources().getDimension(R.dimen.popupwindow_header_text_size);
            tv.setTextSize(textSize);

            //设置背景
//            tv.setBackgroundResource(R.drawable.bg_round_category);

            //设置HEADER
            tv.setText(titles[i]);

            //设置点击事件
            tv.setTag(i);
            tv.setOnClickListener(this);

            content.addView(tv);

            if (i == (titles.length-1)) {
                return;
            }

            //添加分割线
            View divider = new View(getContext());
            int margin = (int)getResources().getDimension(R.dimen.activity_ui_margin_medium);
            LinearLayout.LayoutParams dividerParams = new LayoutParams(1, LayoutParams.MATCH_PARENT);
            dividerParams.leftMargin = margin;
            dividerParams.rightMargin = margin;
            dividerParams.topMargin = 6;
            dividerParams.bottomMargin = 6;
            divider.setBackgroundColor(getResources().getColor(R.color.gray));
            divider.setLayoutParams(dividerParams);
            content.addView(divider);
        }
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        content = new LinearLayout(getContext());
        content.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        content.setOrientation(LinearLayout.HORIZONTAL);

        addView(content);

        View divider = new View(getContext());
        LinearLayout.LayoutParams dividerParams = new LayoutParams(LayoutParams.MATCH_PARENT, 1);
        divider.setBackgroundColor(getResources().getColor(R.color.gray));
        divider.setLayoutParams(dividerParams);
        addView(divider);
//        int padding = (int)getResources().getDimension(R.dimen.activity_ui_margin_small);
//        setPadding(0, padding, 0, padding);
//        setBackgroundColor(getResources().getColor(R.color.tool_bar_color_1));
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        if (listener != null && lastClickedItemPosition != position) {
            listener.onItemClicked(this, view, position);
        }
    }
}
