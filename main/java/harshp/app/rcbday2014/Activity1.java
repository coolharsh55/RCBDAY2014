package harshp.app.rcbday2014;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.io.ByteArrayInputStream;
import java.util.Random;


public class Activity1 extends Activity {

    public SQLiteDatabase db;
    public byte[] blob;
    public long c_cur = 0; // current record
    public long c_rec = 0; // total records
    public long c_min = 0; // last record read
    TextView tv;
    TextView sender;
    ImageView img;
    float[] hsv;
    Random rand;
    View view;
    ByteArrayInputStream inputStream;
    Bitmap bitmap;
    private Cursor messages;
    private DBHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity1);
        database = new DBHelper(this);
        db = database.getReadableDatabase();
        messages = db.rawQuery("select count(*) from Messages;", null);
        messages.moveToFirst();
        c_rec = messages.getLong(0);
        messages.close();
        tv = (TextView) findViewById(R.id.tvMessage);
        sender = (TextView) findViewById(R.id.tvSender);
        img = (ImageView) findViewById(R.id.ivPic);

        // SET COLOR
        SwipeGestures sg = new SwipeGestures(getApplicationContext());
        view = findViewById(R.id.svContent);
        view.setOnTouchListener(sg);
        view = findViewById(R.id.rlMain);
        view.setOnTouchListener(sg);

        rand = new Random();
        hsv = new float[3];
        hsv[0] = 360 * rand.nextFloat();
        hsv[1] = 0.75f;
        hsv[2] = 0.35f;
        view.setBackgroundColor(Color.HSVToColor(hsv));
    }

    @Override
    public void onResume() {
        super.onResume();
        // change color
        hsv[0] = 360 * rand.nextFloat();
        view.setBackgroundColor(Color.HSVToColor(hsv));

        // get last read message counter
        SharedPreferences prefs = getSharedPreferences("RCBDAY2014", Context.MODE_PRIVATE);
        c_min = prefs.getLong("LASTREAD", 1);
        c_cur = c_min;
        new ChangeImage().execute(0);
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences preferences = getSharedPreferences("RCBDAY2014", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("LASTREAD", c_cur);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private class SwipeGestures extends OnSwipeTouchListener {

        public SwipeGestures(Context ctx) {
            super(ctx);
        }

        @Override
        public void onSwipeTop() {
        }

        @Override
        public void onSwipeRight() { // NEXT
            Log.v("RCBDAY", "right/next cur:" + c_cur + " rec:" + c_rec);
            if (c_cur < c_rec) {
                c_cur++;
                new ChangeImage().execute(0);
            }
        }

        @Override
        public void onSwipeLeft() { // PREV
            Log.v("RCBDAY", "left/prev cur:" + c_cur + " rec:" + c_rec);
            if (c_cur > 1) {
                c_cur--;
                new ChangeImage().execute(0);
            }
        }

        @Override
        public void onSwipeBottom() {
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }
    }

    class ChangeImage extends AsyncTask<Integer, Integer, Integer> {
        protected void onPreExecute() {
            YoYo.with(Techniques.ZoomOut)
                    .duration(500)
                    .playOn(img);
            YoYo.with(Techniques.SlideOutDown)
                    .duration(500)
                    .playOn(tv);
            YoYo.with(Techniques.SlideOutUp)
                    .duration(500)
                    .playOn(sender);
            img.setImageDrawable(null);
            hsv[0] = 360 * rand.nextFloat();
            view.setBackgroundColor(Color.HSVToColor(hsv));
        }

        @Override
        protected Integer doInBackground(Integer... arg0) {
            messages = db.rawQuery("select * from Messages where _id = ?", new String[]{String.valueOf(c_cur)});
            messages.moveToFirst();
            blob = messages.getBlob(3);
            if (blob != null) {
                inputStream = new ByteArrayInputStream(blob);
                blob = null;
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(inputStream, null, options);
                inputStream.reset();
                options.inSampleSize = calculateInSampleSize(options);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(inputStream, null, options);
                inputStream = null;
            } else {
                bitmap = null;
            }
            return 0;
        }

        private int calculateInSampleSize(
                BitmapFactory.Options options) {
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > 900 || width > 540) {
                final int halfheight = height / 2;
                final int halfwidth = width / 2;
                while ((halfheight / inSampleSize) > 900 && (halfwidth / inSampleSize) > 540) {
                    inSampleSize *= 2;
                }
            }
            return inSampleSize;
        }

        protected void onProgressUpdate(Integer... a) {

        }

        protected void onPostExecute(Integer result) {
            YoYo.with(Techniques.SlideInUp)
                    .duration(2000)
                    .playOn(tv);
            YoYo.with(Techniques.SlideInDown)
                    .duration(2000)
                    .playOn(sender);

            if (bitmap != null) {
                img.setVisibility(View.VISIBLE);
                img.setImageBitmap(bitmap);
                bitmap = null;
                YoYo.with(Techniques.ZoomIn)
                        .duration(2000)
                        .playOn(img);
            } else {
                img.setVisibility(View.GONE);
            }
            tv.setText(messages.getString(1));
            sender.setText("from " + messages.getString(2));
            messages.close();

        }
    }
}
