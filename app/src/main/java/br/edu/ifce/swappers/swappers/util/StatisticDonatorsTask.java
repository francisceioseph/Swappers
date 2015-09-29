package br.edu.ifce.swappers.swappers.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.model.User;
import br.edu.ifce.swappers.swappers.webservice.StatisticDonatorsService;

/**
 * Created by gracyaneoliveira on 28/09/2015.
 */
public class StatisticDonatorsTask extends AsyncTask<Void,Void,ArrayList<User>> {
    private Context context;
    private DonatorsInterface donatorsInterface;
    private ProgressDialog progressDialog;

    public StatisticDonatorsTask(Context context,DonatorsInterface donatorsInterface){
        this.context = context;
        this.donatorsInterface = donatorsInterface;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading donators, wait...");
        progressDialog.show();
    }

    @Override
    protected ArrayList<User> doInBackground(Void... params) {
        return StatisticDonatorsService.getStatisticDonatorsForMonthWS();
    }

    @Override
    protected void onPostExecute(ArrayList<User> users) {
        progressDialog.dismiss();
        this.donatorsInterface.updateStatisticDonators(users);
    }
}
