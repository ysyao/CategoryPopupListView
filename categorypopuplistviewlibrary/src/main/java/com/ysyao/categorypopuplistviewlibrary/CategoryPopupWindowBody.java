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

    private CategoryBarHeaderDelegator categoryBarHeaderDelegator;
    private PopupWindowDisplayDelegator popupWindowDelegator;

    public CategoryPopupWindowBody(Activity context, View content) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        initPopupWindow(content);
//        initDialog();
    }

    public void show(View aboveView) {
        this.selecteItem = aboveView;
        popupWindow.showAsDropDown(aboveView);
//        dialog.show();

        if (popupWindowDelegator != null) {
            popupWindowDelegator.onShowing(aboveView);
        }

        if (categoryBarHeaderDelegator != null) {
            categoryBarHeaderDelegator.onShowing(aboveView);
        }
    }

    public void dismiss() {
        if(popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }


    }

    public void setCategoryBarHeaderDelegator(CategoryBarHeaderDelegator categoryBarHeaderDelegator) {
        this.categoryBarHeaderDelegator = categoryBarHeaderDelegator;
        setDelegators();
    }

    public void setPopupWindowDelegator(final PopupWindowDisplayDelegator popupWindowDelegator) {
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

    protected void initPopupWindow(View content) {
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

    private int caculateHeightOfPopupWindow() {
        Display display = context.getWindowManager().getDefaultDisplay();
        return (int) (display.getHeight() * 0.5);
    }
}
