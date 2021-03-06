package com.ysyao.categorypopuplistview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ysyao.categorypopuplistviewlibrary.BodyAdapterChildItem;
import com.ysyao.categorypopuplistviewlibrary.BodyAdapterItem;
import com.ysyao.categorypopuplistviewlibrary.CategoryBar;
import com.ysyao.categorypopuplistviewlibrary.CategoryPopupWindowBody;
import com.ysyao.categorypopuplistviewlibrary.CategoryPopupWindowListView;
import com.ysyao.categorypopuplistviewlibrary.CategoryPopupWindowListViewDelegator;
import com.ysyao.categorypopuplistviewlibrary.PopupWindowDisplayDelegator;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements
        CategoryBar.OnCategoryBarItemClicked,
        CategoryPopupWindowListViewDelegator,
        PopupWindowDisplayDelegator {
    private MyCategoryBar mCategoryBar;
    private FrameLayout popupWindowArea;

    private CategoryPopupWindowBody<AppointmentParentItem, AppointmentChildItem> mBody;
    private CategoryPopupAdapter categoryPopupAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_appointment);
        mCategoryBar = (MyCategoryBar) findViewById(R.id.category_bar);
        popupWindowArea = (FrameLayout) findViewById(R.id.popup_window_area);

        //设置分类类别选项头
        String[] headers = {"医院", "科别", "科室"};
        mCategoryBar.setTitles(headers);
        mCategoryBar.setOnCategroyBarItemClickedListener(this);
    }

    private List<AppointmentParentItem> initItems(int param) {
        String parent;
        String child;
        switch (param) {
            case 0:
                parent = "蘑菇";
                child = "好吃";
                break;
            case 1:
                parent = "开车";
                child = "费劲";
                break;
            case 2:
                parent = "睡觉";
                child = "起不来";
                break;
            default:
                parent = "蘑菇";
                child = "好吃";
                break;
        }
        List<AppointmentParentItem> items = new ArrayList<>();
        for (int i=0;i<10;i++) {
            AppointmentParentItem item = new AppointmentParentItem();
            item.setName(parent + i);
            item.setId(param*100+i*10);
            List<AppointmentChildItem> childItems = new ArrayList<>();
            for (int j=0;j<20;j++) {
                AppointmentChildItem childItem = new AppointmentChildItem();
                childItem.setDescription(child + i + j);
                childItem.setId(param * 100 + i * 10 + j);
                childItem.setParentId(i);
                childItems.add(childItem);
            }
            item.setItems(childItems);
            items.add(item);
        }
        return items;
    }


    @Override
    public void onItemClicked(View viewGroup, View view, int position) {
        //用POPOUPWINDOW来展示级联数据
        if (mBody == null) {
            //在popup window当中的view
            CategoryPopupWindowListView categoryPopupWindowListView = new CategoryPopupWindowListView(this);
            //当popup window当中parent listview或child listview被点击的回调函数
            categoryPopupWindowListView.setCategoryPopupWindowListViewDelegator(this);

            mBody = new CategoryPopupWindowBody<>(this, categoryPopupWindowListView);
            //当popup window show or dismiss的回调函数
            mBody.setPopupWindowDisplayDelegator(this);

            //categorybar会根据popup window的展示和隐藏修改ui
            mBody.setCategoryBarHeaderDelegator(mCategoryBar);

            categoryPopupAdapter =new CategoryPopupAdapter(this, initItems(position));
            categoryPopupWindowListView.setAdapter(categoryPopupAdapter);
        } else {
            categoryPopupAdapter.updateAdapter(initItems(position));
            mBody.getCategoryPopupWindowListView().scrollToSelectedItem();
        }
        mBody.dismiss();
        mBody.show(view);
    }

    @Override
    public void onParentItemClicked(BodyAdapterItem bodyAdapterItem, AdapterView adapterView, View view, int i, long l) {
    }

    @Override
    public void onChildItemClicked(BodyAdapterChildItem bodyAdapterChildItem, AdapterView adapterView, View view, int i, long l) {
        Toast.makeText(this, "child id:" + bodyAdapterChildItem.getId(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowing(View view) {
        //将背景变成灰色
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (popupWindowArea.getForeground() == null) {
                    popupWindowArea.setForeground(getResources().getDrawable(R.drawable.bg_dim));
                }
                popupWindowArea.getForeground().setAlpha(100);
            }
        }, 100);
    }

    @Override
    public void onDismissing(View view) {
        //将背景恢复
        if (popupWindowArea.getForeground() != null) {
            popupWindowArea.getForeground().setAlpha(0);
        }
    }
}
