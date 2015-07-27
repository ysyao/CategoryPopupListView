package com.ysyao.categorypopuplistviewlibrary;


import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

public class CategoryPopupWindowBody<T extends BodyAdapterItem, V extends BodyAdapterChildItem> {
    private Activity context;
    private PopupWindow popupWindow;
    private LayoutInflater inflater;
    private View selecteItem;

    /**
     * 1.Category Bar自带接口，当adapter当初始化parentId或者childId数据时，需通过categoryBarHeaderDelegator
     *  来修改存储在其中的parentId和childId。(通过设定到adapter当中来完成)
     * 2.当adpater需要更新数据时，判断更新的数据中是否有之前选中的parentId和childId，有则根据选中parentId
     * 设定展示child数组数据，没有则初始化。(通过设定到adapter当中来完成)
     * 3.当popup window展示或者消失的时候，Category bar上标题文字颜色、图片会改变，这里设定了onShowing()
     * 和onDismissing()来回调。
     */
    private CategoryBarHeaderDelegator categoryBarHeaderDelegator;

    /**
     * 当popup window展示或者消失的时候，Activity中其他ui可能会改变，比如背景变灰色，这里设定了onShowing()
     * 和onDismissing()来回调。
     */
    private PopupWindowDisplayDelegator popupWindowDelegator;
    private CategoryPopupWindowListView categoryPopupWindowListView;

    public CategoryPopupWindowBody(Activity context, CategoryPopupWindowListView categoryPopupWindowListView) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.categoryPopupWindowListView = categoryPopupWindowListView;
        initPopupWindow(categoryPopupWindowListView);
    }

    public CategoryPopupWindowListView getCategoryPopupWindowListView() {
        return categoryPopupWindowListView;
    }

    public void show(View aboveView) {
        this.selecteItem = aboveView;
        popupWindow.showAsDropDown(aboveView);
        if (categoryBarHeaderDelegator != null) {
            categoryBarHeaderDelegator.onShowing(aboveView);
        }

        if (popupWindowDelegator != null) {
            popupWindowDelegator.onShowing(aboveView);
        }
    }

    public void dismiss() {
        if(popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    public void setCategoryBarHeaderDelegator(CategoryBarHeaderDelegator categoryBarHeaderDelegator) {
        this.categoryBarHeaderDelegator = categoryBarHeaderDelegator;
        this.categoryPopupWindowListView.setCategoryBarHeaderDelegator(categoryBarHeaderDelegator);
        setDelegators();
    }

    public void setPopupWindowDisplayDelegator(final PopupWindowDisplayDelegator popupWindowDelegator) {
        this.popupWindowDelegator = popupWindowDelegator;
        setDelegators();
    }

    private void setDelegators() {
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (popupWindowDelegator != null) {
                    popupWindowDelegator.onDismissing(selecteItem);
                }
                if (categoryBarHeaderDelegator != null) {
                    categoryBarHeaderDelegator.onDismissing(selecteItem);
                }
            }
        });
    }

    protected void initPopupWindow(CategoryPopupWindowListView content) {
        popupWindow = new PopupWindow(content, ViewGroup.LayoutParams.MATCH_PARENT,
                caculateHeightOfPopupWindow(), true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 计算整个popup window高度，动态的，设置成为屏幕高度的1/2。
     * @return  popup window高度
     */
    private int caculateHeightOfPopupWindow() {
        Display display = context.getWindowManager().getDefaultDisplay();
        return (int) (display.getHeight() * 0.5);
    }
}

