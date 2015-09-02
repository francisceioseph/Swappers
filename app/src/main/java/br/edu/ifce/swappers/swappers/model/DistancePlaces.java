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
    private ArrayList<Place> places=null;

    public DistancePlaces(){}

    public DistancePlaces(ArrayList<Place> places){
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

    public ArrayList<Place> calculateNearPlace(LatLng positionUser){
        ArrayList<Place> placeList = new ArrayList<Place>();
        double distanceBetweenPlaces;

        for (int i=0; i<places.size(); i++){
            distanceBetweenPlaces = distanceBetween(positionUser, new LatLng(places.get(i).getLatitude(), places.get(i).getLongitude()));

            Place place = new Place();
            place.setLatitude(places.get(i).getLatitude());
            place.setLongitude(places.get(i).getLongitude());
            place.setDistance(distanceBetweenPlaces);
            place.setCity(places.get(i).getCity());
            place.setNumber(places.get(i).getNumber());
            place.setId(places.get(i).getId());
            place.setName(places.get(i).getName());
            place.setDistrict(places.get(i).getDistrict());
            place.setStreet(places.get(i).getStreet());
            place.setBooks(places.get(i).getBooks());
            place.setStates(places.get(i).getStates());
            place.setDonation(places.get(i).getDonation());
            place.setRecovered(places.get(i).getRecovered());
            place.setCep(places.get(i).getCep());
            place.setHour_func(places.get(i).getHour_func());
            place.setPhoto2(places.get(i).getPhoto2());

            //placeList.add(new Place(places.get(i).getLatitude(), places.get(i).getLongitude(), distanceBetweenPlaces));
            placeList.add(place);
            Log.i("NOME", places.get(i).getName());
        }

        Collections.sort(placeList);

        return placeList;
    }

}
