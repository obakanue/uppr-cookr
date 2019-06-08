package com.example.cookr;


import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mProximity;
    private static final int SENSOR_SENSITIVITY = 4;
    String recipeArray[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        ListView recipe_list = findViewById(R.id.listview);
        recipe_list.setOnItemClickListener(listener());
        ListAdapter adapter= recipe_list.getAdapter();
        recipe_list.setAdapter(adapter);
        recipeArray = getResources().getStringArray(R.array.list_items);

    }

    private OnItemClickListener listener(){
        return new OnItemClickListener(){
            public void onItemClick(AdapterView<?> p, View v, int x, long id){
                String recipe = p.getItemAtPosition(x).toString();

                /*
                if("PANCAKES".equals(recipe)){
                    // Intent intent = new Intent(RecipeActivity.this, RecipeOverview.class);
                    Intent intent = new Intent(RecipeActivity.this, GeneralStepActivity.class);
                    startActivity(intent);
                }
                */

                Intent intent = new Intent(RecipeActivity.this, GeneralStepActivity.class);
                Bundle b = new Bundle();

                switch(recipe) {
                    case "PANCAKES":
                        b.putInt("recipe", 0);
                        intent.putExtras(b);
                        startActivity(intent);
                        // finish(); ?
                        break;
                    case "LASAGNA":
                        b.putInt("recipe", 1);
                        intent.putExtras(b);
                        startActivity(intent);
                        break;
                    case "SWEDISH MEATBALLS":
                        b.putInt("recipe", 2);
                        intent.putExtras(b);
                        startActivity(intent);
                        break;
                    case "HAMBURGERS":
                        b.putInt("recipe", 3);
                        intent.putExtras(b);
                        startActivity(intent);
                        break;
                    case "VEGETABLE SOUP":
                        b.putInt("recipe", 4);
                        intent.putExtras(b);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        };
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

            } else {
                //far

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}