package br.edu.ifce.swappers.swappers.miscellaneous;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Joamila on 24/08/2015.
 */
public class ListenerGPS implements LocationListener {
    float latitudeUser;
    float longitudeUser;
    LatLng userPosition;
    private int changeZoom = 0;

    @Override
    public void onLocationChanged(Location location) {
        latitudeUser = (float)location.getLatitude();
        longitudeUser = (float)location.getLongitude();
        userPosition = new LatLng(latitudeUser, longitudeUser);

        //if (changeZoom < 1) {
            //PlacesFragment.getMapPlace().addMarker(new MarkerOptions().position(userPosition).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_history)));
            //PlacesFragment.getMapPlace().moveCamera(CameraUpdateFactory.newLatLngZoom(userPosition, 16));
            //PlacesFragment.getMapPlace().animateCamera(CameraUpdateFactory.zoomTo(14), 500, null);
            //changeZoom++;
        //}

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
