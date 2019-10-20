package com.radityarin.badminton.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.radityarin.badminton.R;
import com.radityarin.badminton.penyewa.MainActivity;

import java.text.DateFormat;
import java.util.Calendar;

import static com.radityarin.badminton.penyewa.MainActivity.EXTRA_DATE;
import static com.radityarin.badminton.penyewa.MainActivity.EXTRA_HOUR;
import static com.radityarin.badminton.penyewa.MainActivity.EXTRA_MESSAGE;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        int setHour = intent.getIntExtra(EXTRA_HOUR, -1);
        String date = intent.getStringExtra(EXTRA_DATE);

        Calendar rightNow = Calendar.getInstance();
        String today = DateFormat.getDateInstance(DateFormat.FULL).format(rightNow.getTime());
        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY);
        String namalapangan = intent.getStringExtra(EXTRA_MESSAGE);

        if(currentHour == setHour && date.equals(today)){
            showAlarmNotification(context, namalapangan, "Waktu sisa 10 menit", 1);
        }
    }

    private void showAlarmNotification(Context context, String title, String message, int notifId) {

        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "AlarmManager channel";

        Intent intent;
        intent = new Intent(context.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.icon)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }

    }

}
