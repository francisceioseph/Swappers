package br.edu.ifce.swappers.swappers.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.Toast;

import br.edu.ifce.swappers.swappers.model.Place;
import br.edu.ifce.swappers.swappers.webservice.MarkerService;

/**
 * Created by Joamila on 23/08/2015.
 */
public class MarkerAsyncTask extends AsyncTask<Integer,Double,Place> {
    private PlaceInterface placeInterface;
    private ProgressDialog progressDialog;
    private Context context;

    public MarkerAsyncTask(Context context, PlaceInterface placeInterface){
        this.context = context;
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
        progressDialog.dismiss();
        if(placeInformation!=null){
            //placeInterface.getDetailPlace(placeInformation);
        }else{
            Toast toast = SwappersToast.makeText(context,"Erro no servidor! Tente novamente mais tarde!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}
