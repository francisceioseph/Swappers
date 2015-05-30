package br.edu.ifce.swappers.swappers.fragments.tabs.books;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.edu.ifce.swappers.swappers.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendationsFragment extends Fragment {


    public RecommendationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recomendations, container, false);
    }


}
