package br.edu.ifce.swappers.swappers.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.fragments.tabs.detail_place.AvailableBooksFragment;
import br.edu.ifce.swappers.swappers.fragments.tabs.detail_place.InformationFragment;
import br.edu.ifce.swappers.swappers.model.Place;
import br.edu.ifce.swappers.swappers.util.AndroidUtils;
import br.edu.ifce.swappers.swappers.util.ImageUtil;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailPlaceActivity extends AppCompatActivity {

    private FragmentTabHost placeTabHost;
    private Place place;
    private CircleImageView photoPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_place);

        TextView placeName = (TextView) findViewById(R.id.place_name);
        TextView placeCity = (TextView) findViewById(R.id.place_city);
        photoPlace = (CircleImageView) findViewById(R.id.adapter_comment_author_image);

        Intent currentIntent = getIntent();
        place = (Place) currentIntent.getSerializableExtra("SELECTED_BOOK_PLACE");
        placeName.setText(place.getName());
        placeCity.setText(place.getCity());

        //photoPlace.setImageBitmap(BitmapFactory.decodeByteArray(place.getPhoto(), 0, place.getPhoto().length));
        photoPlace.setImageBitmap(ImageUtil.StringToBitMap(place.getPhoto2()));

        this.initToolbar();
        this.initTabHost();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.placeTabHost = null;
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null){
            this.setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void initTabHost() {

        this.placeTabHost = (FragmentTabHost) findViewById(R.id.place_tabHost);
        this.placeTabHost.setup(this, this.getSupportFragmentManager(), android.R.id.tabcontent);

        TabHost.TabSpec placeInformationTab   = this.placeTabHost.newTabSpec("placeInformationTab");
        TabHost.TabSpec availableBooksTab     = this.placeTabHost.newTabSpec("availableBooksTab");

        placeInformationTab.setIndicator("INFORMATION");
        availableBooksTab.setIndicator("AVAILABLE BOOKS");

        this.placeTabHost.addTab(placeInformationTab, InformationFragment.class, null);
        this.placeTabHost.addTab(availableBooksTab, AvailableBooksFragment.class, null);

        this.placeTabHost.setCurrentTab(1);

        this.stylizeTabsTextView(placeTabHost);
    }

    private void stylizeTabsTextView(FragmentTabHost tabHost){
        ColorStateList tabTextColors;
        TabWidget tabWidget;
        TextView tabTextView;
        View tabView;

        int tabAmount;

        tabWidget     = tabHost.getTabWidget();
        tabTextColors = this.getResources().getColorStateList(R.color.tab_text_selector);
        tabAmount     = tabWidget.getTabCount();

        for (int i = 0; i < tabAmount; i++){
            tabView = tabWidget.getChildTabViewAt(i);
            tabView.setBackgroundResource(R.drawable.tab_indicator);

            tabTextView = (TextView) tabView.findViewById(android.R.id.title);
            tabTextView.setTextColor(tabTextColors);
        }
    }

}
