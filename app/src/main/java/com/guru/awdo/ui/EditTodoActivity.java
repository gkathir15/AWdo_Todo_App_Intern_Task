package com.guru.awdo.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.guru.awdo.R;
import com.guru.awdo.pojos.ToDoData;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditTodoActivity extends AppCompatActivity {

    private EditText mDescriptionET;
    private AutoCompleteTextView mAutocompleteCategoryView;
    private Button mTodayBt,mTomorrowBt,mCustomDateBt,mCustomTimeBt,mSubmitBt;
    private Button   mPlusOneHourBt,mPlusTwoHourBt;
    private TextView mTimeSetTv;
    ToDoData mTodoData = new ToDoData();
    SimpleDateFormat DisplayDateFormat = new SimpleDateFormat("hh.mm aa 'on' dd MMM");
    Date mDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        Intent lIntent = getIntent();
        mTodoData = (ToDoData) lIntent.getSerializableExtra("TodoData");


        mTodayBt = findViewById(R.id.today);
        mTomorrowBt = findViewById(R.id.tomorrow);
        mCustomDateBt = findViewById(R.id.custom_date_picker);
        mTimeSetTv = findViewById(R.id.time);

        mPlusOneHourBt = findViewById(R.id.plus_1hour);
        mPlusTwoHourBt = findViewById(R.id.plus2hour);
        mCustomTimeBt = findViewById(R.id.custom_time_picker);

        mDescriptionET = findViewById(R.id.add_description);
        mAutocompleteCategoryView = findViewById(R.id.add_category);
        mSubmitBt = findViewById(R.id.submit);

        mDescriptionET.setText(mTodoData.getmDescription());
        mAutocompleteCategoryView.setText(mTodoData.getmCategory());
        try {
            mTimeSetTv.setText((CharSequence) (mDate = DisplayDateFormat.parse(String.valueOf(mTodoData.getmDeadline()))));
        } catch (ParseException e) {
            e.printStackTrace();
        }



    }
}
