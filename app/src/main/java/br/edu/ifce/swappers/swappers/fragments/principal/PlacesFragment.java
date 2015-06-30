package br.edu.ifce.swappers.swappers.fragments.principal;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.util.SwappersToast;


public class PlacesFragment extends Fragment{

    MapView mapView;
    static GoogleMap mapPlace;
    LocationManager locationManager;

    private final LatLng SHOPPING_BENFICA = new LatLng(-3.739126, -38.5402);
    private final LatLng NORTH_SHOPPING = new LatLng(-3.7348059, -38.5662608);
    private final LatLng SHOPPING_IGUATEMI = new LatLng(-3.75529, -38.488498);
    private final LatLng IFCE_FORTALEZA = new LatLng(-3.744197, -38.535877);

    private LatLng myPosition;

    public PlacesFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Listener listener = new Listener();
        long timeUpdate = 5000;
        float distance = 0;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_places, container, false);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, timeUpdate, distance, listener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, timeUpdate, distance, listener);

        Location locationUser = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(locationUser == null){
            Toast toast = SwappersToast.makeText(getActivity(), "Connect the GPS!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            //se o gps estiver desligado, o mapa abrirá em um local previamente definido.
            myPosition = IFCE_FORTALEZA;
        }
        else myPosition = listener.getMyPosition(locationUser);

        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        mapPlace = mapView.getMap();
        mapPlace.getUiSettings().setMyLocationButtonEnabled(true);
        mapPlace.getUiSettings().setMapToolbarEnabled(true);
        mapPlace.setMyLocationEnabled(true);
        mapPlace.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        MapsInitializer.initialize(this.getActivity());
        setUpMap();

        return view;

    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private void setUpMap() {
        mapPlace.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 14));
        mapPlace.animateCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 14));

        mapPlace.addMarker(new MarkerOptions().position(SHOPPING_BENFICA)
                .title("Shopping Benfica")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mapPlace.addMarker(new MarkerOptions().position(NORTH_SHOPPING)
                                        .title("North Shopping")
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mapPlace.addMarker(new MarkerOptions().position(SHOPPING_IGUATEMI)
                .title("Shopping Iguatemi")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

    }

}

    class Listener implements LocationListener {
        float latitudeUser;
        float longitudeUser;
        LatLng userPosition;
        private int changeZoom = 0;

        @Override
        public void onLocationChanged(Location location){
            latitudeUser = (float)location.getLatitude();
            longitudeUser = (float)location.getLongitude();
            userPosition = new LatLng(latitudeUser, longitudeUser);

            if (changeZoom < 1) {
                PlacesFragment.mapPlace.addMarker(new MarkerOptions().position(userPosition).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_portrait)));
                PlacesFragment.mapPlace.moveCamera(CameraUpdateFactory.newLatLngZoom(userPosition, 14));
                PlacesFragment.mapPlace.animateCamera(CameraUpdateFactory.zoomTo(14), 500, null);
                changeZoom++;
            }
        }

        public void onStatusChanged(String provider, int status, Bundle extras){}

        @Override
        public void onProviderEnabled(String provider){

        }

        @Override
        public void onProviderDisabled(String provider){}

        public LatLng getMyPosition(Location location){
            float myPositionLatitude = (float) location.getLatitude();
            float myPositionLongitude = (float) location.getLongitude();
            LatLng myPosition = new LatLng(myPositionLatitude, myPositionLongitude);

            return myPosition;
        }
}

