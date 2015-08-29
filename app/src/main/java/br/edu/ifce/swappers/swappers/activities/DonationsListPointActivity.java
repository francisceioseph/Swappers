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
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.adapters.DonationsListPointRecyclerViewAdapter;
import br.edu.ifce.swappers.swappers.model.Book;
import br.edu.ifce.swappers.swappers.model.Place;
import br.edu.ifce.swappers.swappers.model.User;
import br.edu.ifce.swappers.swappers.util.AndroidUtils;
import br.edu.ifce.swappers.swappers.util.DonationTask;
import br.edu.ifce.swappers.swappers.util.RecycleViewOnClickListenerHack;
import br.edu.ifce.swappers.swappers.util.SwappersToast;
import br.edu.ifce.swappers.swappers.util.UserPosition;
import br.edu.ifce.swappers.swappers.webservice.PlaceSingleton;

public class DonationsListPointActivity extends AppCompatActivity implements RecycleViewOnClickListenerHack {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    Book book;
    Place place;
    DonationsListPointRecyclerViewAdapter adapter;

    ArrayList<Place> dataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donations_list_point);

        Intent currentIntent = getIntent();
        book = (Book) currentIntent.getSerializableExtra(AndroidUtils.SELECTED_BOOK_ID);


        UserPosition userPosition;

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean isLocationServiceEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (isLocationServiceEnabled) {
            userPosition = UserPosition.getInstance(locationManager);

            this.initToolbar();
            this.initRecyclerView(userPosition);
        }
    }

    @Override
    public void onClickListener(View view, int position) {
        AlertDialog alertDonateInThePlace;
        Intent currentIntent = getIntent();
        int intentCode = currentIntent.getIntExtra("INTENT_CODE", 0);

        alertDonateInThePlace = this.makeConfirmDialog(position);
        alertDonateInThePlace.show();
    }

 /*   private AlertDialog makeConfirmDialog(int posRecycleView){
        AlertDialog dialog = null;
        Intent currentIntent = getIntent();
        int intentCode = currentIntent.getIntExtra(AndroidUtils.BOOK_INTENT_CODE_ID, 0);

        if (intentCode == AndroidUtils.BOOK_ADOPTION_INTENT_CODE){
            dialog = this.makeConfirmDialogForBookAdoption();
        }
        else if (intentCode == AndroidUtils.BOOK_DONATION_INTENT_CODE) {
            dialog = this.makeConfirmDialogForBookDonation(posRecycleView);
        }

        return dialog;
    }*/

      private AlertDialog makeConfirmDialog(int posRecycleView){
        AlertDialog dialog = null;
        dialog = this.makeConfirmDialogForBookDonation(posRecycleView);
        return dialog;
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
                onBackPressed();

            }
        });

        return builder.create();
    }


    private AlertDialog makeConfirmDialogForBookAdoption(){

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
                SwappersToast.makeText(getApplicationContext(), "This book has been adopted by you! <3", Toast.LENGTH_SHORT).show();
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

        DonationTask donationTask = new DonationTask(getApplicationContext());
        donationTask.execute(user);
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

    private void initRecyclerView(UserPosition userPosition) {
        dataSource = PlaceSingleton.getInstance().getPlaces();


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
