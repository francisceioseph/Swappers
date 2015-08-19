package br.edu.ifce.swappers.swappers.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.util.List;

import br.edu.ifce.swappers.swappers.model.Place;
import br.edu.ifce.swappers.swappers.webservice.PlaceService;

/**
 * Created by Bolsista on 19/08/2015.
 */
public class PlaceAsyncTask extends AsyncTask<String,String,List<Place>> {

    @Override
    protected List<Place> doInBackground(String... params) {
        return PlaceService.getPlaceWS(params[0],params[1]);
    }

    @Override
    protected void onPostExecute(List<Place> placeList) {
    }
}
