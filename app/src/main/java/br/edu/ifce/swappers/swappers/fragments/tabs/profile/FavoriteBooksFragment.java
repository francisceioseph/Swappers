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
import br.edu.ifce.swappers.swappers.miscellaneous.adapters.BookRecyclerViewAdapter;
import br.edu.ifce.swappers.swappers.dao.BookDAO;
import br.edu.ifce.swappers.swappers.model.Book;
import br.edu.ifce.swappers.swappers.miscellaneous.CategoryBook;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.RecycleViewOnClickListenerHack;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteBooksFragment extends Fragment implements RecycleViewOnClickListenerHack {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<Book> dataSource;

    public FavoriteBooksFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BookDAO bookDAO = new BookDAO(getActivity());
        if (!bookDAO.getBookAllByCategory(CategoryBook.FAVORITE.toString()).isEmpty()) {
            dataSource = bookDAO.getBookAllByCategory(CategoryBook.FAVORITE.toString());
        }else{
            dataSource = MockSingleton.INSTANCE.createMockedBookDataSource();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite_books, container, false);

        BookRecyclerViewAdapter adapter = new BookRecyclerViewAdapter(getActivity().getApplicationContext(),dataSource);
        adapter.setRecycleViewOnClickListenerHack(this);
        this.layoutManager = new LinearLayoutManager(getActivity());
        this.recyclerView = (RecyclerView) rootView.findViewById(R.id.favorite_books_list);

        this.recyclerView.setHasFixedSize(false);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;

    }

    @Override
    public void onClickListener(View view, int position) {
        //Intent detailBookFragmentIntent = new Intent(this.getActivity(),DetailBookActivity.class);
        //this.startActivity(detailBookFragmentIntent);
    }
}
