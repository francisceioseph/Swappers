package br.edu.ifce.swappers.swappers.activities.MVP;

import android.content.Context;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.miscellaneous.tasks.StateCityTask;
import br.edu.ifce.swappers.swappers.miscellaneous.utils.AndroidUtils;

import static android.content.Context.MODE_PRIVATE;

public class SplashPresenter {

    private Context context;
    private SplashContract.View view;

    public SplashPresenter(SplashContract.View view){
        this.view = view;
        context = (Context) view;
    }


    public void handleLogin(){
        boolean isFirstRun = context.getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true);
        if (isFirstRun) {
            context.getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).apply();
            view.startLogin();
        }
        else{
            doAutoLogin();
        }
    }

    private void doAutoLogin(){
        loadCityStateFromServer();

        if (AndroidUtils.userHasBeenLoaded(context) && AndroidUtils.isNetworkAvailable(context)) {
            MockSingleton.INSTANCE.user = AndroidUtils.loadUser(context);
            AndroidUtils.startMainActivity(context);
        }
        else {
            AndroidUtils.startSignInActivity(context);
        }
    }

    private void loadCityStateFromServer(){
        StateCityTask stateCityTask = new StateCityTask(context);
        stateCityTask.execute();
    }
}
