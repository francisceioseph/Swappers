package br.edu.ifce.swappers.swappers.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.fragments.tabs.detail_place.AvailableBooksFragment;
import br.edu.ifce.swappers.swappers.fragments.tabs.detail_place.InformationFragment;
import br.edu.ifce.swappers.swappers.model.Place;
import br.edu.ifce.swappers.swappers.miscellaneous.utils.ImageUtil;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailPlaceActivity extends AppCompatActivity {

    private FragmentTabHost placeTabHost;
    private Place currentPlace;
    private CircleImageView photoPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_place);

        TextView placeName = (TextView) findViewById(R.id.place_name);
        TextView placeCity = (TextView) findViewById(R.id.place_city);
        photoPlace = (CircleImageView) findViewById(R.id.adapter_comment_author_image);

        Intent currentIntent = getIntent();
        currentPlace = (Place) currentIntent.getSerializableExtra("SELECTED_BOOK_PLACE");
        placeName.setText(currentPlace.getName());
        placeCity.setText(currentPlace.getCity());

        photoPlace.setImageBitmap(ImageUtil.StringToBitMap(currentPlace.getPhoto2()));

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

            toolbar.setTitle(getString(R.string.places_detail_toolbar_title));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void initTabHost() {

        this.placeTabHost = (FragmentTabHost) findViewById(R.id.place_tabHost);
        this.placeTabHost.setup(this, this.getSupportFragmentManager(), android.R.id.tabcontent);

        TabHost.TabSpec placeInformationTab   = this.placeTabHost.newTabSpec("placeInformationTab");
        TabHost.TabSpec availableBooksTab     = this.placeTabHost.newTabSpec("availableBooksTab");

        placeInformationTab.setIndicator(getString(R.string.information_tab_title));
        availableBooksTab.setIndicator(getString(R.string.available_books_tab_title));

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
