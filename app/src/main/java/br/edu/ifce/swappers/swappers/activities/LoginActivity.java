package br.edu.ifce.swappers.swappers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.util.AndroidUtils;
import br.edu.ifce.swappers.swappers.util.LoginTask;
import br.edu.ifce.swappers.swappers.util.SwappersToast;
import br.edu.ifce.swappers.swappers.util.TaskInterface;

public class LoginActivity extends AppCompatActivity implements TaskInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Button signInButton = (Button) findViewById(R.id.sign_in_button);
        Button signUpButton = (Button) findViewById(R.id.sign_up_button);

        if (toolbar != null){
            setSupportActionBar(toolbar);
        }

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AndroidUtils.isNetworkAvailable(getApplicationContext())){
                    makeLoginTask();
                }else {
                Toast toast = SwappersToast.makeText(getApplicationContext(), "Verifique sua conexão!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.startRegisterActivity();
            }
        });
    }

    @Override
    public void startNextActivity() {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(mainActivityIntent);
    }

    public void startRegisterActivity(){
        Intent registerActivityIntent = new Intent(this, RegisterActivity.class);
        //registerActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        if(AndroidUtils.isNetworkAvailable(this)){
            this.startActivity(registerActivityIntent);
        }else {
            Toast toast = SwappersToast.makeText(this, "Verifique sua conexão!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public void makeLoginTask(){
        EditText emailEditText = (EditText) findViewById(R.id.email_edit_text);
        EditText passwordEditText = (EditText) findViewById(R.id.password_edit_text);

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        LoginTask loginTask = new LoginTask(this,this);
        loginTask.execute(email,password);
    }

}
