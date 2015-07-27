package com.ysyao.categorypopuplistviewlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
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
    public static final int DEFULT_SELECTED_VALUE = -111;

    public interface OnCategoryBarItemClicked {
        /**
         * 当category bar上面任由一项被点击的时候触发的回调函数
         * @param viewGroup 此类
         * @param view      被点击的view
         * @param position  被点击view的position
         */
        void onItemClicked(View viewGroup, View view, int position);
    }

    private LinearLayout content;
    private OnCategoryBarItemClicked listener;
    //当前被点击的position
    private int lastClickedItemPosition = 0;

    private Integer[] choosedParentIds;
    private Integer[] choosedChildIds;

    private int headerTextSize;
    private int headerTextColor;
    private Drawable headerPictureRight;

    public CategoryBar(Context context) {
        this(context, null);
    }

    public CategoryBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public void setOnCategroyBarItemClickedListener(OnCategoryBarItemClicked listener) {
        this.listener = listener;
    }

    @Override
    public void setChoosedParentId(int parentId) {
        this.choosedParentIds[lastClickedItemPosition] = parentId;
    }

    @Override
    public void setChoosedChildId(int childId) {
        this.choosedChildIds[lastClickedItemPosition] = childId;
    }

    @Override
    public int getChoosedParentId() {
        return choosedParentIds[lastClickedItemPosition];
    }

    @Override
    public int getChoosedChildId() {
        return choosedChildIds[lastClickedItemPosition];
    }

    /**
     * 实例化存储parentId,childId的数组，数组的长度由category bar的标题数来决定，默认选中值是DEFULT_SELECTED_VALUE.
     * @param length    初始化数组长度
     */
    private void initArray(int length) {
        choosedParentIds = new Integer[length];
        choosedChildIds = new Integer[length];
        for (int i=0; i<length;i++) {
            choosedParentIds[i] = DEFULT_SELECTED_VALUE;
            choosedChildIds[i] = DEFULT_SELECTED_VALUE;
        }
    }

    /**
     * 将一个标题组设置到category bar当中，通过这个数组将category bar绘制出来
     * @param titles
     */
    public void setTitles(String[] titles) {
        //实例化数组，choosedParentIds，choosedChildIds是为了存储选中的parentId和childId。
        initArray(titles.length);

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
//            int color = getResources().getColor(R.color.category_bar_header_text_color);
            tv.setTextColor(headerTextColor);

            tv.setCompoundDrawablesWithIntrinsicBounds(null, null, headerPictureRight, null);

            //设置文字大小
//            int textSize = (int)getResources().getDimension(R.dimen.popupwindow_header_text_size);
            tv.setTextSize(headerTextSize);

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

    private void init(AttributeSet attrs) {
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

        // Get the attribute values (if any).
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.CategoryBar, 0, 0);
        headerPictureRight = a.getDrawable(R.styleable.CategoryBar_headerPictureRight);
        headerTextSize = a.getDimensionPixelSize(R.styleable.CategoryBar_headerTextSize, R.dimen.popupwindow_header_text_size);
        headerTextColor = a.getColor(R.styleable.CategoryBar_headerTextColor, R.color.category_bar_header_text_color);
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        lastClickedItemPosition = position;
        if (listener != null) {
            listener.onItemClicked(this, view, position);
        }
    }
}
