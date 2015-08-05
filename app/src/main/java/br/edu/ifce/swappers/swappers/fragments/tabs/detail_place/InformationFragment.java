package br.edu.ifce.swappers.swappers.fragments.tabs.detail_place;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.adapters.BookRecyclerViewAdapter;
import br.edu.ifce.swappers.swappers.model.Book;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformationFragment extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<Book> dataSource;

    public InformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_information, container, false);
        BookRecyclerViewAdapter adapter = new BookRecyclerViewAdapter(dataSource);

        this.layoutManager = new LinearLayoutManager(getActivity());
        this.recyclerView = (RecyclerView) rootView.findViewById(R.id.available_books_list);

        this.recyclerView.setHasFixedSize(false);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }
}
