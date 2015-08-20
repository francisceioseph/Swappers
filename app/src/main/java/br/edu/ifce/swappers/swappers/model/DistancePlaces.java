package br.edu.ifce.swappers.swappers.model;

import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Joamila on 16/07/2015.
 */
public class DistancePlaces{

    private final int ROWS = 4; //quantidade de pontos de troca
    private double matrixCoordinates[][] = {{-3.734421, -38.655867},
                                            {-3.739126, -38.5402},
                                            {-3.7348059, -38.5662608},
                                            {-3.75529, -38.535877}};

    public float distanceBetween(LatLng positionUser, LatLng positionPoint){
        Location locationUser = new Location(LocationManager.GPS_PROVIDER);
        Location locationPoint = new Location(LocationManager.GPS_PROVIDER);

        locationUser.setLatitude(positionUser.latitude);
        locationUser.setLongitude(positionUser.longitude);

        locationPoint.setLatitude(positionPoint.latitude);
        locationPoint.setLongitude(positionPoint.longitude);

        return locationUser.distanceTo(locationPoint);
    }

    public void calculateNearPlace(LatLng positionUser, GoogleMap map){
        List<Place> placeList = new ArrayList<Place>();
        double coordinateX;
        double coordinateY;
        double distanceBetweenPlaces;

        for (int i=0; i<ROWS; i++){
            distanceBetweenPlaces = distanceBetween(positionUser, new LatLng(matrixCoordinates[i][0], matrixCoordinates[i][1]));
            placeList.add(new Place(matrixCoordinates[i][0], matrixCoordinates[i][1], distanceBetweenPlaces));
        }

        Collections.sort(placeList);

        Place nearPlace = placeList.get(0);
        coordinateX = nearPlace.getLatitude();
        coordinateY = nearPlace.getLongitude();

        showMarker(new LatLng(coordinateX, coordinateY), map);
    }

    public void showMarker(LatLng position, GoogleMap googleMap){
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 17));
    }

}
