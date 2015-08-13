package br.edu.ifce.swappers.swappers.activities;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.zip.Inflater;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.adapters.DonationsListPointRecyclerViewAdapter;
import br.edu.ifce.swappers.swappers.model.Book;
import br.edu.ifce.swappers.swappers.model.Place;
import br.edu.ifce.swappers.swappers.util.RecycleViewOnClickListenerHack;

public class DonationsListPointActivity extends AppCompatActivity implements RecycleViewOnClickListenerHack {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<Place> dataSource;
    Location location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_donations_list_point);
        LayoutInflater inflater = getLayoutInflater();
        String provider;

        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

        Criteria criteria = new Criteria();
        provider = service.getBestProvider(criteria, false);
        Location location = service.getLastKnownLocation(provider);

        double latitude = location.getLatitude();
        double longitude = location.getLatitude();

        dataSource = MockSingleton.INSTANCE.createMockedPlaceDataSource(latitude, longitude);

        DonationsListPointRecyclerViewAdapter adapter = new DonationsListPointRecyclerViewAdapter(dataSource);
        adapter.setRecycleViewOnClickListenerHack(this);

        this.layoutManager = new LinearLayoutManager(this);
        this.recyclerView = (RecyclerView) findViewById(R.id.donation_list_point);

        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_donations_list_point, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickListener(View view, int position) {

    }
}
