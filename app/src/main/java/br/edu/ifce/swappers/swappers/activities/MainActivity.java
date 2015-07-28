package br.edu.ifce.swappers.swappers.activities;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.fragments.principal.AboutFragment;
import br.edu.ifce.swappers.swappers.fragments.principal.BooksFragment;
import br.edu.ifce.swappers.swappers.fragments.principal.PlacesFragment;
import br.edu.ifce.swappers.swappers.fragments.principal.ProfileFragment;
import br.edu.ifce.swappers.swappers.fragments.principal.SettingsFragment;
import br.edu.ifce.swappers.swappers.fragments.principal.StatisticsFragment;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialAccountListener;

public class MainActivity extends MaterialNavigationDrawer implements MaterialAccountListener {

 @Override
    public void init(Bundle savedInstance) {

        MaterialAccount userAccount = this.loadAccount();

        this.addAccount(userAccount);
        this.setAccountListener(this);

        this.buildMainMenu();
    }

    private void buildMainMenu() {

        Drawable profileSectionIcon     = this.getResources().getDrawable(R.drawable.ic_person);
        Drawable booksSectionIcon       = this.getResources().getDrawable(R.drawable.ic_book);
        Drawable placesSectionIcon      = this.getResources().getDrawable(R.drawable.ic_room);
        Drawable statisticsSectionIcon  = this.getResources().getDrawable(R.drawable.ic_dashboard);
        Drawable aboutSectionIcon       = this.getResources().getDrawable(R.drawable.ic_help);
        Drawable settingsSectionIcon    = this.getResources().getDrawable(R.drawable.ic_settings);

        MaterialSection profileSection     = this.newSection("Profile", profileSectionIcon, new ProfileFragment());
        MaterialSection booksSection       = this.newSection("Books", booksSectionIcon, new BooksFragment());
        MaterialSection placesSection      = this.newSection("Places", placesSectionIcon, new PlacesFragment());
//        MaterialSection placesSection      = this.newSection("Places", placesSectionIcon, new SearchViewFragment());
        MaterialSection statisticsSection  = this.newSection("Statistics", statisticsSectionIcon, new StatisticsFragment());
        MaterialSection aboutSection       = this.newSection("About", aboutSectionIcon, new AboutFragment());
        MaterialSection settingsSection    = this.newSection("Settings", settingsSectionIcon, new SettingsFragment());

        this.addSection(profileSection);
        this.addSection(booksSection);
        this.addSection(placesSection);
        this.addSection(statisticsSection);

        this.addDivisor();

        this.addSection(aboutSection);
        this.addSection(settingsSection);

        this.disableLearningPattern();

        this.setDefaultSectionLoaded(this.getSectionList().indexOf(placesSection));
    }

    private MaterialAccount loadAccount(){
        String username     = this.loadUserName();
        String email        = this.loadUserEmail();
        Bitmap userPhoto    = this.loadUserPhoto();
        Bitmap coverPhoto   = this.loadCoverPhoto();

        MaterialAccount userAccount = new MaterialAccount(this.getResources(), username, email,userPhoto, coverPhoto);
        return userAccount;
    }

    private Bitmap loadCoverPhoto() {
        BitmapDrawable coverPhotoDrawable = (BitmapDrawable) this.getResources().getDrawable(R.drawable.background_splash);
        Bitmap coverPhotoBitmap = coverPhotoDrawable.getBitmap();
        return coverPhotoBitmap;
    }

    private Bitmap loadUserPhoto() {
        BitmapDrawable userPhotoDrawer = (BitmapDrawable) this.getResources().getDrawable(R.drawable.ic_person_giant);
        Bitmap userPhotoBitmap = userPhotoDrawer.getBitmap();
        return userPhotoBitmap;
    }

    private String loadUserEmail() {
        return "francisthomas@swappers.com";
    }

    private String loadUserName() {
        return "Francis Thomas";
    }

    @Override
    public void onAccountOpening(MaterialAccount materialAccount) {

    }

    @Override
    public void onChangeAccount(MaterialAccount materialAccount) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);

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
