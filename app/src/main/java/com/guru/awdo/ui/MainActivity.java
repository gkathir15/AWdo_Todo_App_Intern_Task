package com.guru.awdo.ui;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import com.facebook.stetho.Stetho;
import com.guru.awdo.R;
import com.guru.awdo.adapters.CategoryRecyclerAdapter;
import com.guru.awdo.adapters.ExpandableListAdapter;
import com.guru.awdo.constants.ToDoTableEntries;
import com.guru.awdo.db.ToDoTableHelper;
import com.guru.awdo.interfaces.OnItemClickListener;
import com.guru.awdo.pojos.ToDoData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    ExpandableListView mExpandableListView;
    HashMap<Integer,List<ToDoData>> mExpandableListParentData= new HashMap();
    ExpandableListAdapter mExpandableListAdapter;
    List<ToDoData> mUpcomingList = new ArrayList<>();
    List<ToDoData> mTomorrowList = new ArrayList<>();
    List<ToDoData> mTodayList = new ArrayList<>();
    List<ToDoData> mLaterList = new ArrayList<>();
    List<String> mParentTitles = new ArrayList<>();
    List<String> mCategoryTitleList = new ArrayList<>();
    Calendar mCalendar;
    RecyclerView mCategoryRecyclerView;
    CategoryRecyclerAdapter mCategoryRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Stetho.initializeWithDefaults(this);

        mParentTitles.add("Upcoming");
        mParentTitles.add("Today");
        mParentTitles.add("Tomorrow");
        mParentTitles.add("Later");

        mExpandableListView = findViewById(R.id.parent_expandable_list);
        mCategoryRecyclerView = findViewById(R.id.category_recycler);
       // mExpandableListParentData = new ExpandableListAdapter()

        FloatingActionButton lAddTodoFAB = findViewById(R.id.add_todo_fab);
        lAddTodoFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lIntent = new Intent(getApplicationContext(),AddToDoActivity.class);
                startActivity(lIntent);
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });



        mExpandableListParentData.put(0, mUpcomingList);
        mExpandableListParentData.put(1, mTodayList);
        mExpandableListParentData.put(2, mTomorrowList);
        mExpandableListParentData.put(3,mLaterList);


        mExpandableListAdapter = new ExpandableListAdapter(this,mExpandableListParentData,mParentTitles);
        mCategoryRecyclerAdapter = new CategoryRecyclerAdapter(mCategoryTitleList,R.layout.category_views);
        mExpandableListView.setAdapter(mExpandableListAdapter);
        mCategoryRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mCategoryRecyclerView.setAdapter(mCategoryRecyclerAdapter);
        mCategoryRecyclerAdapter.setClickListener(this);
        //mExpandableListView.animate();
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                ToDoData lTodoData = mExpandableListParentData.get(groupPosition).get(childPosition);

                Intent lIntent = new Intent(getApplicationContext(),EditToDoActivity.class);
                lIntent.putExtra("TodoData",lTodoData);
                startActivity(lIntent);
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);

                return true;
            }
        });



        }

    @Override
    protected void onResume() {
        super.onResume();
        new FetchTodoTasks().execute();
    }

    @Override
    public void onClick(View View, int Position) {

        Intent lIntent = new Intent(MainActivity.this,TaskByCategoryActivity.class);
        lIntent.putExtra("category",mCategoryTitleList.get(Position));
        startActivity(lIntent);

    }

    class FetchTodoTasks extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {

            mUpcomingList.clear();
            mTodayList.clear();
            mTomorrowList.clear();
            mLaterList.clear();
            mCategoryTitleList.clear();

            ToDoTableHelper mToDoTableHelper = new ToDoTableHelper(MainActivity.this);
            SimpleDateFormat lSimpleDateFormatOfHourMinute = new SimpleDateFormat("yyyyMMddHHmm");
            mCalendar = Calendar.getInstance();
            String lCurrentMs= String.valueOf(mCalendar.getTimeInMillis());
            mCalendar.add(Calendar.HOUR_OF_DAY,8);
            String lUpComing = String.valueOf(mCalendar.getTimeInMillis());
            Log.d("time plus8 hrs  ",lUpComing);
            mUpcomingList.addAll(mToDoTableHelper.retrieveWithWhereClause(ToDoTableEntries.COLUMN_DEADLINE," BETWEEN " +lCurrentMs+" AND "+lUpComing));

            mCalendar = Calendar.getInstance();
            mCalendar.set(Calendar.HOUR_OF_DAY,0);
            String lDayStart = String.valueOf(mCalendar.getTimeInMillis());
            mCalendar.set(Calendar.HOUR_OF_DAY,23);
            String lDayEnd = String.valueOf(mCalendar.getTimeInMillis());
            mTodayList.addAll(mToDoTableHelper.retrieveWithWhereClause(ToDoTableEntries.COLUMN_DEADLINE," BETWEEN " +lDayStart+" AND "+lDayEnd));

            mCalendar = Calendar.getInstance();
            mCalendar.add(Calendar.DAY_OF_MONTH,1);
            mCalendar.set(Calendar.HOUR_OF_DAY,0);
            lDayStart = String.valueOf(mCalendar.getTimeInMillis());
            mCalendar.set(Calendar.HOUR_OF_DAY,23);
            lDayEnd = String.valueOf(mCalendar.getTimeInMillis());
            mTomorrowList.addAll(mToDoTableHelper.retrieveWithWhereClause(ToDoTableEntries.COLUMN_DEADLINE," BETWEEN " +lDayStart+" AND "+lDayEnd));

            mLaterList.addAll(mToDoTableHelper.retrieveWithWhereClause(ToDoTableEntries.COLUMN_DEADLINE,"> "+lDayEnd));

            mCategoryTitleList.addAll(mToDoTableHelper.getCategories());




            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mExpandableListAdapter.notifyDataSetChanged();
            mCategoryRecyclerAdapter.notifyDataSetChanged();
        }
    }
}
