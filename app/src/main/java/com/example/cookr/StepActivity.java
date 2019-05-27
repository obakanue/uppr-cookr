package com.example.cookr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class StepActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mProximity;
    private Sensor mAccelerometer;
    private static final int SENSOR_SENSITIVITY = 4;
    private boolean turned = false, covered = false;
    private float accYValue;
    private ViewPager viewPager;
    private long coverTime, time;
    private int minutes = 10, seconds = 0;
    private TextView mTextField, minutesText, secondsText;

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

    public void timerStart(View v) {
        new CountDownTimer(600000, 1000) {

            public void onTick(long millisUntilFinished) {

                if (seconds == 0) {
                        seconds = 59;
                        minutes--;
                } else {
                    seconds--;
                }

                printMin();
                printSec();

            }

            public void onFinish() {
            }

        }.start();
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
            time = System.currentTimeMillis();
            if (event.values[0] >= -SENSOR_SENSITIVITY && event.values[0] <= SENSOR_SENSITIVITY) {
                //near
                covered = true;
                coverTime = System.currentTimeMillis();
            } else {
                if (time - coverTime < 1000 && covered) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    covered = false;
                }
                else if (time - coverTime >= 1000 && covered) {
                    Intent intent = new Intent(StepActivity.this, TimerActivity.class);
                    startActivity(intent);
                    covered = false;
                }
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

    public void prev(View v){
        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
    }

    private void printMin() {
        minutesText = (TextView) findViewById(R.id.stepMinutesText);
        String formatedTime = String.format("%02d", minutes);
        minutesText.setText(": " + formatedTime + ":");

    }

    private void printSec() {
        secondsText = (TextView) findViewById(R.id.stepSecondsText);
        String formatedTime = String.format("%02d", seconds);
        secondsText.setText(formatedTime);
    }
}
