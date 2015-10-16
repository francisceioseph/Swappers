package br.edu.ifce.swappers.swappers.miscellaneous.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import br.edu.ifce.swappers.swappers.model.User;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.TaskInterface;
import br.edu.ifce.swappers.swappers.webservice.UserService;

/**
 * Created by gracyane on 04/06/2015.
 */
public class RegisterTask extends AsyncTask<User,String,Integer>{
    private Context context;
    private ProgressDialog progressDialog;
    private TaskInterface ti;


    public RegisterTask(Context context, TaskInterface ti){
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
    protected Integer doInBackground(User... params) {
        return UserService.registerUserWithWS(context,params[0]);
    }

    @Override
    protected void onProgressUpdate(String... params) {
    }

    @Override
    protected void onPostExecute(Integer result) {
        Log.i("STATUSCODE", String.valueOf(result));
        Toast toast;
        progressDialog.dismiss();
        if (result==201) {
            ti.startNextActivity();
        }else if(result==500){
            toast = Toast.makeText(context,"Erro no servidor. Tente novamente mais tarde.",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }else if(result==409){
            toast = Toast.makeText(context,"Atenção! Este email já existe em nosso sistema.",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

    }
}
