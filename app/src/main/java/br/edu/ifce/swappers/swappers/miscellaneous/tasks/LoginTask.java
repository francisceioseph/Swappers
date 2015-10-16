package br.edu.ifce.swappers.swappers.miscellaneous.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.Toast;

import br.edu.ifce.swappers.swappers.miscellaneous.SwappersToast;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.TaskInterface;
import br.edu.ifce.swappers.swappers.webservice.UserService;

/**
 * Created by gracyane on 12/06/2015.
 */
public class LoginTask extends AsyncTask<String,String,Boolean> {

    private Context context;
    private ProgressDialog progressDialog;
    private TaskInterface ti;


    public LoginTask(Context context, TaskInterface ti){
        this.ti = ti;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Processando...");
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        return UserService.checkLoginWS(params[0], params[1]);
    }

    @Override
    protected void onProgressUpdate(String... params) {
    }

    @Override
    protected void onPostExecute(Boolean result) {
        Toast toast;
        progressDialog.dismiss();
        if (result){
            ti.startNextActivity();
            toast = SwappersToast.makeText(context, "Login efetuado com sucesso!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }else {
            toast = SwappersToast.makeText(context,"Atenção! E-mail ou senha incorreto.",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}
