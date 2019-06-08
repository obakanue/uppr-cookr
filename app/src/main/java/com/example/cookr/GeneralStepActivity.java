package com.example.cookr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class GeneralStepActivity extends AppCompatActivity implements SensorEventListener {

    private Bundle bundle;
    private int recipeID = 0;
    private RecipeReader reader;
    private ArrayList<Instruction> instructions;
    private TextView tvTitle;
    private int stepCounter = 1;

    private SensorManager mSensorManager;
    private Sensor mProximity;
    private Sensor mAccelerometer;
    private static final int SENSOR_SENSITIVITY = 4;
    private boolean turned = false, covered = false;
    private float accYValue;
    private long coverTime, time;

    private ConstraintLayout constraintLayoutButtons;
    private ConstraintLayout constraintLayoutInstruction;
    private TextView textViewInstruction;
    private CardView cardViewNextBig;
    private CardView cardViewPrevBig;

    private CardView cardViewTimerButton;
    private ConstraintLayout constraintLayoutClock;

    public boolean timerOn = false;
    private int hours, minutes, seconds;
    private TextView textViewHours, textViewMinutes, textViewSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_step);

        bundle = getIntent().getExtras();
        reader = new RecipeReader(this);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        constraintLayoutButtons = (ConstraintLayout) findViewById(R.id.constraintLayoutButtons);
        constraintLayoutInstruction = (ConstraintLayout) findViewById(R.id.constraintLayoutInstruction);
        textViewInstruction = (TextView) findViewById(R.id.textViewInstruction);
        cardViewNextBig = (CardView) findViewById(R.id.cardViewNextBig);
        cardViewPrevBig = (CardView) findViewById(R.id.cardViewPrevBig);

        cardViewTimerButton = (CardView) findViewById(R.id.cardViewTimerButton);
        constraintLayoutClock = (ConstraintLayout) findViewById(R.id.constraintLayoutClock);
        textViewHours = (TextView) findViewById(R.id.textViewHours);
        textViewMinutes = (TextView) findViewById(R.id.textViewMinutes);
        textViewSeconds = (TextView) findViewById(R.id.textViewSeconds);

        if(bundle != null) {
            recipeID = bundle.getInt("recipe");
        }

        try {
            instructions = reader.loadRecipeFromAssets("recipes/recipe" + recipeID + ".txt");
        } catch(Exception e) {
            e.printStackTrace();
        }

        tvTitle = (TextView) findViewById(R.id.textViewRecipeTitle);

        tvTitle.setText(instructions.get(0).evaluate());
        displayViews();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
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
                    // jump forward in recipe
                    next();
                    covered = false;
                }
                else if (time - coverTime >= 1000 && covered) {
                    Intent intent = new Intent(GeneralStepActivity.this, TimerActivity.class);
                    startActivity(intent);
                    covered = false;
                }
            }
        } else if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accYValue = event.values[1];
            if (accYValue > 1) {
                float xValue = event.values[0];
                if (xValue < -4) {
                    next();
                } else if (xValue > 4) {
                    prev();
                } else {
                    turned = false;
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public void next(View v) {
        next();
    }

    private void next() {
        stepCounter++;
        displayViews();
    }

    public void prev(View v) {
        prev();
    }

    private void prev() {
        stepCounter--;
        displayViews();
    }

    private void displayViews() {
        if(stepCounter < instructions.size()) {
            if (instructions.get(stepCounter) instanceof StepInstruction) {
                cardViewTimerButton.setVisibility(View.GONE);
                constraintLayoutClock.setVisibility(View.GONE);

                constraintLayoutButtons.setVisibility(View.GONE);
                cardViewNextBig.setVisibility(View.GONE);
                cardViewPrevBig.setVisibility(View.GONE);
                constraintLayoutInstruction.setVisibility(View.VISIBLE);
                textViewInstruction.setText(instructions.get(stepCounter).evaluate());

                if (stepCounter == 1) {
                    cardViewNextBig.setVisibility(View.VISIBLE);
                } else if (stepCounter == instructions.size() - 1) {
                    cardViewPrevBig.setVisibility(View.VISIBLE);
                } else {
                    constraintLayoutButtons.setVisibility(View.VISIBLE);
                    constraintLayoutInstruction.setVisibility(View.VISIBLE);
                }
            } else if (instructions.get(stepCounter) instanceof TimerInstruction) {
                constraintLayoutButtons.setVisibility(View.GONE);
                constraintLayoutInstruction.setVisibility(View.GONE);

                String[] clock = instructions.get(stepCounter).evaluate().split("\\.");
                textViewHours.setText(clock[0]);
                textViewMinutes.setText(clock[1]);
                textViewSeconds.setText(clock[2]);

                hours = Integer.parseInt(clock[0]);
                minutes = Integer.parseInt(clock[1]);
                seconds = Integer.parseInt(clock[2]);

                cardViewTimerButton.setVisibility(View.VISIBLE);
                constraintLayoutClock.setVisibility(View.VISIBLE);
            }
        } else {
            System.out.println("Weird error");
        }
    }

    public void timerStart(View v) {
        if (!timerOn) {
            timerOn = true;
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
                    timerOn = false;
                    MediaPlayer ring = MediaPlayer.create(GeneralStepActivity.this, R.raw.alarm);
                    ring.start();
                }

            }.start();
        }
    }

    private void printMin() {
        textViewMinutes = (TextView) findViewById(R.id.textViewMinutes);
        String formattedTime = String.format("%02d", minutes);
        textViewMinutes.setText(formattedTime);

    }

    private void printSec() {
        textViewSeconds = (TextView) findViewById(R.id.textViewSeconds);
        String formattedTime = String.format("%02d", seconds);
        textViewSeconds.setText(formattedTime);
    }
}
