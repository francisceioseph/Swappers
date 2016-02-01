package br.edu.ifce.swappers.swappers.miscellaneous.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.NotificationTaskInterface;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.StateCityTaskInterface;
import br.edu.ifce.swappers.swappers.model.Notification;
import br.edu.ifce.swappers.swappers.model.StateCity;
import br.edu.ifce.swappers.swappers.webservice.NotificationService;
import br.edu.ifce.swappers.swappers.webservice.StateCityService;

/**
 * Created by gracyaneoliveira on 28/01/16.
 */
public class StateCityTask extends AsyncTask<Void, Void, ArrayList<StateCity>>  {

    private Context context;
    private ProgressDialog progressDialog;
    private StateCityTaskInterface stateCityTaskInterface;

    public StateCityTask(Context context) {
        this.context = context;
        //this.stateCityTaskInterface = stateCityTaskInterface;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

//        this.progressDialog = new ProgressDialog(this.context);
//        this.progressDialog.setMessage(context.getString(R.string.progress_dialog_retrieving_notification_message));
//        this.progressDialog.setCancelable(false);
//        this.progressDialog.show();
    }

    @Override
    protected ArrayList<StateCity> doInBackground(Void... params) {
        return StateCityService.getStateCityList();
    }

    @Override
    protected void onPostExecute(ArrayList<StateCity> stateCityList) {
        super.onPostExecute(stateCityList);

        if(stateCityList!=null){
             MockSingleton.INSTANCE.cityStateList = stateCityList;
            Log.i("SIGLA-ESTADO","task()");
        }

//        this.progressDialog.dismiss();
//        this.stateCityTaskInterface.onReceiveStateCity(stateCityList);
    }
}
