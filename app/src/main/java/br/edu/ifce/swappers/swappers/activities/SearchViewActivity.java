package br.edu.ifce.swappers.swappers.activities;

import android.content.Context;
import android.content.Intent;
import android.app.SearchManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.adapters.BookRecyclerViewAdapter;
import br.edu.ifce.swappers.swappers.model.Book;


public class SearchViewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Book> mBookList;
    private ArrayList<Book> mBookListAux;
    private BookRecyclerViewAdapter adapter;
    private LinearLayoutManager lm;
    private TextView tv;
    private FrameLayout frameLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search_view);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mBookListAux = new ArrayList<>();

        Book book = new Book("A Culpa é das Estrelas", "Shanya", "Shnya", 3.0f, 2.5f);
        mBookListAux.add(book);
        adapter = new BookRecyclerViewAdapter(mBookListAux);

        frameLayout = (FrameLayout) findViewById(R.id.fl_container);

        recyclerView.setHasFixedSize(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        lm = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);

        hendleSearch(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        hendleSearch(intent);
    }

    public void hendleSearch(Intent intent) {
        if (Intent.ACTION_SEARCH.equalsIgnoreCase(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchBook(query);
        }
    }

    //Busca a ser feita com AsyncTask no google-books
    public void searchBook(String query) {
        mBookListAux.clear();

        for (int i = 0, tamI = mBookList.size(); i < tamI; i++) {
            if (mBookList.get(i).getTitle().toLowerCase().startsWith(query.toLowerCase())) {
                mBookListAux.add(mBookList.get(i));
            }
        }

        recyclerView.setVisibility(mBookListAux.isEmpty() ? View.GONE : View.VISIBLE);
        if (mBookListAux.isEmpty()) {
            tv = new TextView(this);
            tv.setText("Nenhum livro encontrado.");
            tv.setTextColor(getResources().getColor(R.color.color_primary));
            tv.setId(new Integer(1));
            tv.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            tv.setGravity(Gravity.CENTER);
            frameLayout.addView(tv);
        }else if(frameLayout.findViewById(new Integer(1))!=null){
            frameLayout.removeView(frameLayout.findViewById(new Integer(1)));
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_searchview_activity, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView;

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        return super.onCreateOptionsMenu(menu);
    }
}
