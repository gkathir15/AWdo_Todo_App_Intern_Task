package com.guru.awdo.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    HashMap mExpandableListParentData = new HashMap();
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

        new FetchTodoTasks().execute();

        mExpandableListParentData.put(0,mUpcoming);
        mExpandableListParentData.put(1,mUpcoming);
        mExpandableListParentData.put(2,mUpcoming);


        mExpandableListAdapter = new ExpandableListAdapter(this,mExpandableListParentData,mParentTitles);
        mExpandableListView.setAdapter(mExpandableListAdapter);
        mExpandableListView.animate();
        mExpandableListView.smoothScrollByOffset(5);
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });

        }

    class FetchTodoTasks extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {

            ToDoTableHelper mToDoTableHelper = new ToDoTableHelper(MainActivity.this);
            SimpleDateFormat lSimpleDateFormatOfHourMinute = new SimpleDateFormat("yyyyMMddHHmm");
            mCalendar = Calendar.getInstance();
            mCalendar.add(Calendar.HOUR_OF_DAY,8);
            String lTempString = lSimpleDateFormatOfHourMinute.format(mCalendar.getTime());
            Log.d("time plus8 hrs  ",lTempString);
            mCalendar.clear();
            mUpcoming.addAll(mToDoTableHelper.retrieveWithWhereClause(ToDoTableEntries.COLUMN_DEADLINE+"<=",lTempString));

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mExpandableListAdapter.notifyDataSetChanged();
        }
    }
}
