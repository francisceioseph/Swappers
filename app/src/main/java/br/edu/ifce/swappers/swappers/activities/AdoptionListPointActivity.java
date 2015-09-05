package br.edu.ifce.swappers.swappers.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
import br.edu.ifce.swappers.swappers.util.RetrievedTask;
import br.edu.ifce.swappers.swappers.util.SwappersToast;
import br.edu.ifce.swappers.swappers.util.UserPosition;
import br.edu.ifce.swappers.swappers.webservice.PlaceSingleton;

public class AdoptionListPointActivity extends AppCompatActivity implements RecycleViewOnClickListenerHack,BookInterface {

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
        this.book = (Book) currentIntent.getSerializableExtra(AndroidUtils.SELECTED_BOOK_ID);

        this.initToolbar();
        this.initRecyclerView();
    }

    @Override
    public void onClickListener(View view, int position) {
        this.makeConfirmDialogForBookAdoption(position).show();
    }

    private AlertDialog makeConfirmDialogForBookAdoption(final int posRecycleView){

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.SWDialogTheme);

        builder.setTitle("Do you wish adopt this book in this place?");
        builder.setIcon(R.drawable.ic_ask_place_donate);

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("ADOPT", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                initRetrieved(posRecycleView);
                positionPlace = posRecycleView;
                //SwappersToast.makeText(getApplicationContext(), "This book has been adopted by you! <3", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

        return builder.create();
    }

    public void initRetrieved(int posRecycleview){
        User user = new User();
        user.setId(MockSingleton.INSTANCE.user.getId());
        Place place = new Place();
        place.setId(adapter.getItemID(posRecycleview));

        book.setPlace(place);
        user.setBook(book);

        RetrievedTask retrievedTask = new RetrievedTask(getApplicationContext(),this);
        retrievedTask.execute(user);
    }

    @Override
    public void saveBookBaseLocal() {
        BookDAO bookDAO = new BookDAO(this);
        bookDAO.insert(book, CategoryBook.RETRIEVED);

        removeBookIntoPlace();
    }

    private int removeBookIntoPlace(){
        int size = MockSingleton.INSTANCE.places.size();
        int idPlace = adapter.getItemID(positionPlace);

        for(int i=0; i<size; i++) {
            if(idPlace==MockSingleton.INSTANCE.places.get(i).getId()) {
                List<Book> books = MockSingleton.INSTANCE.places.get(i).getBooks();
                for(int j=0; j<books.size() ; j++){
                    Log.i("ID-BOOK",books.get(j).getId());
                    Log.i("ID-BOOK",book.getId());
                    if(books.get(j).getId().equals(book.getId())){
                        MockSingleton.INSTANCE.places.get(i).getBooks().remove(books.get(j));
                        return 1;
                    }
                }
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
            dataSource = getPlaces();

            adapter = new DonationsListPointRecyclerViewAdapter(dataSource);
            adapter.setRecycleViewOnClickListenerHack(this);

            this.layoutManager = new LinearLayoutManager(this);
            this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

            this.recyclerView.setHasFixedSize(true);
            this.recyclerView.setLayoutManager(layoutManager);
            this.recyclerView.setAdapter(adapter);
            this.recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private ArrayList<Place> getPlaces(){
        ArrayList<Place> places = MockSingleton.INSTANCE.getPlaces();
        ArrayList<Place> placeRetrieved = new ArrayList<>();

        for (int i =0; i<places.size();i++) {
            for (int j =0; j<places.get(i).getBooks().size();j++)
                if (places.get(i).getBooks().get(j).getId().equals(book.getId())){
                    placeRetrieved.add(places.get(i));
                    Log.i("TAG-PLACE-BOOK","entrou");
                }
        }
        return placeRetrieved;
    }
}
