package com.example.cookr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;

public class RecipeOverview extends AppCompatActivity implements SensorEventListener{
    private SensorManager mSensorManager;
    private Sensor mProximity;
    private static final int SENSOR_SENSITIVITY = 4;
    private TextView distance, startStepButton, textIngredients, textPortions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_overview);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        startStepButton = (TextView) findViewById(R.id.startStepsOnClick);
        textIngredients = (TextView) findViewById(R.id.ingredientText);
        textPortions = (TextView) findViewById(R.id.portionerText);

        textIngredients.setInputType(InputType.TYPE_NULL);
        textPortions.setInputType(InputType.TYPE_NULL);
    }

    public void startStepButton(View v) {
        Intent intent = new Intent(this, StepActivity.class);
        startActivity(intent);

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
                //near
                //distance.setText("Near");
            } else {
                //far
                //distance.setText("Far");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
