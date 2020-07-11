package harshp.app.rcbday2014;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;


public class MyWish extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wish);
        View view = findViewById(R.id.vWish);
        view.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void onSwipeTop() {
            }

            public void onSwipeLeft() {
            }

            public void onSwipeRight() {
                Intent intent = new Intent(MyWish.this, Activity1.class);
                startActivity(intent);
            }

            public void onSwipeBottom() {
            }

            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        animateText();
    }

    private void animateText() {
        View myView = findViewById(R.id.vWish);
        myView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                TextView tv = (TextView) findViewById(R.id.tvM1);
                YoYo.with(Techniques.SlideInDown)
                        .duration(1000)
                        .playOn(tv);
                tv = (TextView) findViewById(R.id.tvM2);
                YoYo.with(Techniques.SlideInDown)
                        .duration(1500)
                        .playOn(tv);
                tv = (TextView) findViewById(R.id.tvM3);
                YoYo.with(Techniques.SlideInDown)
                        .duration(2000)
                        .playOn(tv);
                tv = (TextView) findViewById(R.id.tvM4);
                YoYo.with(Techniques.SlideInDown)
                        .duration(2500)
                        .playOn(tv);
                tv = (TextView) findViewById(R.id.tvM5);
                YoYo.with(Techniques.SlideInDown)
                        .duration(3000)
                        .playOn(tv);
                tv = (TextView) findViewById(R.id.tvM6);
                YoYo.with(Techniques.SlideInDown)
                        .duration(3500)
                        .playOn(tv);
                tv = (TextView) findViewById(R.id.tvM7);
                YoYo.with(Techniques.SlideInDown)
                        .duration(4000)
                        .playOn(tv);

                ShimmerTextView stv = (ShimmerTextView) findViewById(R.id.tvM8);
                Shimmer shimmer = new Shimmer();
                shimmer.setStartDelay(8000)
                        .start(stv);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_wish, menu);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
