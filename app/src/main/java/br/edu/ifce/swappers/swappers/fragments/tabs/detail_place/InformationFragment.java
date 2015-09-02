package br.edu.ifce.swappers.swappers.fragments.tabs.detail_place;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.model.Place;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformationFragment extends Fragment {
    private TextView streetAddress;
    TextView neighborhoodAndCity;
    TextView stateAndCountry;
    TextView businessHoursDescription;
    private Place place;

    public InformationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_information, container, false);

        streetAddress = (TextView) rootView.findViewById(R.id.street_text_view);
        neighborhoodAndCity = (TextView) rootView.findViewById(R.id.neighborhood_and_city_text_view);
        stateAndCountry = (TextView) rootView.findViewById(R.id.state_and_country_text_view);
        businessHoursDescription = (TextView) rootView.findViewById(R.id.business_hours_description_text_view);

        Intent currentIntent = getActivity().getIntent();
        place = (Place) currentIntent.getSerializableExtra("SELECTED_BOOK_PLACE");

        streetAddress.setText(place.getStreet()+", "+place.getNumber());
        neighborhoodAndCity.setText(place.getDistrict()+", "+place.getCity());
        stateAndCountry.setText(place.getStates()+", "+"Brasil");
        businessHoursDescription.setText(place.getHour_func());
        
        return rootView;
    }

    public TextView getStreetAddress() {
        return streetAddress;
    }

    public TextView getNeighborhoodAndCity() {
        return neighborhoodAndCity;
    }

    public TextView getStateAndCountry() {
        return stateAndCountry;
    }

    public TextView getBusinessHoursDescription() {
        return businessHoursDescription;
    }

}
