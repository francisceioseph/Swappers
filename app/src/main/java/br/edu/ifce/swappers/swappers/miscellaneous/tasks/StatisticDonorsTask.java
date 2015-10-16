package br.edu.ifce.swappers.swappers.miscellaneous.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.model.User;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.DonorsInterface;
import br.edu.ifce.swappers.swappers.webservice.StatisticDonatorsService;

/**
 * Created by gracyaneoliveira on 28/09/2015.
 */
public class StatisticDonorsTask extends AsyncTask<Void,Void,ArrayList<User>> {
    private Context context;
    private DonorsInterface donorsInterface;
    private ProgressDialog progressDialog;

    public StatisticDonorsTask(Context context, DonorsInterface donorsInterface){
        this.context = context;
        this.donorsInterface = donorsInterface;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.progress_dialog_loading_message));
        progressDialog.show();
    }

    @Override
    protected ArrayList<User> doInBackground(Void... params) {
        return StatisticDonatorsService.getStatisticDonatorsForMonthWS();
    }

    @Override
    protected void onPostExecute(ArrayList<User> users) {
        progressDialog.dismiss();
        this.donorsInterface.updateStatisticDonors(users);
    }
}
