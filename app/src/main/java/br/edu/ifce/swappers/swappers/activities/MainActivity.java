package br.edu.ifce.swappers.swappers.activities;

/**
 * Last modified by Joamila on 16/10/2015
 */

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.fragments.dialogs.UserPhotoDialogFragment;
import br.edu.ifce.swappers.swappers.fragments.principal.AboutFragment;
import br.edu.ifce.swappers.swappers.fragments.principal.BooksFragment;
import br.edu.ifce.swappers.swappers.fragments.principal.PlacesFragment;
import br.edu.ifce.swappers.swappers.fragments.principal.ProfileFragment;
import br.edu.ifce.swappers.swappers.fragments.principal.SettingsFragment;
import br.edu.ifce.swappers.swappers.fragments.principal.StatisticsFragment;
import br.edu.ifce.swappers.swappers.miscellaneous.utils.ImageUtil;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialAccountListener;

public class MainActivity extends MaterialNavigationDrawer implements MaterialAccountListener, UserPhotoDialogFragment.UserPhotoDialogListener {

    private static final short CAMERA_INTENT_CODE  = 1015;
    private static final short GALLERY_INTENT_CODE = 1016;

 @Override
    public void init(Bundle savedInstance) {

        MaterialAccount userAccount = this.loadAccount();

        this.addAccount(userAccount);
        this.setAccountListener(this);

        this.getToolbar().setTitleTextColor(Color.WHITE);

        this.buildMainMenu();
    }

    private void buildMainMenu() {

        Drawable profileSectionIcon     = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_person);
        Drawable booksSectionIcon       = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_book);
        Drawable placesSectionIcon      = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_room);
        Drawable statisticsSectionIcon  = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_dashboard);
        Drawable aboutSectionIcon       = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_help);
        Drawable settingsSectionIcon    = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_settings);

        MaterialSection profileSection     = this.newSection(getString(R.string.profile_section_title), profileSectionIcon, new ProfileFragment());
        MaterialSection booksSection       = this.newSection(getString(R.string.books_section_title), booksSectionIcon, new BooksFragment());
        MaterialSection placesSection      = this.newSection(getString(R.string.places_section_title), placesSectionIcon, new PlacesFragment());
        MaterialSection statisticsSection  = this.newSection(getString(R.string.statistics_section_title), statisticsSectionIcon, new StatisticsFragment());
        MaterialSection aboutSection       = this.newSection(getString(R.string.about_section_title), aboutSectionIcon, new AboutFragment());
        MaterialSection settingsSection    = this.newSection(getString(R.string.settings_section_title), settingsSectionIcon, new SettingsFragment());

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

        return new MaterialAccount(this.getResources(), username, email,userPhoto, coverPhoto);
    }

    private Bitmap loadCoverPhoto() {
        BitmapDrawable coverPhotoDrawable = (BitmapDrawable) ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_splash);
        Bitmap coverPhotoBitmap = coverPhotoDrawable.getBitmap();
        return coverPhotoBitmap;
    }

    private Bitmap loadUserPhoto() {
        Bitmap userPhotoBitmap;

        try {
            userPhotoBitmap = ImageUtil.StringToBitMap(MockSingleton.INSTANCE.user.getPhoto2());
        }
        catch (Exception e){
            userPhotoBitmap = ((BitmapDrawable) ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_person_giant)).getBitmap();
        }

        return userPhotoBitmap;
    }

    private String loadUserEmail() {
        String email;

        try {
            email = MockSingleton.INSTANCE.user.getEmail();
        }
        catch (NullPointerException e){
            email = getString(R.string.guest_email);
        }

        return email;
    }

    private String loadUserName() {
        String userName;

        try {
            userName = MockSingleton.INSTANCE.user.getName();
        }
        catch (NullPointerException e) {
            userName = getString(R.string.guest_name);
        }

        return userName;
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

        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ){
            searchView = (SearchView) searchItem.getActionView();
        }
        else{
            searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        }

        searchView.setQueryHint(getString(R.string.search_view_hint));
        ComponentName cn = new ComponentName(this, SearchViewActivity.class);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(cn));

        return true;
    }

    @Override
    public void onGalleryClick(DialogFragment dialogFragment) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_INTENT_CODE);
    }

    @Override
    public void onCameraClick(DialogFragment dialogFragment) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(cameraIntent.resolveActivity(getPackageManager() ) != null) {
            startActivityForResult(cameraIntent, CAMERA_INTENT_CODE);
        }
    }
}
