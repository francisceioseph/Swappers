package br.edu.ifce.swappers.swappers.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.R;
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

        //TODO Load user information for shared preferences

        if (AndroidUtils.userHasBeenLoaded(this)) {
            MockSingleton.INSTANCE.user = AndroidUtils.loadUser(this);

            loadMainActivity();
        }
        else {
            loadSignInActivity();
        }
    }

    private void loadMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void loadSignInActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
