package com.igts.pushnotificationdemo;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSendSimplePush;
    NotificationCompat.Builder notificationBuilder;
    NotificationManager notificationManager;
    Activity mActivity;
    Bitmap largeIcon;
    private int currentNotifID = 0;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = MainActivity.this;

        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        largeIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.notification);

        btnSendSimplePush = findViewById(R.id.btn_simple_push);
        btnSendSimplePush.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_simple_push:
                sendSimplePushNotification();
    //            sendPushNotificationsOreo();
                break;

        }

    }

    private void sendNotification() {
        Intent notificationIntent = new Intent(mActivity, MainActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(mActivity, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder.setAutoCancel(false)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(contentIntent);

        notificationManager = (NotificationManager) mActivity.getSystemService(Context.NOTIFICATION_SERVICE);


        currentNotifID++;
        int notificationId = currentNotifID;
        if (notificationId == Integer.MAX_VALUE - 1)
            notificationId = 0;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert notificationManager != null;
            notificationBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        assert notificationManager != null;
        notificationManager.notify(notificationId /* Request Code */, notificationBuilder.build());
    }

    public void sendSimplePushNotification() {
        notificationBuilder = new NotificationCompat.Builder(mActivity)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(largeIcon)
                .setContentTitle("Simple Push Notification")
                .setContentText("Simple Push Notification Demo");
        sendNotification();

    }
}
