package br.edu.ifce.swappers.swappers.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.adapters.DonationsListPointRecyclerViewAdapter;
import br.edu.ifce.swappers.swappers.model.Book;
import br.edu.ifce.swappers.swappers.model.Place;
import br.edu.ifce.swappers.swappers.util.RecycleViewOnClickListenerHack;
import br.edu.ifce.swappers.swappers.util.SwappersToast;

public class DonationsListPointActivity extends AppCompatActivity implements RecycleViewOnClickListenerHack {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<Place> dataSource;
    Location location;
    LayoutInflater inflater;
    AlertDialog alertDonateInThePlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_donations_list_point);
        inflater = getLayoutInflater();
        String provider;

        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

        Criteria criteria = new Criteria();
        provider = service.getBestProvider(criteria, false);
        Location location = service.getLastKnownLocation(provider);
        double latitude = 0.0;
        double longitude = 0.0;

        if (location != null){
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

        dataSource = MockSingleton.INSTANCE.createMockedPlaceDataSource(latitude, longitude);

        DonationsListPointRecyclerViewAdapter adapter = new DonationsListPointRecyclerViewAdapter(dataSource);
        adapter.setRecycleViewOnClickListenerHack(this);

        this.layoutManager = new LinearLayoutManager(this);
        this.recyclerView = (RecyclerView) findViewById(R.id.donation_list_point);

        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());

        this.initToolbar();

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null){
            this.setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    @Override
    public void onClickListener(View view, int position) {
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
                SwappersToast.makeText(getApplicationContext(), "This book has been adopted by you! <3", Toast.LENGTH_SHORT).show();

                onBackPressed();
            }
        });

        alertDonateInThePlace = builder.create();
        alertDonateInThePlace.show();
    }
}
