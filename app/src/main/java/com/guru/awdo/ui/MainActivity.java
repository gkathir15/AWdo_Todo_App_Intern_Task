package com.guru.awdo.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.facebook.stetho.Stetho;
import com.guru.awdo.R;
import com.guru.awdo.adapters.ExpandableListAdapter;
import com.guru.awdo.constants.ToDoTableEntries;
import com.guru.awdo.db.ToDoTableHelper;
import com.guru.awdo.pojos.ToDoData;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ExpandableListView mExpandableListView;
    HashMap<Integer,List<ToDoData>> mExpandableListParentData= new HashMap();
    ExpandableListAdapter mExpandableListAdapter;
    List<ToDoData> mUpcoming = new ArrayList<>();
    List<ToDoData> mTomorrow = new ArrayList<>();
    List<ToDoData> mToday = new ArrayList<>();
    List<String> mParentTitles = new ArrayList<>();
    Calendar mCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Stetho.initializeWithDefaults(this);

        mParentTitles.add("Upcoming");
        mParentTitles.add("Today");
        mParentTitles.add("Tomorrow");

        mExpandableListView = findViewById(R.id.parent_expandable_list);
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



        mExpandableListParentData.put(0,mUpcoming);
        mExpandableListParentData.put(1,mToday);
        mExpandableListParentData.put(2,mTomorrow);


        mExpandableListAdapter = new ExpandableListAdapter(this,mExpandableListParentData,mParentTitles);
        mExpandableListView.setAdapter(mExpandableListAdapter);
        mExpandableListView.animate();
        mExpandableListView.smoothScrollByOffset(5);
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                ToDoData lTodoData = mExpandableListParentData.get(groupPosition).get(childPosition);

                Intent lIntent = new Intent(getApplicationContext(),EditTodoActivity.class);
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

    class FetchTodoTasks extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {

            mUpcoming.clear();
            mToday.clear();
            mTomorrow.clear();

            ToDoTableHelper mToDoTableHelper = new ToDoTableHelper(MainActivity.this);
            SimpleDateFormat lSimpleDateFormatOfHourMinute = new SimpleDateFormat("yyyyMMddHHmm");
            mCalendar = Calendar.getInstance();
            String lCurrentMs= String.valueOf(mCalendar.getTimeInMillis());
            mCalendar.add(Calendar.HOUR_OF_DAY,8);
            String lUpComing = String.valueOf(mCalendar.getTimeInMillis());
            Log.d("time plus8 hrs  ",lUpComing);
            mUpcoming.addAll(mToDoTableHelper.retrieveWithWhereClause(ToDoTableEntries.COLUMN_DEADLINE," BETWEEN " +lCurrentMs+" AND "+lUpComing));

            mCalendar = Calendar.getInstance();
            mCalendar.set(Calendar.HOUR_OF_DAY,0);
            String lDayStart = String.valueOf(mCalendar.getTimeInMillis());
            mCalendar.set(Calendar.HOUR_OF_DAY,23);
            String lDayEnd = String.valueOf(mCalendar.getTimeInMillis());
            mToday.addAll(mToDoTableHelper.retrieveWithWhereClause(ToDoTableEntries.COLUMN_DEADLINE," BETWEEN " +lDayStart+" AND "+lDayEnd));

            mCalendar = Calendar.getInstance();
            mCalendar.add(Calendar.DAY_OF_MONTH,1);
            mCalendar.set(Calendar.HOUR_OF_DAY,0);
            lDayStart = String.valueOf(mCalendar.getTimeInMillis());
            mCalendar.set(Calendar.HOUR_OF_DAY,23);
            lDayEnd = String.valueOf(mCalendar.getTimeInMillis());
            mTomorrow.addAll(mToDoTableHelper.retrieveWithWhereClause(ToDoTableEntries.COLUMN_DEADLINE," BETWEEN " +lDayStart+" AND "+lDayEnd));




            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mExpandableListAdapter.notifyDataSetChanged();
        }
    }
}
