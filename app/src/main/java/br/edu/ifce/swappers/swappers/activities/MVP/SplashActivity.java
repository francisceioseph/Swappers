package br.edu.ifce.swappers.swappers.activities.MVP;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.activities.AppIntroActivity;


public class SplashActivity extends Activity implements SplashContract.View {

    private SplashPresenter presenter;

    private static final int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        presenter = new SplashPresenter(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.handleLogin();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    public void startLogin() {
        Intent intent = new Intent(SplashActivity.this, AppIntroActivity.class);
        startActivity(intent);
    }
}
