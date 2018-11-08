package com.careeranna.careeranna.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.careeranna.careeranna.R;
import com.careeranna.careeranna.data.Topic;
import com.careeranna.careeranna.data.Unit;

import java.util.ArrayList;

public class ExpandableList_Adapter extends BaseExpandableListAdapter {
    private Context mContext;
    private ArrayList<Unit> unit;
    private LayoutInflater layoutInflater;

    public ExpandableList_Adapter(Context mContext, ArrayList<Unit> unit) {
        this.mContext = mContext;
        this.unit = unit;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return unit.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return unit.get(i).topics.size();
    }

    @Override
    public Unit getGroup(int i) {
        return unit.get(i);
    }

    @Override
    public Topic getChild(int i, int i1) {
        return unit.get(i).topics.get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = getGroup(i).Name;
        Drawable icon = getGroup(i).getIcon();
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater)this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_unit_group, null);
        }
        TextView listHeader = (TextView) view.findViewById(R.id.header);
        ImageView imageView = view.findViewById(R.id.imageView);
        listHeader.setTypeface(null, Typeface.BOLD);
        listHeader.setText(headerTitle);
        imageView.setImageDrawable(icon);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = layoutInflater.inflate(R.layout.list_group_item, null);
        }
        String childText = getChild(i, i1).getName();
        Drawable icon = getChild(i,i1).getIcon();
        ImageView listChildImage = view.findViewById(R.id.imageView);
        TextView listChild = (TextView) view.findViewById(R.id.headeritem);
        listChild.setText(childText);
        listChildImage.setImageDrawable(icon);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
