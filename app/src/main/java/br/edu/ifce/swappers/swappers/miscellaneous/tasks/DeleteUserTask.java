package br.edu.ifce.swappers.swappers.miscellaneous.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.net.HttpURLConnection;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.miscellaneous.SwappersToast;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.DeleteUserTaskInterface;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.UpdateImageTaskInterface;
import br.edu.ifce.swappers.swappers.model.User;
import br.edu.ifce.swappers.swappers.webservice.UserService;

/**
 * Created by gracyaneoliveira on 11/11/15.
 */
public class DeleteUserTask extends AsyncTask<Integer,Void, Integer> {

    private Context context;
    private ProgressDialog progressDialog;
    private DeleteUserTaskInterface deleteUserTaskInterface;

    public DeleteUserTask(Context context, DeleteUserTaskInterface deleteUserTaskInterface) {
        this.context = context;
        this.deleteUserTaskInterface = deleteUserTaskInterface;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        this.progressDialog = new ProgressDialog(this.context);
        this.progressDialog.setMessage(context.getString(R.string.progress_dialog_update_pwd_message));
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();
    }

    @Override
    protected Integer doInBackground(Integer... params) {
        return UserService.deleteUserService(params[0]);
    }

    @Override
    protected void onPostExecute(Integer status_code) {
        super.onPostExecute(status_code);
        this.progressDialog.dismiss();
        Log.i("#URL_DELETE", String.valueOf(status_code));
        if(status_code== HttpURLConnection.HTTP_OK){
            this.deleteUserTaskInterface.onDeleteUserHadFinished();
        }else{
            SwappersToast.makeText(context,context.getString(R.string.settings_error_delete_user_message),Toast.LENGTH_LONG).show();
        }
    }
}
