package com.example.cookr;


import android.app.Service;
import android.content.Intent;

import android.os.CountDownTimer;
import android.os.IBinder;


import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * Created by Tutlane on 02-08-2017.
 */

public class TimerService extends Service {
    CountDownTimer timer;
    int totalTime;
    public int seconds, minutes, hours;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        seconds = intent.getIntExtra("sec",0);
        minutes = intent.getIntExtra("min",0);
        hours = intent.getIntExtra("hours",0);
        timerLogic();
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        timer.cancel();
        sendBroadcast();
        super.onDestroy();


    }

    private void sendBroadcast (){
        Intent intent = new Intent ("timerStatsFromService"); //put the same message as in the filter you used in the activity when registering the receiver
        intent.putExtra("min", minutes);
        intent.putExtra("hours", hours);
        intent.putExtra("sec", seconds);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
    public void timerLogic(){
        totalTime = seconds + minutes * 60 + hours * 3600;

        timer = new CountDownTimer(totalTime * 1000, 2100) {
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



            }

            public void onFinish() {

            }
        }.start();
    }


}