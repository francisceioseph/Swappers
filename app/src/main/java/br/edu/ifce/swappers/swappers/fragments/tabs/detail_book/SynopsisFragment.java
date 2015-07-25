package br.edu.ifce.swappers.swappers.fragments.tabs.detail_book;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.adapters.BookRecyclerViewAdapter;
import br.edu.ifce.swappers.swappers.model.Book;

/**
 * A simple {@link Fragment} subclass.
 */
public class SynopsisFragment extends Fragment {

    //private RatingBar ratingBar;

    public SynopsisFragment() {

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_synopsis, container, false);


        TextView streetAddress       = (TextView) rootView.findViewById(R.id.street_text_view);
        TextView neighborhoodAndCity = (TextView) rootView.findViewById(R.id.neighborhood_and_city_text_view);
        TextView stateAndCountry     = (TextView) rootView.findViewById(R.id.state_and_country_text_view);
        TextView businessHoursDescription = (TextView) rootView.findViewById(R.id.business_hours_description_text_view);
//        RatingBar ratingBar = (RatingBar) rootView.findViewById(R.id.adapter_book_rating_bar);

        return rootView;


    }

}
