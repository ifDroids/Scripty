package com.scripty.base;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

public class CommandExecutor extends Service {

    private Bundle mBundle;
    private static final String TAG = "CommandExecutor";
    private static final String CHANNEL_ID = "CommandExecutor";

    private Context ego;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "Into the service.");
        ego=this;

        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Scripty: Script is running...")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("NAI"))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL)
                .build();
        startForeground(1, notification);

        callAsynchronousTask();
        return super.onStartCommand(intent,flags,startId);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Scripty: Script is running...",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }

    Timer timer;

    private void callAsynchronousTask() {
        Log.e(TAG, "Running callAsynchronousTask()...");
        timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                AccessibilityClickerService.instance.sendFuckingClick(400,600);
            }

        };

        Log.e(TAG, "Going to call asynctask...");
        timer.schedule(doAsynchronousTask, 2000, 100000);
    }


    @Override
    public void onDestroy() {
        Log.e(TAG, "Service got killed...");
        timer.cancel();
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
