package harshp.app.rcbday2014;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {
    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        generateNotification(context);
//        Log.v("RCBDAY", "Receiver activated");
    }

    private void generateNotification(Context context) {
        Intent intent;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cake3);
        Bitmap bl = BitmapFactory.decodeResource(context.getResources(), R.drawable.candles2);
        Notification.Builder nBuilder = new Notification.Builder(context);
        nBuilder.setSmallIcon(R.drawable.candles1);
        nBuilder.setLargeIcon(bl);
        nBuilder.setPriority(2);

        long difference = dateDiff("06/11/2014 23:59");
        if (difference <= 0) { // BIRTHDAY
            Log.v("RCBDAY", "Alarm : Birthday");
            intent = new Intent(context, MyWish.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            nBuilder.setContentIntent(pendingIntent);
            nBuilder.setContentTitle("Happy Birthday Rohit");
            nBuilder.setContentText("Here's a different and creative gift from me made specially for you!!!");
            nBuilder.setStyle(new Notification.BigPictureStyle()
                    .bigPicture(bitmap)
                    .setBigContentTitle("Happy Birthday Rohit!!!"));
            notify(context, nBuilder);

            return;
        }
        Log.v("RCBDAY", "Pre-Birthday Notification");

        intent = new Intent(context, Activity0.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        nBuilder.setContentIntent(pendingIntent);
        nBuilder.setContentTitle("The surprise will be revealed soon");
        nBuilder.setContentText("can you guess what's in the app???");

        String time_left = "";
        long bday = 0;

        if (difference <= 900000) { // 15m
            difference /= 1000; // seconds
            difference /= 60;
            time_left = difference + " minutes to activate the Surprise!!!";
            bday = getDate("07/11/2014 00:00"); // next alarm 00m before
//            Log.v("RCBDAY", "<=5m set 5:30 d=" + bday);
        } else if (difference <= 2.7e+6) { // 45m
            difference /= 1000; // seconds
            difference /= 60;
            time_left = difference + " minutes to activate the Surprise!!!";
            bday = getDate("06/11/2014 23:45"); // next alarm 30m from prev
//            Log.v("RCBDAY", "<=10m set 5:25 d=" + bday);
        } else if (difference <= 5.4e+6) { // 90m
            difference /= 1000; // seconds
            difference /= 60;
            time_left = difference + " minutes to activate the Surprise!!!";
            bday = getDate("06/11/2014 23:15"); // next alarm 45m from prev
//            Log.v("RCBDAY", "<=20m set 5:20 d=" + bday);
        } else if (difference <= 1.08e+7) { // 3h
            difference /= 1000; // seconds
            difference /= 60 * 60;
            time_left = difference + " hours to activate the Surprise!!!";
            bday = getDate("06/11/2014 22:30"); // next alarm 90m before
//            Log.v("RCBDAY", "<=30m set 5:10 d=" + bday);
        } else if (difference <= 2.16e+7) { // 6h
            difference /= 1000; // seconds
            difference /= 60 * 60;
            time_left = difference + " hours to activate the Surprise!!!";
            bday = getDate("06/11/2014 21:00"); // next alarm 3h before
//            Log.v("RCBDAY", "<=40m set 5:00 d=" + bday);
        } else if (difference <= 4.32e+7) { // 12h
            difference /= 1000; // seconds
            difference /= 60 * 60;
            time_left = difference + " hours to activate the Surprise!!!";
            bday = getDate("06/11/2014 18:00"); // next alarm 6h before
//            Log.v("RCBDAY", "<=50m set 4:50 d=" + bday);
        } else if (difference <= 8.64e+7) { // 24h
            difference /= 1000; // seconds
            difference /= 60 * 60 * 60;
            time_left = difference + " day(s) to activate the Surprise!!!";
            bday = getDate("06/11/2014 12:00"); // next alarm 12h before
//            Log.v("RCBDAY", "<=60m set 4:40 d=" + bday);
        } else {
            difference /= 1000; // seconds
            difference /= 60 * 60 * 60;
            time_left = difference + " day(s) to activate the Surprise!!!";
            bday = getDate("06/11/2014 00:00"); // next alarm 12h before
        }
        nBuilder.setStyle(new Notification.BigPictureStyle()
                .bigPicture(bitmap)
                .setBigContentTitle(time_left));
        notify(context, nBuilder);
        intent = new Intent(context, AlarmReceiver.class);
        intent.setAction("harshp.app.rcbday2014.ALARM_ACTION");
        pendingIntent = PendingIntent.getBroadcast(context, 111, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, bday, pendingIntent);

    }

    private void notify(Context context, Notification.Builder nBuilder) {
        Notification notif = nBuilder.build();
        notif.defaults |= Notification.DEFAULT_SOUND;
        notif.defaults |= Notification.DEFAULT_VIBRATE;
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notif);
    }

    private long dateDiff(String target) {
        long bday = getDate(target);
        long now = new Date().getTime();
        return bday - now;
    }

    private long getDate(String target) {
        Date rcday = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
//            rcday = sdf.parse("06/11/2014 23:59");
            rcday = sdf.parse(target);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return rcday.getTime();
    }
}
