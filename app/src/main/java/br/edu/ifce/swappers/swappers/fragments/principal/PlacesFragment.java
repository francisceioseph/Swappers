package br.edu.ifce.swappers.swappers.fragments.principal;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import br.edu.ifce.swappers.swappers.R;

public class PlacesFragment extends Fragment {

    MapView mapView;
    GoogleMap mapPlace;

    private final LatLng SHOPPING_BENFICA = new LatLng(-3.739126, -38.5402);
    private final LatLng NORTH_SHOPPING = new LatLng(-3.7348059, -38.5662608);
    private final LatLng SHOPPING_IGUATEMI = new LatLng(-3.75529, -38.488498);
    private final LatLng IFCE_FORTALEZA = new LatLng(-3.744197, -38.535877);


    public PlacesFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_places, container, false);

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        mapPlace = mapView.getMap();
        mapPlace.getUiSettings().setMyLocationButtonEnabled(true);
        mapPlace.setMyLocationEnabled(true);
        mapPlace.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
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
        LatLng myPosition = IFCE_FORTALEZA;

        mapPlace.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mapPlace.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 14));
        mapPlace.animateCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 14));

        mapPlace.addMarker(new MarkerOptions().position(myPosition).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person)));

        mapPlace.addMarker(new MarkerOptions().position(SHOPPING_BENFICA)
                                        .title("Shopping Benfica")
                                        .visible(true)
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        mapPlace.addMarker(new MarkerOptions().position(NORTH_SHOPPING)
                                        .title("North Shopping")
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        mapPlace.addMarker(new MarkerOptions().position(SHOPPING_IGUATEMI)
                                        .title("Shopping Iguatemi")
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

    }


}
