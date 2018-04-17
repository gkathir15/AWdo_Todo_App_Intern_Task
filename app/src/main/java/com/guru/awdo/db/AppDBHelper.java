package com.guru.awdo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Guru on 12-04-2018.
 */

public class AppDBHelper extends SQLiteOpenHelper {

    public static int DATABASE_VERSION = 1;
    Context mContext;
    public static String TAG ="AppDBHelper";
    public static String DB_NAME = "ToDo_DATABASE";


    public AppDBHelper(Context context) {
        super(context,DB_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        ToDoTableHelper.createTodoTable(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
