package app.GKO.GExam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ImageView;

/**
 * Created by SAVATH on 2/23/14.
 */
public class SplashScreenActivity extends Activity {


    Handler objHandler;
    Runnable objRunable;
    Long Delay_time;
    Long Time = 3000L;
    ImageView imvLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splashscreen_layout);

        imvLogo = (ImageView) findViewById(R.id.imvLogo);


        objHandler = new Handler();

        objRunable = new Runnable() {
            @Override
            public void run() {
                Intent objIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(objIntent);
                finish();
            }
        };





    }

    @Override
    protected void onResume() {
        super.onResume();

        Delay_time = Time;
        objHandler.postDelayed(objRunable, 3000);
        Time = System.currentTimeMillis();

    }

    @Override
    protected void onPause() {
        super.onPause();

        objHandler.removeCallbacks(objRunable);
        Time = Delay_time - (System.currentTimeMillis() - Time);
    }


}   // end of Main Class

