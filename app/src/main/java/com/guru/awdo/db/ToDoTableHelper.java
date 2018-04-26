package com.guru.awdo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.guru.awdo.constants.ToDoTableEntries;
import com.guru.awdo.pojos.ToDoData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guru on 12-04-2018.
 */

public class ToDoTableHelper {

    Context mContext;
    public static String TAG = "ToDoTableHelper";
    SQLiteDatabase mSqLiteDatabase;

    public ToDoTableHelper(Context pContext) {
        this.mContext = pContext;


    }

    String mAllColumns[] = {ToDoTableEntries.COLUMN_ID,
            ToDoTableEntries.COLUMN_DESCRIPTION,
            ToDoTableEntries.COLUMN_TIMESTAMP,
            ToDoTableEntries.COLUMN_CATEGORY,
            ToDoTableEntries.COLUMN_IS_RECURRING,
            ToDoTableEntries.COLUMN_DEADLINE,
            ToDoTableEntries.COLUMN_PRIORITY,
            ToDoTableEntries.COLUMN_IS_DONE,
            ToDoTableEntries.COLUMN_IS_PENDING};

    //remove is pending is pending  column


    /**
     * method to create table Todo
     *
     * @param mSqLiteDatabase
     */
    public static void createTodoTable(SQLiteDatabase mSqLiteDatabase) {
        String lSqlCreateTodoTable = "CREATE TABLE " + ToDoTableEntries.TABLE_NAME +
                '(' + ToDoTableEntries.COLUMN_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT ," +
                ToDoTableEntries.COLUMN_DESCRIPTION + " TEXT NOT NULL," +
                ToDoTableEntries.COLUMN_TIMESTAMP + " LONG," +
                ToDoTableEntries.COLUMN_CATEGORY + " TEXT," +
                ToDoTableEntries.COLUMN_IS_RECURRING + " BOOLEAN," +
                ToDoTableEntries.COLUMN_DEADLINE + " LONG NOT NULL," +
                ToDoTableEntries.COLUMN_PRIORITY + " INTEGER," +
                ToDoTableEntries.COLUMN_IS_DONE + " BOOLEAN," +
                ToDoTableEntries.COLUMN_IS_PENDING + " BOOLEAN )";

        mSqLiteDatabase.execSQL(lSqlCreateTodoTable);

    }

    /**
     * method to get TodoData by category
     * @param pCategory
     * @return
     */
    public List<ToDoData> getTodoDataByCategory(String pCategory) {
        List<ToDoData> lTodoByCategoryList = new ArrayList<>();
        String lSelectQuery = "SELECT * FROM " + ToDoTableEntries.TABLE_NAME + "WHERE " + ToDoTableEntries.COLUMN_CATEGORY + " = " + pCategory + " ORDER BY " + ToDoTableEntries.COLUMN_DEADLINE + " ASC";
        mSqLiteDatabase = new AppDBHelper(mContext).getReadableDatabase();
        Cursor lCursor = mSqLiteDatabase.rawQuery(lSelectQuery, null);
        if (lCursor.moveToFirst()) {
            do {
                ToDoData lToDoData = new ToDoData();
                lToDoData.setmID(lCursor.getInt(lCursor.getColumnIndex(ToDoTableEntries.COLUMN_ID)));
                lToDoData.setmDescription(lCursor.getString(lCursor.getColumnIndex(ToDoTableEntries.COLUMN_DESCRIPTION)));
                lToDoData.setmTimeStamp(lCursor.getLong(lCursor.getColumnIndex(ToDoTableEntries.COLUMN_TIMESTAMP)));
                lToDoData.setmCategory(lCursor.getString(lCursor.getColumnIndex(ToDoTableEntries.COLUMN_CATEGORY)));
                lToDoData.setmIsRecurring(Boolean.valueOf(lCursor.getString(lCursor.getColumnIndex(ToDoTableEntries.COLUMN_IS_RECURRING))));
                lToDoData.setmDeadline(lCursor.getLong(lCursor.getColumnIndex(ToDoTableEntries.COLUMN_DEADLINE)));
                lToDoData.setmPriority(lCursor.getInt(lCursor.getColumnIndex(ToDoTableEntries.COLUMN_IS_RECURRING)));
                lToDoData.setmIsPending(Boolean.valueOf(lCursor.getString(lCursor.getColumnIndex(ToDoTableEntries.COLUMN_IS_PENDING))));
                lToDoData.setmIsDone((lCursor.getString(lCursor.getColumnIndex(ToDoTableEntries.COLUMN_IS_DONE))).equalsIgnoreCase("1"));


                lTodoByCategoryList.add(lToDoData);
            } while (lCursor.moveToNext());
        }

        mSqLiteDatabase.close();
        lCursor.close();


        return lTodoByCategoryList;
    }

    /**
     * method to Save values to db from addToDoActivity
     * @param pToDoData
     */
    public long SaveTodoTaskToDB(ToDoData pToDoData) {
        long lRowId;
        ContentValues lContentValues = new ContentValues();
        mSqLiteDatabase = new AppDBHelper(mContext).getWritableDatabase();

        lContentValues.put(ToDoTableEntries.COLUMN_CATEGORY, pToDoData.getmCategory());
        lContentValues.put(ToDoTableEntries.COLUMN_DESCRIPTION, pToDoData.getmDescription());
        lContentValues.put(ToDoTableEntries.COLUMN_TIMESTAMP, pToDoData.getmTimeStamp());
        lContentValues.put(ToDoTableEntries.COLUMN_DEADLINE, pToDoData.getmDeadline());
        lContentValues.put(ToDoTableEntries.COLUMN_IS_RECURRING, pToDoData.ismIsRecurring());
        lContentValues.put(ToDoTableEntries.COLUMN_IS_DONE, false);

       lRowId =  mSqLiteDatabase.insert(ToDoTableEntries.TABLE_NAME, null, lContentValues);
        mSqLiteDatabase.close();
        return lRowId;
    }

    /**
     * Common method to retrieve with where clause
     * @param pWhereColumnWithCondition
     * @param pWhereClause
     * @return
     */
    public List<ToDoData> retrieveWithWhereClause(String pWhereColumnWithCondition, String pWhereClause) {
        List<ToDoData> lRetrievedList = new ArrayList<>();
        mSqLiteDatabase = new AppDBHelper(mContext).getReadableDatabase();
        Cursor lCursor; //= mSqLiteDatabase.query(ToDoTableEntries.TABLE_NAME,mAllColumns,null,new String[]{pWhereColumnWithCondition},ToDoTableEntries.COLUMN_TIMESTAMP,pWhereClause,ToDoTableEntries.COLUMN_DEADLINE+" ASC");
        lCursor = mSqLiteDatabase.rawQuery("SELECT * FROM " + ToDoTableEntries.TABLE_NAME + " WHERE " + pWhereColumnWithCondition + pWhereClause, null);
        if (lCursor.moveToFirst()) {
            do {
                ToDoData lToDoData = new ToDoData();

                lToDoData.setmID(lCursor.getInt(lCursor.getColumnIndex(ToDoTableEntries.COLUMN_ID)));
                lToDoData.setmDescription(lCursor.getString(lCursor.getColumnIndex(ToDoTableEntries.COLUMN_DESCRIPTION)));
                lToDoData.setmTimeStamp(lCursor.getLong(lCursor.getColumnIndex(ToDoTableEntries.COLUMN_TIMESTAMP)));
                lToDoData.setmCategory(lCursor.getString(lCursor.getColumnIndex(ToDoTableEntries.COLUMN_CATEGORY)));
                lToDoData.setmIsRecurring(Boolean.valueOf(lCursor.getString(lCursor.getColumnIndex(ToDoTableEntries.COLUMN_IS_RECURRING))));
                lToDoData.setmDeadline(lCursor.getLong(lCursor.getColumnIndex(ToDoTableEntries.COLUMN_DEADLINE)));
                lToDoData.setmPriority(lCursor.getInt(lCursor.getColumnIndex(ToDoTableEntries.COLUMN_IS_RECURRING)));
                lToDoData.setmIsPending(Boolean.valueOf(lCursor.getString(lCursor.getColumnIndex(ToDoTableEntries.COLUMN_IS_PENDING))));
                lToDoData.setmIsDone(lCursor.getString(lCursor.getColumnIndex(ToDoTableEntries.COLUMN_IS_DONE)).equalsIgnoreCase("1"));


                lRetrievedList.add(lToDoData);
            } while (lCursor.moveToNext());
        }

        mSqLiteDatabase.close();
        lCursor.close();

        return lRetrievedList;
    }

    /**
     * List of entered categories for auto complete text view
     * @return arraylist containing  values by category
     */
    public List<String> getCategories() {
        List<String> lCategoriesList = new ArrayList<>();
        mSqLiteDatabase = new AppDBHelper(mContext).getReadableDatabase();
        Cursor lCursor = mSqLiteDatabase.rawQuery("SELECT DISTINCT " + ToDoTableEntries.COLUMN_CATEGORY + " FROM " + ToDoTableEntries.TABLE_NAME+" ORDER BY "+ ToDoTableEntries.COLUMN_CATEGORY +" COLLATE NOCASE", null);
        if (lCursor.moveToFirst()) {
            String lTemp;
            do {
                lTemp = lCursor.getString(lCursor.getColumnIndex(ToDoTableEntries.COLUMN_CATEGORY));
                lCategoriesList.add(lTemp);
            } while (lCursor.moveToNext());

        }
        mSqLiteDatabase.close();
        lCursor.close();
        Log.d("category list","Size "+lCategoriesList.size());


        return lCategoriesList;
    }

    /**
     * update data to db from EditTodo Activity
     * @param pToDoData
     */
    public void updateTodoData(ToDoData pToDoData)
    {
        ContentValues lContentValues = new ContentValues();
        mSqLiteDatabase = new AppDBHelper(mContext).getWritableDatabase();
        lContentValues.put(ToDoTableEntries.COLUMN_ID,pToDoData.getmID());
        lContentValues.put(ToDoTableEntries.COLUMN_CATEGORY, pToDoData.getmCategory());
        lContentValues.put(ToDoTableEntries.COLUMN_DESCRIPTION, pToDoData.getmDescription());
        lContentValues.put(ToDoTableEntries.COLUMN_TIMESTAMP, pToDoData.getmTimeStamp());
        lContentValues.put(ToDoTableEntries.COLUMN_DEADLINE, pToDoData.getmDeadline());
        lContentValues.put(ToDoTableEntries.COLUMN_IS_RECURRING, pToDoData.ismIsRecurring());
        mSqLiteDatabase.insertWithOnConflict(ToDoTableEntries.TABLE_NAME, null, lContentValues,SQLiteDatabase.CONFLICT_REPLACE);
        mSqLiteDatabase.close();

    }

    public void updateDoneState(long pId)
    {
        ContentValues lContentValues = new ContentValues();
        mSqLiteDatabase = new AppDBHelper(mContext).getWritableDatabase();
        String lWhereClause = ToDoTableEntries.COLUMN_ID + " = "+pId;
        lContentValues.put(ToDoTableEntries.COLUMN_IS_DONE, true);
        //mSqLiteDatabase.updateWithOnConflict(ToDoTableEntries.TABLE_NAME,lContentValues,null,null,SQLiteDatabase.CONFLICT_REPLACE);
        mSqLiteDatabase.update(ToDoTableEntries.TABLE_NAME,lContentValues,lWhereClause,null);
        mSqLiteDatabase.close();

    }

    public ToDoData getTodoDataById(long pId)
    {
        ToDoData lToDoData = new ToDoData();

        mSqLiteDatabase = new AppDBHelper(mContext).getReadableDatabase();
        Cursor lCursor = mSqLiteDatabase.rawQuery(new String("SELECT * FROM "+ ToDoTableEntries.TABLE_NAME +" WHERE "+ ToDoTableEntries.COLUMN_ID +" = "+ pId ),null);
        if (lCursor.moveToFirst())
        {
            lToDoData.setmID(lCursor.getInt(lCursor.getColumnIndex(ToDoTableEntries.COLUMN_ID)));
            lToDoData.setmDescription(lCursor.getString(lCursor.getColumnIndex(ToDoTableEntries.COLUMN_DESCRIPTION)));
            lToDoData.setmTimeStamp(lCursor.getLong(lCursor.getColumnIndex(ToDoTableEntries.COLUMN_TIMESTAMP)));
            lToDoData.setmCategory(lCursor.getString(lCursor.getColumnIndex(ToDoTableEntries.COLUMN_CATEGORY)));
            lToDoData.setmIsRecurring(Boolean.valueOf(lCursor.getString(lCursor.getColumnIndex(ToDoTableEntries.COLUMN_IS_RECURRING))));
            lToDoData.setmDeadline(lCursor.getLong(lCursor.getColumnIndex(ToDoTableEntries.COLUMN_DEADLINE)));
            lToDoData.setmPriority(lCursor.getInt(lCursor.getColumnIndex(ToDoTableEntries.COLUMN_IS_RECURRING)));
            lToDoData.setmIsPending(Boolean.valueOf(lCursor.getString(lCursor.getColumnIndex(ToDoTableEntries.COLUMN_IS_PENDING))));
            lToDoData.setmIsDone((lCursor.getString(lCursor.getColumnIndex(ToDoTableEntries.COLUMN_IS_DONE))).equalsIgnoreCase("1"));

        }
        lCursor.close();
        mSqLiteDatabase.close();



        return lToDoData;
    }


}
