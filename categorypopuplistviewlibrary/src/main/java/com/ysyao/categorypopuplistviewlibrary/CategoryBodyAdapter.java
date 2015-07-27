package com.ysyao.categorypopuplistviewlibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
public abstract class CategoryBodyAdapter<T extends BodyAdapterItem, V extends BodyAdapterChildItem> {
    private Context context;
    private List<T> items;
    private BaseListAdapter<T> parentAdapter;
    private BaseListAdapter<V> childAdapter;

    /**
     * 1.Category Bar自带接口，当adapter当初始化parentId或者childId数据时，需通过categoryBarHeaderDelegator
     * 来修改存储在其中的parentId和childId。
     * 2.当adpater需要更新数据时，判断更新的数据中是否有之前选中的parentId和childId，有则根据选中parentId
     * 设定展示child数组数据，没有则初始化。
     */
    private CategoryBarHeaderDelegator categoryBarHeaderDelegator;

    public CategoryBodyAdapter(Context context, List<T> items) {
        this.context = context;
        this.items = items;
    }

    public void setCategoryBarHeaderDelegator(CategoryBarHeaderDelegator categoryBarHeaderDelegator) {
        this.categoryBarHeaderDelegator = categoryBarHeaderDelegator;
    }

    public CategoryBarHeaderDelegator getCategoryBarHeaderDelegator() {
        return categoryBarHeaderDelegator;
    }

    /**
     * 创建parentAdapter，adapter具体的layout由用户自行确定
     * @return Parent Adapter
     */
    public BaseListAdapter<T> getParentAdapter() {
        if (parentAdapter == null) {
            //返回listadapter
            parentAdapter = new BaseListAdapter<T>(context, new ArrayList<>(items)) {

                @Override
                public View getView(int i, View view, ViewGroup viewGroup) {
                    return getParentView(getItem(i), getInflater(), i, view, viewGroup);
                }
            };

            //初始化选中parentId，设定成为第一个数据
            int parentId = parentAdapter.getItem(0).getId();
            if (categoryBarHeaderDelegator != null) {
                categoryBarHeaderDelegator.setChoosedParentId(parentId);
            }
        }
        return parentAdapter;
    }

    /**
     * 创建child adapter,adapter具体的layout由用户自行确定
     * @param parentViewId  被选中的parent view id
     * @return              Child Adapter
     */
    public BaseListAdapter<V> getChildAdapter(int parentViewId) {
        if (childAdapter == null) {
            List<V> children = new ArrayList<>(items.get(parentViewId).getChildrenItems());
            childAdapter = new BaseListAdapter<V>(context, children) {
                @Override
                public View getView(int i, View view, ViewGroup viewGroup) {
                    return getChildView(getItem(i), getInflater(), i, view, viewGroup);
                }
            };
            //初始化选中childId，设定成为第一个数据
            int childId = childAdapter.getItem(0).getId();
            if (categoryBarHeaderDelegator != null) {
                categoryBarHeaderDelegator.setChoosedChildId(childId);
            }
        }
        return childAdapter;
    }

    /**
     * 当展示数据需要更新的时候，调用此方法
     * @param items 更新的数据
     */
    public void updateAdapter(List<T> items) {
        this.items.clear();
        this.items.addAll(items);
        parentAdapter.updateAdapter(new ArrayList<T>(items));

        //在adapter切换数据的时候，需要通过categoryBarHeaderDelegator来查看是否已经存储了parentId和
        //childId，如果有，则将存储的id设置为选中id。
        int index = 0;
        for (T item : items) {
            //查询选中的parentId
            int parentId = categoryBarHeaderDelegator.getChoosedParentId();
            //如果这里还没有设置选中parentId，则将id设定为items当中的第一个
            if (parentId == CategoryBar.DEFULT_SELECTED_VALUE) {
                categoryBarHeaderDelegator.setChoosedParentId(items.get(0).getId());
                V v = (V)items.get(0).getChildrenItems().get(0);
                categoryBarHeaderDelegator.setChoosedChildId(v.getId());
            }

            //在items当中找到与选中id匹配的id。
            if (item.getId() == categoryBarHeaderDelegator.getChoosedParentId()) {
                index = items.indexOf(item);
            }
        }

        //更新childadapter当中的数据
        childAdapter.updateAdapter(new ArrayList<V>(items.get(index).getChildrenItems()));
    }

    /**
     * 创建parent adapter每一行的layout，与一般listview中adapter的getView方法大同小异，这里只不过是将
     * 当前位置的数据(某个POJO类型的item)作为参数设置到了方法之中，简化用户调用getItem(int position)的
     * 过程。
     * @param t         在某个位置（position)上Parent Adapter的item数据
     * @param inflater  LayoutInflater
     * @param position  当前位置
     * @param view      view
     * @param parent    根viewGroup
     * @return          listview当中每个单位的item
     */
    public abstract View getParentView(T t, LayoutInflater inflater, int position, View view, ViewGroup parent);

    /**
     * 创建child adapter每一行的layout，与一般listview中adapter的getView方法大同小异，这里只不过是将
     * 当前位置的数据(某个POJO类型的item)作为参数设置到了方法之中，简化用户调用getItem(int position)的
     * 过程。
     * @param v         在某个位置（position)上Child Adapter的item数据
     * @param inflater  LayoutInflater
     * @param position  当前位置
     * @param view      view
     * @param parent    根viewGroup
     * @return           listview当中每个单位的item
     */
    public abstract View getChildView(V v, LayoutInflater inflater, int position, View view, ViewGroup parent);
    protected Context getContext() {
        return context;
    }
}

