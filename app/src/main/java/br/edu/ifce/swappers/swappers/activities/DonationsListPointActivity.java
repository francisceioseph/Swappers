package br.edu.ifce.swappers.swappers.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.adapters.DonationsListPointRecyclerViewAdapter;
import br.edu.ifce.swappers.swappers.dao.BookDAO;
import br.edu.ifce.swappers.swappers.model.Book;
import br.edu.ifce.swappers.swappers.model.Place;
import br.edu.ifce.swappers.swappers.model.User;
import br.edu.ifce.swappers.swappers.util.AndroidUtils;
import br.edu.ifce.swappers.swappers.util.BookInterface;
import br.edu.ifce.swappers.swappers.util.CategoryBook;
import br.edu.ifce.swappers.swappers.util.DonationTask;
import br.edu.ifce.swappers.swappers.util.RecycleViewOnClickListenerHack;

public class DonationsListPointActivity extends AppCompatActivity implements RecycleViewOnClickListenerHack,BookInterface {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    Book book;
    DonationsListPointRecyclerViewAdapter adapter;
    ArrayList<Place> dataSource;
    int positionPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donations_list_point);

        Intent currentIntent = getIntent();
        book = (Book) currentIntent.getSerializableExtra(AndroidUtils.SELECTED_BOOK_ID);

        this.initToolbar();
        this.initRecyclerView();
    }

    @Override
    public void onClickListener(View view, int position) {
        this.makeConfirmDialogForBookDonation(position).show();
    }

    private AlertDialog makeConfirmDialogForBookDonation(final int posRecycleView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.SWDialogTheme);

        builder.setTitle("Do you wish donate this book in this place?");
        builder.setIcon(R.drawable.ic_ask_place_donate);

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("DONATE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                initDonate(posRecycleView);
                positionPlace = posRecycleView;
                onBackPressed();
            }
        });

        return builder.create();
    }

    public void initDonate(int posRecycleview){
        User user = new User();
        user.setId(MockSingleton.INSTANCE.user.getId());
        Place place = new Place();
        place.setId(adapter.getItemID(posRecycleview));

        book.setPlace(place);
        user.setBook(book);

        DonationTask donationTask = new DonationTask(getApplicationContext(), this);
        donationTask.execute(user);

    }


    @Override
    public void saveBookBaseLocal(){
        BookDAO bookDAO = new BookDAO(this);
        bookDAO.insert(book, CategoryBook.DONATION);

        addBookIntoPlace();
    }

    private int addBookIntoPlace(){
        int size = MockSingleton.INSTANCE.places.size();
        int idPlace = adapter.getItemID(positionPlace);

        for(int i=0; i<size; i++) {
            if(idPlace==MockSingleton.INSTANCE.places.get(i).getId()) {
               MockSingleton.INSTANCE.places.get(i).getBooks().add(book);
               return 1;
            }
        }
        return 0;
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null){
            toolbar.setTitle("Near Places");

            this.setSupportActionBar(toolbar);
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            this.getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    private void initRecyclerView() {
        //dataSource = PlaceSingleton.getInstance().getPlaces();

        dataSource = MockSingleton.INSTANCE.places;

        adapter = new DonationsListPointRecyclerViewAdapter(dataSource);
        adapter.setRecycleViewOnClickListenerHack(this);

        this.layoutManager = new LinearLayoutManager(this);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());

    }
}
