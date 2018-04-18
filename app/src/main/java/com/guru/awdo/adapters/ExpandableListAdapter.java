package com.guru.awdo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.guru.awdo.R;
import com.guru.awdo.helpers.CommonHelper;
import com.guru.awdo.pojos.ToDoData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Guru on 13-04-2018.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    List<ToDoData> mUpcomingList = new ArrayList<>();
    List<ToDoData> mTodayList = new ArrayList<>();
    List<ToDoData> mTomorrowList = new ArrayList<>();
    HashMap<Integer, List<ToDoData>> mMapOfChildList;
    List<String> mParentTitle = new ArrayList<>();

    public ExpandableListAdapter(Context mContext, HashMap<Integer, List<ToDoData>> mMapOfChildList, List<String> mParentTitle) {
        this.mContext = mContext;
        this.mMapOfChildList = mMapOfChildList;
        this.mParentTitle = mParentTitle;
    }

    @Override
    public int getGroupCount() {
       // Log.d("adapter"," "+mParentTitle.size());
      return mParentTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        Log.d("adapter child "," "+ mMapOfChildList.get(mParentTitle.get(groupPosition)).size());
        return mMapOfChildList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {

        return mParentTitle.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return mMapOfChildList.get(mParentTitle.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String lParentTitle = (String) getGroup(groupPosition);
        if(convertView == null)
        {
            LayoutInflater lLayoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            convertView = lLayoutInflater.inflate(R.layout.parent_list,null);

        }
        TextView lParentTitleTv = convertView.findViewById(R.id.parent_title);
        lParentTitleTv.setText(lParentTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        CommonHelper lCommonHelper = new CommonHelper();
        ToDoData lTodoData  = mMapOfChildList.get(groupPosition).get(childPosition);
                //(ToDoData) getChild(groupPosition,childPosition);
        if (convertView == null)
        {
            LayoutInflater lLayoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            convertView = lLayoutInflater.inflate(R.layout.child_list,null);

        }
        TextView lChildTodoDescTv = convertView.findViewById(R.id.child_description);
        TextView lChildTodoDeadlineTv = convertView.findViewById(R.id.child_deadline);
        lChildTodoDescTv.setText(lTodoData.getmDescription());
        lChildTodoDeadlineTv.setText(lCommonHelper.longDateToFormattedDate(lTodoData.getmDeadline()));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
