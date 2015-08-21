package br.edu.ifce.swappers.swappers.fragments.tabs.detail_book;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.edu.ifce.swappers.swappers.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SynopsisFragment extends Fragment {

    public SynopsisFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_synopsis, container, false);

        TextView synopsisTextView = (TextView) rootView.findViewById(R.id.synopsis_book);

        return rootView;
    }

}
