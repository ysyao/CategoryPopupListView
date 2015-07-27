package com.ysyao.categorypopuplistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ysyao.categorypopuplistviewlibrary.CategoryBodyAdapter;

import java.util.List;
public class CategoryPopupAdapter extends CategoryBodyAdapter<AppointmentParentItem, AppointmentChildItem> {

    class ParentViewHolder {
        TextView description;
    }
    class ChildViewHolder {
        TextView name;
    }


    public CategoryPopupAdapter(Context context, List<AppointmentParentItem> items) {
        super(context, items);
    }

    @Override
    public View getParentView(AppointmentParentItem appointmentParentItem, LayoutInflater inflater, int position, View view, ViewGroup parent) {
        ParentViewHolder holder;
        if (view == null) {
            holder = new ParentViewHolder();
            view = inflater.inflate(R.layout.layout_category_adapter_parent, parent, false);
            holder.description = (TextView)view.findViewById(R.id.description);
            view.setTag(holder);
        } else {
            holder = (ParentViewHolder) view.getTag();
        }
        holder.description.setText(appointmentParentItem.getName());
        if (getCategoryBarHeaderDelegator().getChoosedParentId() == appointmentParentItem.getId()) {
            holder.description.setTextColor(getContext().getResources().getColor(R.color.ghc_green_color));
            view.setBackgroundColor(getContext().getResources().getColor(R.color.tool_bar_color));
        } else {
            holder.description.setTextColor(getContext().getResources().getColor(R.color.category_bar_header_text_color));
            view.setBackgroundResource(R.drawable.selector_category_body);
        }
        return view;
    }

    @Override
    public View getChildView(AppointmentChildItem appointmentChildItem, LayoutInflater inflater, int position, View view, ViewGroup parent) {
        ChildViewHolder holder;
        if (view == null) {
            holder = new ChildViewHolder();
            view = inflater.inflate(R.layout.layout_category_adapter_child, parent, false);
            holder.name = (TextView)view.findViewById(R.id.description);
            view.setTag(holder);
        } else {
            holder = (ChildViewHolder) view.getTag();
        }
        holder.name.setText(appointmentChildItem.getDescription());
        if (getCategoryBarHeaderDelegator().getChoosedChildId() == appointmentChildItem.getId()) {
            holder.name.setTextColor(getContext().getResources().getColor(R.color.ghc_green_color));
        } else {
            holder.name.setTextColor(getContext().getResources().getColor(android.R.color.tertiary_text_light));
        }
        return view;
    }


}