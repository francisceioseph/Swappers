package br.edu.ifce.swappers.swappers.fragments.tabs.detail_place;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.adapters.BookRecyclerViewAdapter;
import br.edu.ifce.swappers.swappers.model.Book;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformationFragment extends Fragment {
    private TextView streetAddress;
    TextView neighborhoodAndCity;
    TextView stateAndCountry;
    TextView businessHoursDescription;

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

        Bundle extras = getActivity().getIntent().getExtras();

        streetAddress.setText(extras.getString("streetnumber"));
        neighborhoodAndCity.setText(extras.getString("neighborcity"));
        stateAndCountry.setText(extras.getString("statecountry"));
        businessHoursDescription.setText(extras.getString("hourfunc"));

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
