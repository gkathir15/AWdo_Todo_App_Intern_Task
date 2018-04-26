package com.guru.awdo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guru.awdo.R;
import com.guru.awdo.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guru on 26-04-2018.
 */
public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder> {

    List<String> mCategoryList;
    int mCategoryViewItem;
    OnItemClickListener mOnItemClickListener;

    public CategoryRecyclerAdapter(List<String> mCategoryList, int mCategoryViewItem) {
        this.mCategoryList = mCategoryList;
        this.mCategoryViewItem = mCategoryViewItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context lContext = parent.getContext();
        LayoutInflater lLayoutInflater = LayoutInflater.from(lContext);
        View lCategoryList = lLayoutInflater.inflate(mCategoryViewItem,parent,false);

        ViewHolder lViewHolder = new ViewHolder(lCategoryList);



        return lViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String lCategory = mCategoryList.get(position);
        TextView lTitleTv = holder.mTitleTv;
        lTitleTv.setText(lCategory);

    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    public void setClickListener(OnItemClickListener pOnItemClickListener)
    {
        this.mOnItemClickListener = pOnItemClickListener;

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements OnItemClickListener,View.OnClickListener {

        TextView mTitleTv,mCountTv;

        public ViewHolder(View itemView) {
            super(itemView);

            mTitleTv = itemView.findViewById(R.id.category_name);
            mCountTv = itemView.findViewById(R.id.count);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            mOnItemClickListener.onClick(v,getAdapterPosition());

        }

        @Override
        public void onClick(View View, int Position) {
            mOnItemClickListener.onClick(View,Position);

        }
    }

}
