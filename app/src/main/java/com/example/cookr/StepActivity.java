package com.example.cookr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StepActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mProximity;
    private Sensor mAccelerometer;
    private static final int SENSOR_SENSITIVITY = 4;
    private boolean turned = false;
    private float accYValue;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (event.values[0] >= -SENSOR_SENSITIVITY && event.values[0] <= SENSOR_SENSITIVITY) {
                //near
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        } else if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accYValue = event.values[1];
            if (accYValue > 1) {
                float xValue = event.values[0];
                if (xValue < -4) {
                    turn(true);
                } else if (xValue > 4) {
                    turn(false);
                } else {
                    turned = false;
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void turn(boolean forward) {
        if (!turned) {
            int add = forward ? 1 : -1;
            viewPager.setCurrentItem(viewPager.getCurrentItem() + add);
            turned = true;
        }
    }

    public void next(View v){
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
    }
}
