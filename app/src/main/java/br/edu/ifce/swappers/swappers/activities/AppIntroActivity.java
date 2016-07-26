package br.edu.ifce.swappers.swappers.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.miscellaneous.tasks.StateCityTask;
import br.edu.ifce.swappers.swappers.miscellaneous.utils.AndroidUtils;

public class AppIntroActivity extends AppIntro {


    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        addSlide(AppIntroFragment.newInstance("Ligue o GPS!", "Encontraremos o ponto de troca mais próximo de você.",
                R.drawable.intro_1, Color.parseColor("#1182DF")));
        addSlide(AppIntroFragment.newInstance("Adote, ", "quando encontrar um bom livro.",
                R.drawable.intro_2, Color.parseColor("#1182DF")));
        addSlide(AppIntroFragment.newInstance("Doe, ", "quando tiver um livro sobrando.",
                R.drawable.intro_3, Color.parseColor("#1182DF")));
        addSlide(AppIntroFragment.newInstance("Acompanhe ", "as doações e adoções, em tempo real.",
                R.drawable.intro_4, Color.parseColor("#1182DF")));

        showSkipButton(true);
        showDoneButton(true);

        setBarColor(Color.parseColor("#1182DF"));
        setSeparatorColor(Color.parseColor("#005FB4"));
        //setFadeAnimation();
        setZoomAnimation();
    }

    @Override
    public void onSkipPressed() {
        doAutoLogin();
    }

    @Override
    public void onDonePressed() {
        doAutoLogin();
    }

    private void doAutoLogin(){
        loadCityStateFromServer();

        if (AndroidUtils.userHasBeenLoaded(this) && AndroidUtils.isNetworkAvailable(this)) {
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
