package br.edu.ifce.swappers.swappers.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

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
                doAutoLogin();
            }
        }, SPLASH_TIME_OUT);
    }

    private void doAutoLogin(){
        if (AndroidUtils.userHasBeenLoaded(this) && AndroidUtils.isNetworkAvailable(this)) {
            loadCityStateFromServer();
            MockSingleton.INSTANCE.user = AndroidUtils.loadUser(this);
            AndroidUtils.startMainActivity(this);
        }
        else {
            AndroidUtils.startSignInActivity(this);
        }
    }

    private void loadCityStateFromServer(){
        StateCityTask stateCityTask = new StateCityTask(this);
        stateCityTask.execute();
    }
}
