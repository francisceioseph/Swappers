package br.edu.ifce.swappers.swappers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;


public class SplashActivity extends Activity {

    private final int SPLASH_TIMEOUT_MILI = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
               SplashActivity.this.loadMainClass();
            }
        };

        timer.schedule(task, this.SPLASH_TIMEOUT_MILI);
    }

    private void loadMainClass(){
        Intent intent = new Intent();
        intent.setClass(SplashActivity.this, MainActivity.class);
        startActivity(intent);

        this.finish();
    }
}
