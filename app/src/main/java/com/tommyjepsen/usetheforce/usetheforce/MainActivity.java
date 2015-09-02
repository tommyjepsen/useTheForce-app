package com.tommyjepsen.usetheforce.usetheforce;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private SensorManager mSensorManager;
    private Sensor mProximity;
    private FrameLayout rl;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rl = (FrameLayout) findViewById(R.id.activity_main_rl);
        tv = (TextView) findViewById(R.id.activity_main_tv);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onPause() {
        mSensorManager.unregisterListener(forceSensor);
        super.onPause();
    }


    @Override
    public void onResume() {
        super.onResume();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorManager.registerListener(forceSensor, mProximity, SensorManager.SENSOR_DELAY_FASTEST);
    }


    private SensorEventListener forceSensor = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.values[0] < (mProximity.getMaximumRange() / 2)) {
                Random rnd = new Random();
                int r = rnd.nextInt(256);
                int g = rnd.nextInt(256);
                int b = rnd.nextInt(256);
                int color = Color.argb(255, r, g, b);
                String hexColor = String.format( "#%02x%02x%02x", r, g, b );
                tv.setText(hexColor);
                rl.setBackgroundColor(color);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

}
