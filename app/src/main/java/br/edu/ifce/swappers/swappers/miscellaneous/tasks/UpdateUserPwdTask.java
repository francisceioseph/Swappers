package br.edu.ifce.swappers.swappers.miscellaneous.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.net.HttpURLConnection;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.miscellaneous.SwappersToast;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.UpdatePwdTaskInterface;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.UploadReviewTaskInterface;
import br.edu.ifce.swappers.swappers.model.Review;
import br.edu.ifce.swappers.swappers.model.User;
import br.edu.ifce.swappers.swappers.webservice.ReviewService;
import br.edu.ifce.swappers.swappers.webservice.UserService;

/**
 * Created by gracyaneoliveira on 11/11/15.
 */
public class UpdateUserPwdTask extends AsyncTask<User,Void, Integer> {
    private Context context;
    private ProgressDialog progressDialog;
    private UpdatePwdTaskInterface updatePwdTaskInterface;

    public UpdateUserPwdTask(Context context, UpdatePwdTaskInterface updatePwdTaskInterface) {
        this.context = context;
        this.updatePwdTaskInterface = updatePwdTaskInterface;
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
    protected Integer doInBackground(User... params) {
        return UserService.updatePwdUserService(params[0]);
    }

    @Override
    protected void onPostExecute(Integer status_code) {
        super.onPostExecute(status_code);

        this.progressDialog.dismiss();
        if(status_code== HttpURLConnection.HTTP_OK){
            this.updatePwdTaskInterface.onUpdatePwdHadFinished();
        }else{
            SwappersToast.makeText(context,context.getString(R.string.settings_error_update_password_message),Toast.LENGTH_LONG).show();
        }
    }
}
