package br.edu.ifce.swappers.swappers;

import android.app.Application;

import com.ubertesters.sdk.Ubertesters;

/**
 * Created by francisco on 14/11/15.
 */
public class SwappersApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Ubertesters.initialize(this);
    }
}
