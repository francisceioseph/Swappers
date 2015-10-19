package br.edu.ifce.swappers.swappers.miscellaneous.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.BestPlaceInterface;
import br.edu.ifce.swappers.swappers.model.Place;
import br.edu.ifce.swappers.swappers.webservice.StatisticPlaceService;

/**
 * Created by gracyaneoliveira on 18/10/2015.
 */
public class StatisticPlaceTask extends AsyncTask<Void,Void,ArrayList<Place>> {

    private Context context;
    private BestPlaceInterface bestPlaceInterface;
    private ProgressDialog progressDialog;

    public StatisticPlaceTask(Context context, BestPlaceInterface bestPlaceInterface){
        this.context = context;
        this.bestPlaceInterface = bestPlaceInterface;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.progress_dialog_loading_message));
        progressDialog.show();
    }

    @Override
    protected ArrayList<Place> doInBackground(Void... params) {
        return StatisticPlaceService.getBestPlacesCurrentMonth();
    }

    @Override
    protected void onPostExecute(ArrayList<Place> places) {
        progressDialog.dismiss();
        this.bestPlaceInterface.updateStatisticPlace(places);
    }
}
