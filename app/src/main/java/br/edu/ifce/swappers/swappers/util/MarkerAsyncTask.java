package br.edu.ifce.swappers.swappers.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;

import br.edu.ifce.swappers.swappers.model.Place;

/**
 * Created by Joamila on 23/08/2015.
 */
public class MarkerAsyncTask extends AsyncTask<Integer,Double,Place> {
    private PlaceInterface placeInterface;
    private ProgressDialog progressDialog;
    private Context context;
    private GoogleMap.OnMarkerClickListener onMarkerClickListener;

    public MarkerAsyncTask(Context context, PlaceInterface placeInterface){
        this.context = context;
       // this.onMarkerClickListener = markerClickListener;
        this.placeInterface = placeInterface;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Acessando ponto de troca...");
        progressDialog.show();
    }

    @Override
    protected Place doInBackground(Integer... params) {
        return MarkerService.getDetailPlaceWS(params[0]);
    }

    @Override
    protected void onPostExecute(Place placeInformation) {
        Log.i("onPostExecute",placeInformation.getName());
        placeInterface.getDetailPlace(placeInformation);
        progressDialog.dismiss();
    }
}
