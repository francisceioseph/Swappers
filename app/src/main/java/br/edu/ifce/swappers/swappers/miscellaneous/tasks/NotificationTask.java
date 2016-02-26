package br.edu.ifce.swappers.swappers.miscellaneous.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.NotificationTaskInterface;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.RetrieveReviewsTaskInterface;
import br.edu.ifce.swappers.swappers.model.Notification;
import br.edu.ifce.swappers.swappers.model.Review;
import br.edu.ifce.swappers.swappers.webservice.NotificationService;
import br.edu.ifce.swappers.swappers.webservice.ReviewService;

/**
 * Created by gracyaneoliveira on 28/01/16.
 */
public class NotificationTask extends AsyncTask<String, Void, ArrayList<Notification>>  {

    private Context context;
    private ProgressDialog progressDialog;
    private NotificationTaskInterface notificationTaskInterface;

    public NotificationTask(Context context, NotificationTaskInterface notificationTaskInterface) {
        this.context = context;
        this.notificationTaskInterface = notificationTaskInterface;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        this.progressDialog = new ProgressDialog(this.context);
        this.progressDialog.setMessage(context.getString(R.string.progress_dialog_retrieving_notification_message));
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();
    }

    @Override
    protected ArrayList<Notification> doInBackground(String... params) {
        return NotificationService.getNotifications(params[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<Notification> notifications) {
        super.onPostExecute(notifications);
        this.progressDialog.dismiss();
        this.notificationTaskInterface.onReceiveNotification(notifications);
    }
}
