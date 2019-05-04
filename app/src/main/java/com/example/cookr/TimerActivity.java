package com.example.cookr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;


public class TimerActivity extends AppCompatActivity {
    TextView textView;
    public int counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        textView = (TextView)findViewById(R.id.tickingTimer);
        counter = 0;
    }
    public void up(View v) {
        counter ++;
        textView.setText(String.valueOf(counter));

    }

    public void down(View v) {
        if (counter > 0) {
            counter--;
            textView.setText(String.valueOf(counter));
        }
    }
    public void startTimer(View v) {
        new CountDownTimer(counter*1000, 1000){
            public void onTick(long millisUntilFinished){
                textView.setText(String.valueOf(counter));
                counter--;
            }
            public  void onFinish(){
                textView.setText("FINISH!!");
            }
        }.start();

    }
}
