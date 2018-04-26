package com.guru.awdo.receiver;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.guru.awdo.db.ToDoTableHelper;
import com.guru.awdo.ui.AddToDoActivity;
import com.guru.awdo.ui.EditToDoActivity;

import javax.xml.datatype.Duration;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Guru on 23-04-2018.
 */
public class NotificationDoneReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        long lTaskId = intent.getLongExtra("TaskId",0);

        new ToDoTableHelper(context).updateDoneState(lTaskId);
        Toast.makeText(context,"Todo Done",Toast.LENGTH_SHORT).show();

        Intent lIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent lPendingIntent = PendingIntent.getBroadcast(context, (int) lTaskId,lIntent,PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager lAlarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        lAlarmManager.cancel(lPendingIntent);
        NotificationManager lNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        lNotificationManager.cancel((int) lTaskId);


    }
}
