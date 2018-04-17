package com.guru.awdo.pojos;

import com.guru.awdo.constants.ToDoTableEntries;

/**
 * Created by Guru on 12-04-2018.
 */

public class ToDoData {


    private String mDescription,mCategory;
    private boolean mIsRecurring,mIsDone,mIsPending,mNotifyEnabled;
    private int mPriority,mID;
    private long mDeadline,mTimeStamp;

    public long getmDeadline() {
        return mDeadline;
    }

    public void setmDeadline(long mDeadline) {
        this.mDeadline = mDeadline;
    }

    public long getmTimeStamp() {
        return mTimeStamp;
    }

    public void setmTimeStamp(long mTimeStamp) {
        this.mTimeStamp = mTimeStamp;
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }


    public boolean ismIsRecurring() {
        return mIsRecurring;
    }

    public void setmIsRecurring(boolean mIsRecurring) {
        this.mIsRecurring = mIsRecurring;
    }

    public boolean ismIsDone() {
        return mIsDone;
    }

    public void setmIsDone(boolean mIsDone) {
        this.mIsDone = mIsDone;
    }

    public boolean ismIsPending() {
        return mIsPending;
    }

    public void setmIsPending(boolean mIsPending) {
        this.mIsPending = mIsPending;
    }

    public int getmPriority() {
        return mPriority;
    }

    public void setmPriority(int mPriority) {
        this.mPriority = mPriority;
    }
}
