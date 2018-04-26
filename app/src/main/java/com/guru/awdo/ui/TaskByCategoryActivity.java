package com.guru.awdo.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.guru.awdo.R;
import com.guru.awdo.pojos.ToDoData;

public class TaskByCategoryActivity extends AppCompatActivity {

    String mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        Intent lIntent = getIntent();
        mCategory = lIntent.getStringExtra("category");
    }
}
