package com.example.cookr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;
import android.os.Vibrator;


public class TimerActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mProximity;
    private static final int SENSOR_SENSITIVITY = 4;
    // TextView textView;
    public int seconds, minutes, hours;
    public int totalTime, secondsLeft;
    private boolean timerOn, userTimerActivity;
    public boolean secondsHolder, minutesHolder, hoursHolder;
    Vibrator vib;
    TextView hoursTV, minutesTV, secondsTV, infoPanel;
    SpannableString underlined;
    CountDownTimer timer;

    private int parsedLight, parsedDark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        //textView = (TextView)findViewById(R.id.tickingTimer);

        hoursTV = (TextView) findViewById(R.id.hours);
        minutesTV = (TextView) findViewById(R.id.minutes);
        secondsTV = (TextView) findViewById(R.id.seconds);

        infoPanel = (TextView) findViewById(R.id.infopanel);

        timerOn = false;
        parsedDark = Color.parseColor("#474338");
        parsedLight = Color.parseColor("#725F42");

        setColorTimerAll(parsedLight);

        userTimerActivity = false;
        minutes = 0;
        hours = 0;
        seconds = 0;
        printTime();
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
            if (event.values[0] >= -SENSOR_SENSITIVITY && event.values[0] <= SENSOR_SENSITIVITY && !timerOn) {
                startTimer(null);
            } else if (event.values[0] >= -SENSOR_SENSITIVITY && event.values[0] <= SENSOR_SENSITIVITY && timerOn){
                timerOn = false;
                timer.cancel();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void vibrate(int ms) {
        if (vib.hasVibrator()) {
            vib.vibrate(ms); // for 500 ms
        }
    }

    private void printTime() {
        printH();
        printMin();
        printSec();
    }

    private void printH() {
        String formatedTime = String.format("%02d", hours);

        if (hoursHolder) {
            SpannableString underlined = new SpannableString(formatedTime + ":");
            underlined.setSpan(new UnderlineSpan(), 0, 2, 0);
            hoursTV.setText(underlined);
        } else {
            hoursTV.setText(formatedTime + ":");
        }

    }

    private void printMin() {
        String formatedTime = String.format("%02d", minutes);

        if (minutesHolder) {
            SpannableString underlined = new SpannableString(formatedTime + ":");
            underlined.setSpan(new UnderlineSpan(), 0, 2, 0);
            minutesTV.setText(underlined);
        } else {
            minutesTV.setText(formatedTime + ":");
        }
    }

    private void printSec() {
        String formatedTime = String.format("%02d", seconds);

        if (secondsHolder) {
            SpannableString underlined = new SpannableString(formatedTime);
            underlined.setSpan(new UnderlineSpan(), 0, 2, 0);
            secondsTV.setText(underlined);
        } else {
            secondsTV.setText(formatedTime);
        }
    }

    private void printLabel(int labelType) {
        switch (labelType) {
            case 0:
                infoPanel.setText("COOKR");
                break;
            case 1:
                infoPanel.setText("Please click on number before");
                break;
            case 2:
                infoPanel.setText("Please set timer first");
                break;
            case 3:
                infoPanel.setText("Press start or use sensor on top of the phone");
                break;
            default:
                // code block
        }

    }


    public void up(View v) {
        printLabel(3);
        if (!userTimerActivity) {
            printLabel(1);
        } else {
            if (hoursHolder) {
                hours++;
                printH();
            } else if (minutesHolder) {
                minutes = minutes + 1;
                printMin();
            } else if (secondsHolder) {
                seconds = seconds + 5;
                printSec();
            }

            vibrate(100);
        }
    }

    public void down(View v) {
        printLabel(3);
        if (hoursHolder) {
            if (hours > 0) {
                hours--;
                printH();
            }
        } else if (minutesHolder) {
            if (minutes > 0) {
                minutes--;
                printMin();
            }

        } else if (secondsHolder) {
            if (seconds > 0) {
                seconds--;
                printSec();
            }
        }

        vibrate(100);


    }


    public void timerClick(View v) {
        userTimerActivity = true;
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


    public void hoursClick(View v) {
        userTimerActivity = true;
        hoursTV.setTextColor(parsedDark);
        minutesTV.setTextColor(parsedLight);
        secondsTV.setTextColor(parsedLight);

        hoursHolder = true;
        minutesHolder = false;
        secondsHolder = false;

        printTime();
    }

    public void minutesClick(View v) {
        userTimerActivity = true;
        hoursTV.setTextColor(parsedLight);
        minutesTV.setTextColor(parsedDark);
        secondsTV.setTextColor(parsedLight);

        hoursHolder = false;
        minutesHolder = true;
        secondsHolder = false;

        printTime();
    }

    public void secondsClick(View v) {
        userTimerActivity = true;
        hoursTV.setTextColor(parsedLight);
        minutesTV.setTextColor(parsedLight);
        secondsTV.setTextColor(parsedDark);

        hoursHolder = false;
        minutesHolder = false;
        secondsHolder = true;

        printTime();
    }

    public void logoClick(View v) {
        vibrate(100);
        if (!timerOn) {
            //textView.setText("cookr");
        } else {
            hours++;

        }
    }

    private void setColorTimerAll(int parsedColor) {
        hoursTV.setTextColor(parsedColor);
        minutesTV.setTextColor(parsedColor);
        secondsTV.setTextColor(parsedColor);
    }

    private boolean timerSet() {
        if (seconds == 0 && minutes == 0 && hours == 0) {
            printLabel(2);
            return false;
        }
        return true;
    }

    public void startTimer(View v) {
        if (timerSet()) {

            if (!timerOn) {
                printLabel(0);
                vibrate(500);
                timerOn = true;
                setColorTimerAll(parsedDark);


                totalTime = seconds + minutes * 60 + hours * 3600;

                timer = new CountDownTimer(totalTime * 1000, 1000) {
                    public void onTick(long millisUntilFinished) {

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

                        printTime();

                    }

                    public void onFinish() {
                        //textView.setText("FINISH!!");
                        // textView.setTextColor(Color.parseColor("#474338"));

                        seconds = 0;
                        printTime();
                        setColorTimerAll(parsedLight);
                        timerOn = false;
                        vibrate(500);


                    }
                }.start();

            }
        }
    }

    public void startTimerOLD(View v) {
        if (!timerOn) {
            vibrate(500);
            timerOn = true;
            //textView.setTextColor(Color.parseColor("#725F42"));
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
                    //textView.setText("FINISH!!");
                    // textView.setTextColor(Color.parseColor("#474338"));
                    vibrate(500);
                    timerOn = false;
                }
            }.start();

        }
    }
}


