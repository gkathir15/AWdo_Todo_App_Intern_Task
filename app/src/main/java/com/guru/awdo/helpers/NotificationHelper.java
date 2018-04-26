package com.guru.awdo.helpers;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.guru.awdo.R;
import com.guru.awdo.pojos.ToDoData;
import com.guru.awdo.receiver.NotificationDoneReceiver;
import com.guru.awdo.ui.MainActivity;

/**
 * Created by Guru on 19-04-2018.
 */

public class NotificationHelper {

    private String mChannelId = "AWTodo";
    int mTempID = 123;
    Uri mUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


    public void showNotificationWithAction(Context pContext, ToDoData pTodoData) {
        Intent lIntent = new Intent(pContext, MainActivity.class);
        lIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        lIntent.putExtra("TaskData",pTodoData);
        PendingIntent lPendingIntent = PendingIntent.getActivity(pContext, 0, lIntent, 0);
        Intent lSetDoneIntent = new Intent(pContext, NotificationDoneReceiver.class);
        lSetDoneIntent.setAction("DONE");
        lSetDoneIntent.putExtra("TaskId", pTodoData.getmID());
        PendingIntent lSetDonePendingIntent = PendingIntent.getBroadcast(pContext, 0, lSetDoneIntent, 0);


        NotificationCompat.Builder lBuilder = new NotificationCompat.Builder(pContext, mChannelId)
                .setSmallIcon(R.drawable.notify)
                .setContentTitle("AW ToDo")
                .setContentText(pTodoData.getmDescription())
                .setContentInfo(pTodoData.getmCategory())
                .setSound(mUri)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setContentIntent(lPendingIntent)
                .setAutoCancel(true);
        NotificationManager lNotificationManager = (NotificationManager) pContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel lNotificationChannel = new NotificationChannel(mChannelId, mChannelId, NotificationManager.IMPORTANCE_DEFAULT);
            lNotificationChannel.setDescription(mChannelId);
            lNotificationManager.createNotificationChannel(lNotificationChannel);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            lBuilder.setCategory(Notification.CATEGORY_REMINDER);
        }
        lBuilder.addAction(R.drawable.ic_tick, "DONE", lSetDonePendingIntent);
        lNotificationManager.notify((int) pTodoData.getmID(), lBuilder.build());

    }


}
