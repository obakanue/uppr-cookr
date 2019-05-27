package com.example.cookr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
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
    private boolean timerOn, userTimerActivity, covered = false;
    public boolean secondsHolder, minutesHolder, hoursHolder;
    private long coverTime, time;
    Vibrator vib;
    TextView hoursTV, minutesTV, secondsTV, infoPanel, startStopButton;
    SpannableString underlined;
    CountDownTimer timer;
    private int parsedLight, parsedDark;
    Intent timerService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        timerService = new Intent(this, TimerService.class);
        hoursTV = (TextView) findViewById(R.id.hours);
        minutesTV = (TextView) findViewById(R.id.minutes);
        secondsTV = (TextView) findViewById(R.id.seconds);
        startStopButton = (TextView) findViewById(R.id.startTimerOnClick);

        infoPanel = (TextView) findViewById(R.id.infopanel);

        timerOn = false;
        parsedDark = Color.parseColor("#474338");
        parsedLight = Color.parseColor("#725F42");

        setColorTimerAll(parsedLight);

        userTimerActivity = false;
        minutes = 0;
        hours = 0;
        seconds = 0;

        LocalBroadcastManager.getInstance(this).registerReceiver(bReceiver, new IntentFilter("timerStatsFromService"));
       // printTime();
    }
    @Override
    public void onBackPressed() {

       // timer.cancel();

        super.onBackPressed();
      //  moveTaskToBack(false);

    }

    @Override
    protected void onResume() {
        mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);

        Intent myService = new Intent(TimerActivity.this, TimerService.class);
        stopService(timerService);



        super.onResume();

    }



    @Override
    protected void onPause() {
        if (timerOn) {
            timerService.putExtra("sec", seconds);
            timerService.putExtra("min", minutes);
            timerService.putExtra("hours", hours);
            Intent myService = new Intent(TimerActivity.this, TimerService.class);
            startService(timerService);
        }

        mSensorManager.unregisterListener(this);
        super.onPause();
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
                    if (!timerOn) {
                        startTimer(null);
                    } else {
                        timerOn = false;
                        timer.cancel();
                    }
                    covered = false;
                } else if (time - coverTime >= 1000 && covered) {
                    Intent intent = new Intent(TimerActivity.this, StepActivity.class);
                    startActivity(intent);
                    covered = false;
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private BroadcastReceiver bReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            minutes = intent.getIntExtra("min", 0);
            seconds = intent.getIntExtra("sec", 0);
            hours = intent.getIntExtra("hours", 0);
            printTime();
            minutesHolder = true;
            secondsHolder = true;
            hoursHolder = true;
            timerOn = false;
            startTimer(null);

        }
    };
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
            vibrate(300);
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

        }
    }

    public void down(View v) {
        printLabel(3);
        if (!userTimerActivity) {
            printLabel(1);
            vibrate(300);
        } else {
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
        }



    }


    public void hoursClick(View v) {
        if (!timerOn){
            printStartStopButtonText(0);
        }
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
        if (!timerOn){
            printStartStopButtonText(0);

        }
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
        if (!timerOn){
            printStartStopButtonText(0);

        }
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
        // vibrate(100);
        if (!timerOn) {
            //textView.setText("cookr");
        } else {
           // hours++;

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
    private void printStartStopButtonText(int labelType) {
        switch (labelType) {
            case 0:
                startStopButton.setText("START");
                break;
            case 1:
                startStopButton.setText("STOP");
                break;
            case 2:
                startStopButton.setText("FINNISH");
                break;

            default:
                // code block
        }


    }

    private void onTimerStartUI(){
        vibrate(100);
        printStartStopButtonText(1);
        printLabel(0);

        setColorTimerAll(parsedDark);
    }
    private void onTimerFinnishUI(){
        if (timerOn) {
            printTime();
            printStartStopButtonText(2);
            setColorTimerAll(parsedLight);

            vibrate(1000);
        }
    }
    public void timerLogic(){
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

                onTimerFinnishUI();

                timerOn = false;
            }
        }.start();
    }

    public void startTimer(View v) {
        if (timerSet()) {

            if (!timerOn) {
                onTimerStartUI(); //här finns vibration

                timerOn = true;
                timerLogic();

            } else {
                vibrate(500);
                printStartStopButtonText(0);
                timerOn = false;
                timer.cancel();

           }
        } else {
            vibrate(300);
        }
    }


}




