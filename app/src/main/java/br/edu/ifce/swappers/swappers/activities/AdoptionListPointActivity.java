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
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.adapters.DonationsListPointRecyclerViewAdapter;
import br.edu.ifce.swappers.swappers.model.Book;
import br.edu.ifce.swappers.swappers.model.Place;
import br.edu.ifce.swappers.swappers.util.AndroidUtils;
import br.edu.ifce.swappers.swappers.util.RecycleViewOnClickListenerHack;
import br.edu.ifce.swappers.swappers.util.SwappersToast;
import br.edu.ifce.swappers.swappers.util.UserPosition;
import br.edu.ifce.swappers.swappers.webservice.PlaceSingleton;

public class AdoptionListPointActivity extends AppCompatActivity implements RecycleViewOnClickListenerHack {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    Book book;
    DonationsListPointRecyclerViewAdapter adapter;

    ArrayList<Place> dataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donations_list_point);

        UserPosition userPosition;
        LocationManager locationManager;
        boolean isLocationServiceEnabled;

        Intent currentIntent = getIntent();
        this.book = (Book) currentIntent.getSerializableExtra(AndroidUtils.SELECTED_BOOK_ID);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        isLocationServiceEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (isLocationServiceEnabled) {
            userPosition = UserPosition.getInstance(locationManager);

            this.initToolbar();
            this.initRecyclerView(userPosition);
        }
    }

    @Override
    public void onClickListener(View view, int position) {
        this.makeConfirmDialogForBookAdoption().show();
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
