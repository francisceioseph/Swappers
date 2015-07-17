package br.edu.ifce.swappers.swappers.model;

import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Joamila on 16/07/2015.
 */
public class DistancePlaces {

    public static float distanceBetween(LatLng positionUser, LatLng positionPoint){
        Location locationUser = new Location(LocationManager.GPS_PROVIDER);
        Location locationPoint = new Location(LocationManager.GPS_PROVIDER);

        locationUser.setLatitude(positionUser.latitude);
        locationUser.setLongitude(positionUser.longitude);

        locationPoint.setLatitude(positionPoint.latitude);
        locationPoint.setLongitude(positionPoint.longitude);

        return locationUser.distanceTo(locationPoint);
    }

    public static void ordenar(double[] vetor, double[] id)
    {
        ordenar(vetor, id, 0, vetor.length - 1);
    }

    private static void ordenar(double[] vetor, double[] id, int inicio, int fim)
    {
        if (inicio < fim)
        {
            int posicaoPivo = separar(vetor, id, inicio, fim);
            ordenar(vetor, id, inicio, posicaoPivo - 1);
            ordenar(vetor, id, posicaoPivo + 1, fim);
        }
    }

    private static int separar(double[] vetor, double[] id, int inicio, int fim)
    {
        double pivo = vetor[inicio];
        int i = inicio + 1, f = fim;
        while (i <= f)
        {
            if (vetor[i] <= pivo)
                i++;
            else if (pivo < vetor[f])
                f--;
            else
            {
                double troca = vetor[i];
                double troca_id = id[i];
                vetor[i] = vetor[f];
                id[i] = id[f];
                vetor[f] = troca;
                id[f] = troca_id;
                i++;
                f--;
            }
        }
        vetor[inicio] = vetor[f];
        id[inicio] = id[f];
        vetor[f] = pivo;
        id[f] = id[inicio];
        return f;
    }

    public static void showMarker(LatLng position, GoogleMap googleMap){
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 17));
    }
}
