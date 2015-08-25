package br.edu.ifce.swappers.swappers.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;

import br.edu.ifce.swappers.swappers.model.Place;

/**
 * Created by Joamila on 23/08/2015.
 */
public class MarkerAsyncTask extends AsyncTask<Double,Double,Place> {
    private PlaceInterface placeInterface;
    private ProgressDialog progressDialog;
    private Context context;
    private GoogleMap.OnMarkerClickListener onMarkerClickListener;

    public MarkerAsyncTask(Context context, GoogleMap.OnMarkerClickListener markerClickListener){
        this.context = context;
        this.onMarkerClickListener = markerClickListener;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Acessando ponto de troca...");
        progressDialog.show();
    }

    @Override
    protected Place doInBackground(Double... params) {
        return MarkerService.getDetailPlaceWS(params[0], params[1]);
    }

    @Override
    protected void onPostExecute(Place placeInformation) {
        placeInterface.getDetailPlace(placeInformation);
        progressDialog.dismiss();

    }
}
