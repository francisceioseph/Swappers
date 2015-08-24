package br.edu.ifce.swappers.swappers.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.util.List;

import br.edu.ifce.swappers.swappers.model.Place;
import br.edu.ifce.swappers.swappers.webservice.PlaceService;

/**
 * Created by Gracyane on 19/08/2015.
 */
public class PlaceAsyncTask extends AsyncTask<String,String,List<Place>> {
 private PlaceInterface placeInterface;
 private ProgressDialog progressDialog;
 private Context context;

    public PlaceAsyncTask(Context context, PlaceInterface placeInterface){
        this.placeInterface = placeInterface;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Buscando...");
        progressDialog.show();
    }

    @Override
    protected List<Place> doInBackground(String... params) {
        return PlaceService.getPlaceWS(params[0],params[1]);

    }

    @Override
    protected void onPostExecute(List<Place> placeList) {
        placeInterface.updatePlaceNear(placeList);
        progressDialog.dismiss();

        Log.i("SIZE-PLACE", String.valueOf(placeList.size()));
    }
}
