package br.edu.ifce.swappers.swappers.fragments.tabs.profile;


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
import br.edu.ifce.swappers.swappers.adapters.BookRecyclerViewAdapter;
import br.edu.ifce.swappers.swappers.model.Book;

/**
 * A simple {@link Fragment} subclass.
 */
public class DonatedBooksFragment extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<Book> dataSource;

    public DonatedBooksFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = MockSingleton.INSTANCE.createMockedBookDataSource();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_donated_books, container, false);

        BookRecyclerViewAdapter adapter = new BookRecyclerViewAdapter(getActivity(),dataSource);

        this.layoutManager = new LinearLayoutManager(getActivity());
        this.recyclerView = (RecyclerView) rootView.findViewById(R.id.donated_books_list);

        this.recyclerView.setHasFixedSize(false);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }
}
