package br.edu.ifce.swappers.swappers.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.miscellaneous.tasks.StateCityTask;
import br.edu.ifce.swappers.swappers.miscellaneous.utils.AndroidUtils;


public class SplashActivity extends Activity{


    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true);
                if (isFirstRun) {
                    Intent intent = new Intent(SplashActivity.this, AppIntroActivity.class);
                    startActivity(intent);
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).commit();
                }
                else{
                    doAutoLogin();
                }
            }
        }, SPLASH_TIME_OUT);
    }

    private void doAutoLogin(){
        loadCityStateFromServer();

        if (AndroidUtils.userHasBeenLoaded(this) && AndroidUtils.isNetworkAvailable(this)) {
            MockSingleton.INSTANCE.user = AndroidUtils.loadUser(this);
            AndroidUtils.startMainActivity(this);
        }
        else {
            FacebookSdk.sdkInitialize(getApplicationContext());
            AppEventsLogger.activateApp(this);
            AndroidUtils.startSignInActivity(this);
        }
    }

    private void loadCityStateFromServer(){
        StateCityTask stateCityTask = new StateCityTask(this);
        stateCityTask.execute();
    }
}
