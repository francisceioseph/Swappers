package br.edu.ifce.swappers.swappers.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.Toast;

import java.util.List;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.model.Book;
import br.edu.ifce.swappers.swappers.model.User;
import br.edu.ifce.swappers.swappers.webservice.UserService;

/**
 * Created by francisco on 21/08/15.
 */

public class UserTask extends AsyncTask<String,Void,User> {
    Context context;
    ProgressDialog progressDialog;
    TaskInterface taskInterface;

    public UserTask(Context context, TaskInterface taskInterface) {
        this.context = context;
        this.taskInterface = taskInterface;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Logando...");
        progressDialog.show();
    }

    @Override
    protected User doInBackground(String... params) {
        return UserService.getUserFromWS(params[0], params[1]);
    }

    @Override
    protected void onPostExecute(User user) {

        if (user != null) {
            MockSingleton.INSTANCE.user = user;

            taskInterface.startNextActivity();
        }
        else {
            SwappersToast.makeText(context, "Login ou senha incorretos!!!", Toast.LENGTH_SHORT).show();
        }
        progressDialog.dismiss();
    }
}
