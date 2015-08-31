package br.edu.ifce.swappers.swappers.model;

import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Joamila on 16/07/2015.
 */
public class DistancePlaces{
    private List<Place> places=null;

    public DistancePlaces(){}

    public DistancePlaces(List<Place> places){
        this.places = places;
    }

    public float distanceBetween(LatLng positionUser, LatLng positionPoint){
        Location locationUser = new Location(LocationManager.GPS_PROVIDER);
        Location locationPoint = new Location(LocationManager.GPS_PROVIDER);

        locationUser.setLatitude(positionUser.latitude);
        locationUser.setLongitude(positionUser.longitude);

        locationPoint.setLatitude(positionPoint.latitude);
        locationPoint.setLongitude(positionPoint.longitude);

        return locationUser.distanceTo(locationPoint);
    }

    public List<Place> calculateNearPlace(LatLng positionUser){
        List<Place> placeList = new ArrayList<Place>();
        double distanceBetweenPlaces;

        for (int i=0; i<places.size(); i++){
            distanceBetweenPlaces = distanceBetween(positionUser, new LatLng(places.get(i).getLatitude(), places.get(i).getLongitude()));
            placeList.add(new Place(places.get(i).getLatitude(), places.get(i).getLongitude(), distanceBetweenPlaces));
            Log.i("NOME", places.get(i).getName());
        }

        Collections.sort(placeList);

        return placeList;
    }

}
