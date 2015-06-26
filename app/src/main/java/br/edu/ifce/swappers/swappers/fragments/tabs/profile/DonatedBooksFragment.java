package br.edu.ifce.swappers.swappers.fragments.tabs.profile;


import android.content.res.ColorStateList;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabWidget;
import android.widget.TextView;

import br.edu.ifce.swappers.swappers.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DonatedBooksFragment extends Fragment {

    private FragmentTabHost bookTabHost;

    public DonatedBooksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_donated_books, container, false);
        return rootView;
    }
}
