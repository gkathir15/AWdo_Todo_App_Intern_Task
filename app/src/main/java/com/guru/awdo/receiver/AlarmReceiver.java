package com.guru.awdo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.guru.awdo.db.ToDoTableHelper;
import com.guru.awdo.helpers.NotificationHelper;
import com.guru.awdo.pojos.ToDoData;

/**
 * Created by Guru on 19-04-2018.
 */
public class AlarmReceiver extends BroadcastReceiver {

    static String TAG ="AlarmReceiver";



    @Override
    public void onReceive(Context context, Intent intent) {

        long lReceivedId;


        ToDoData lTodoData;
              lReceivedId =   intent.getLongExtra("TaskId",0);
              lTodoData = new ToDoTableHelper(context).getTodoDataById(lReceivedId);
        Log.d(TAG,lTodoData.getmDescription());

        new NotificationHelper().showNotificationWithAction(context,lTodoData);
        Log.d(TAG,"Broadcast received");


    }
}
