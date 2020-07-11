package harshp.app.rcbday2014;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Activity0 extends Activity {

    public ShimmerTextView stvSeconds;
    public ShimmerTextView stvMinutes;
    public ShimmerTextView stvHours;
    public ShimmerTextView stvDays;
    public Shimmer shimmer;

    private PendingIntent pendingIntent;
    // todo if it's still his birthday, continue to show the message

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity0);

        stvSeconds = (ShimmerTextView) findViewById(R.id.tvSeconds);
        stvMinutes = (ShimmerTextView) findViewById(R.id.tvMinutes);
        stvHours = (ShimmerTextView) findViewById(R.id.tvHours);
        stvDays = (ShimmerTextView) findViewById(R.id.tvDays);
        shimmer = new Shimmer();

        String debugdate = "06/11/2014 23:59";
        long difference = dateDiff(debugdate);
        if (difference <= 0) { // BIRTHDAY OR LATER
            Intent intent;
            difference = dateDiff("07/11/2014 23:59");
            if (difference <= 0) { // BIRTHDAY
                intent = new Intent(Activity0.this, Activity1.class);
            } else { // LATER
                intent = new Intent(Activity0.this, MyWish.class);
            }
            startActivity(intent);
        } else { // BEFORE BIRTHDAY
            SharedPreferences prefs = getSharedPreferences("RCBDAY2014", Context.MODE_PRIVATE);
            boolean alarmSet = prefs.getBoolean("ALARM", true);
            if (alarmSet) { // set alarm
                Intent intent = new Intent(Activity0.this, AlarmReceiver.class);
                intent.setAction("harshp.app.rcbday2014.ALARM_ACTION");
                pendingIntent = PendingIntent.getBroadcast(this, 111, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                long bday = new Date().getTime()+5000;
                /*
                try {
                    bday = sdf.parse("06/11/2014 00:00").getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                */
                alarmManager.set(AlarmManager.RTC_WAKEUP, bday, pendingIntent);

                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("ALARM", false);
                editor.apply();
//                Log.v("RCBDAY", "Alarm Set");
            }
            final CTimer cTimer = new CTimer(difference, 1000);
            cTimer.start();
        }
    }

    private long dateDiff(String target) {
        Date rcday = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
//            rcday = sdf.parse("06/11/2014 23:59");
            rcday = sdf.parse(target);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long bday = rcday.getTime();
        long now = new Date().getTime();
        return bday - now;
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmer.setStartDelay(2000)
                .setDuration(2000)
                .start(stvSeconds);
        shimmer.setStartDelay(2000)
                .setDuration(2000)
                .start(stvMinutes);
        shimmer.setStartDelay(4000)
                .setDuration(2000)
                .start(stvHours);
        shimmer.setStartDelay(6000)
                .setDuration(2000)
                .start(stvDays);

        YoYo.with(Techniques.FadeIn)
                .duration(2000)
                .playOn(stvSeconds);
        YoYo.with(Techniques.FadeIn)
                .duration(2000)
                .playOn(stvMinutes);
        YoYo.with(Techniques.FadeIn)
                .duration(4000)
                .playOn(stvHours);
        YoYo.with(Techniques.FadeIn)
                .duration(6000)
                .playOn(stvDays);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity0, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    public class CTimer extends CountDownTimer {

        public CTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            l /= 1000; // seconds
            stvSeconds.setText(String.valueOf(l) + "\nSeconds");
            l /= 60; // minutes
            stvMinutes.setText(String.valueOf(l) + "\nMinutes");
            l /= 60; // hours
            stvHours.setText(String.valueOf(l) + "\nHours");
            l /= 24; // days
            stvDays.setText(String.valueOf(l) + "\nDays");
        }

        @Override
        public void onFinish() {
//            Intent intent = new Intent(Activity0.this, MyWish.class);
//            startActivity(intent);
        }
    }


}
