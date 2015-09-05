package br.edu.ifce.swappers.swappers.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.Toast;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.fragments.principal.PlacesFragment;
import br.edu.ifce.swappers.swappers.model.Place;
import br.edu.ifce.swappers.swappers.webservice.PlaceService;
import br.edu.ifce.swappers.swappers.webservice.PlaceSingleton;

/**
 * Created by Gracyane on 19/08/2015.
 */
public class PlaceAsyncTask extends AsyncTask<String,String,ArrayList<Place>> {
    private PlaceInterface placeInterface;
    private ProgressDialog progressDialog;
    private Context context;
    private PlaceSingleton placeSingleton;

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
    protected ArrayList<Place> doInBackground(String... params) {
        return PlaceService.getPlaceWS(params[0],params[1]);

    }

    @Override
    protected void onPostExecute(ArrayList<Place> placeList) {
        progressDialog.dismiss();
        if(PlaceService.getResponseCode() == 200){
            placeInterface.updatePlaceNear(placeList);

            placeSingleton = PlaceSingleton.getInstance();
            placeSingleton.setPlaces(placeList);
        }
        else{
            Toast toast = SwappersToast.makeText(context, "Erro no servidor. Tente novamente mais tarde!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

    }
}
