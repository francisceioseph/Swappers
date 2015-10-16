package br.edu.ifce.swappers.swappers.miscellaneous.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import br.edu.ifce.swappers.swappers.R;
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
        progressDialog.setMessage(context.getString(R.string.register_task_progress_dialog_message));
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
        Toast toast;
        progressDialog.dismiss();
        if (result==201) {
            ti.startNextActivity();
        }else if(result==500){
            toast = Toast.makeText(context,context.getString(R.string.sick_server_error),Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }else if(result==409){
            toast = Toast.makeText(context,context.getString(R.string.duplicate_email_error_message),Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

    }
}
