package br.edu.ifce.swappers.swappers.fragments.tabs.detail_place;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.activities.DetailBookActivity;
import br.edu.ifce.swappers.swappers.adapters.BookRecyclerViewAdapter;
import br.edu.ifce.swappers.swappers.model.Book;
import br.edu.ifce.swappers.swappers.util.RecycleViewOnClickListenerHack;

/**
 * A simple {@link Fragment} subclass.
 */
public class AvailableBooksFragment extends Fragment implements RecycleViewOnClickListenerHack {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<Book> dataSource;

    public AvailableBooksFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = MockSingleton.INSTANCE.createMockedBookDataSource();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_available_books, container, false);
        BookRecyclerViewAdapter adapter = new BookRecyclerViewAdapter(getActivity(),dataSource);
        adapter.setRecycleViewOnClickListenerHack(this);

        this.layoutManager = new LinearLayoutManager(getActivity());
        this.recyclerView = (RecyclerView) rootView.findViewById(R.id.available_books_list);

        this.recyclerView.setHasFixedSize(false);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }


    @Override
    public void onClickListener(View view, int position) {
        Intent detailBookFragmentIntent = new Intent(this.getActivity().getApplicationContext(),DetailBookActivity.class);
        this.startActivity(detailBookFragmentIntent);
    }
}

