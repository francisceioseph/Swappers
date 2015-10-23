package br.edu.ifce.swappers.swappers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.miscellaneous.utils.AndroidUtils;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.TaskInterface;
import br.edu.ifce.swappers.swappers.miscellaneous.tasks.UserTask;

public class LoginActivity extends AppCompatActivity implements TaskInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Button signInButton = (Button) findViewById(R.id.sign_in_button);
        Button signUpButton = (Button) findViewById(R.id.sign_up_button);

        /*Essa linha serve para esconder o teclado, assim o usuário não fica apertando o botão de voltar para escondê-lo.*/
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        if (toolbar != null){
            setSupportActionBar(toolbar);
        }

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyInternetAndMakeLogin();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegisterActivity();
            }
        });
    }

    //Função a ser usado quando se quiser fazer login com WS
    public void verifyInternetAndMakeLogin(){
        if(AndroidUtils.isNetworkAvailable(this)){
            makeLoginTask();
        }
        else {
            AndroidUtils.makeDialog(this,
                    getString(R.string.dialog_error_title),
                    getString(R.string.internet_connection_error_message)).show();
        }
    }

    @Override
    public void startNextActivity() {
        AndroidUtils.createUser(this, MockSingleton.INSTANCE.user);
        AndroidUtils.startMainActivity(this);
    }

    public void startRegisterActivity(){
        Intent registerActivityIntent = new Intent(this, RegisterActivity.class);

        if(AndroidUtils.isNetworkAvailable(this)){
            this.startActivity(registerActivityIntent);
        }
        else {
            AndroidUtils.makeDialog(this,
                    getString(R.string.dialog_error_title),
                    getString(R.string.internet_connection_error_message)).show();
        }
    }

    public void makeLoginTask(){
        EditText emailEditText = (EditText) findViewById(R.id.email_edit_text);
        EditText passwordEditText = (EditText) findViewById(R.id.password_edit_text);

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if(AndroidUtils.userHasBeenLoaded(this)){
            MockSingleton.INSTANCE.user = AndroidUtils.loadUser(this);
        }
        else {

            //String digest = DigestUtils.sha256Hex(password.getBytes());
            //String pwdCodec = new String(Hex.encodeHex(digest.getBytes()));
            //String passwordCodec = new String(Hex.encodeHex(DigestUtils.sha256Hex(password.getBytes()).getBytes()));
            String passwordCodec = new String(Hex.encodeHex(DigestUtils.sha256(password.getBytes())));

            UserTask userTask = new UserTask(this, this);
            userTask.execute(email,passwordCodec);
        }
    }

}
