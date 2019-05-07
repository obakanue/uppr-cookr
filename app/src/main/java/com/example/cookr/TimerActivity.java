package com.example.cookr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.os.Vibrator;


public class TimerActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mProximity;
    private static final int SENSOR_SENSITIVITY = 4;
    TextView textView;
    public int seconds, minutes, hours;
    public int totalTime;
    private boolean timerOn;
    Vibrator vib;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        textView = (TextView)findViewById(R.id.tickingTimer);

    }
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
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
                startTimer(null);
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    private void vibrate(int ms){
        if (vib.hasVibrator()) {
            vib.vibrate(ms); // for 500 ms
        }
    }
    private void printTime(){
        String formatedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        textView.setText(formatedTime);
    }

    public void up(View v) {
        vibrate(100);
        minutes++;
        printTime();
    }

    public void down(View v) {
        if (minutes > 0) {
            vibrate(100);
            minutes--;
            String formatedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            printTime();
        }
    }

    public void timerClick(View v) {
        if (!timerOn) {
            vibrate(100);
            minutes = 0;
            hours = 0;
            seconds = 0;
            printTime();
        } else {
            if (hours > 0) {
                vibrate(100);
                hours--;
            }
        }
    }
    public void logoClick(View v) { vibrate(100);
       if (!timerOn){
           textView.setText("cookr");
       } else {
           hours++;

       }
    }

    public void startTimer(View v) {
        if (!timerOn) {
            vibrate(500);
            timerOn = true;
            textView.setTextColor(Color.parseColor("#725F42"));
            totalTime = seconds + minutes * 60 + hours * 60 * 60;
            new CountDownTimer(totalTime * 1000, 1000) {
                public void onTick(long millisUntilFinished) {
                    printTime();
                    if (seconds == 0) {
                        if (minutes > 0) {
                            seconds = 59;
                            minutes--;
                        } else if (hours > 0) {
                            seconds = 59;
                            minutes = 59;
                            hours--;
                        }
                    } else {
                        seconds--;
                    }

                }

                public void onFinish() {
                    textView.setText("FINISH!!");
                    textView.setTextColor(Color.parseColor("#474338"));
                    vibrate(500);
                    timerOn = false;
                }
            }.start();

        }
    }
}


