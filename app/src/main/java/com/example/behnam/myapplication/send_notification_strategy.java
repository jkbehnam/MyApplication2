package com.example.behnam.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.behnam.myapplication.activities.MainActivity;




import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

/**
 * Created by User on 2/1/2018.
 */

public interface send_notification_strategy {
    public void send_nofift(Context context);


}

class daily_data_notification implements send_notification_strategy {
    @Override
    public void send_nofift(Context context) {

        NotificationManager mNotificationManager;
        NotificationCompat.Builder mBuilder;
        final String NOTIFICATION_CHANNEL_ID = "10001";
        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String message = "دوست عزیز جهت نظارت بر روند بهبودی شما و ارائه مشاوره بموقع لازم است اطلاعات لازم را در قسمت شاخص های روزانه ثبت نمایید.";
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
                10001 /* Request code */, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.photo_2018);
        mBuilder.setContentTitle("عدم تکمیل فرم شاخص های روزانه")
                .setContentText(message)
                .setAutoCancel(false)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message))
                .setContentIntent(resultPendingIntent);

        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(10001 /* Request Code */, mBuilder.build());
    }
}

class day_exercise_notification implements send_notification_strategy {
    @Override
    public void send_nofift(Context context) {
        NotificationManager mNotificationManager;
        NotificationCompat.Builder mBuilder;
        final String NOTIFICATION_CHANNEL_ID = "10002";
        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
                10002 /* Request code */, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        String message = "دوست عزیز جهت نظارت بر روند بهبودی شما و ارائه مشاوره بموقع لازم است اطلاعات مربوط به فعالیت ورزشی (گرم کردن، ورزش اصلی و سرد کردن)خود را در قسمت ورزش ثبت نمایید.";
        mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.photo_2018);
        mBuilder.setContentTitle("عدم تکمیل فرم حرکات ورزشی")
                .setContentText(message)
                .setAutoCancel(false)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message))
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent);

        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(10002 /* Request Code */, mBuilder.build());
    }

}

class weekly_sign_notification implements send_notification_strategy {
    @Override
    public void send_nofift(Context context) {
        NotificationManager mNotificationManager;
        NotificationCompat.Builder mBuilder;
        final String NOTIFICATION_CHANNEL_ID = "10003";
        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String message = "دوست عزیز در صورتی که هر گونه ناراحتی و علائم غیر طبیعی در طول روز دارید در قسمت علائم ثبت نمایید تا بتوان بر روند بهبودی شما بدرستی نظارت داشت.";
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
                10003 /* Request code */, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.photo_2018);
        mBuilder.setContentTitle("عدم تکمیل فرم علائم")
                .setContentText(message)
                .setAutoCancel(false)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message))
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent);

        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(10003 /* Request Code */, mBuilder.build());
    }

}

class afsordegi implements send_notification_strategy {
    @Override
    public void send_nofift(Context context) {
        NotificationManager mNotificationManager;
        NotificationCompat.Builder mBuilder;
        final String NOTIFICATION_CHANNEL_ID = "10004";
        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String message = "دوست عزیز جهت مدیریت علائم اضطراب و شدت آن لازم است پرسشنامه بررسی شدت اضطراب را پر نمایید.";
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
                10004 /* Request code */, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.photo_2018);
        mBuilder.setContentTitle("عدم تکمیل فرم افسردگی")
                .setContentText(message)
                .setAutoCancel(false)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message))
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent);

        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(10004 /* Request Code */, mBuilder.build());
    }

}

class ezterab implements send_notification_strategy {
    @Override
    public void send_nofift(Context context) {
        NotificationManager mNotificationManager;
        NotificationCompat.Builder mBuilder;
        final String NOTIFICATION_CHANNEL_ID = "10005";
        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String message = "دوست عزیز جهت مدیریت علائم اضطراب و شدت آن لازم است پرسشنامه بررسی شدت اضطراب را پر نمایید.";
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
                10005 /* Request code */, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.photo_2018);
        mBuilder.setContentTitle("عدم تکمیل فرم اضطراب")
                .setContentText(message)
                .setAutoCancel(false)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message))
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent);

        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(10005 /* Request Code */, mBuilder.build());
    }

}