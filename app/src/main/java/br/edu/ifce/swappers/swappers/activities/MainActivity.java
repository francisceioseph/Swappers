package br.edu.ifce.swappers.swappers.activities;

/**
 * Last modified by Joamila on 14/11/2015
 */

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.fragments.dialogs.UserPhotoDialogFragment;
import br.edu.ifce.swappers.swappers.fragments.principal.AboutFragment;
import br.edu.ifce.swappers.swappers.fragments.principal.BooksFragment;
import br.edu.ifce.swappers.swappers.fragments.principal.NotificationsFragment;
import br.edu.ifce.swappers.swappers.fragments.principal.PlacesFragment;
import br.edu.ifce.swappers.swappers.fragments.principal.ProfileFragment;
import br.edu.ifce.swappers.swappers.fragments.principal.SettingsFragment;
import br.edu.ifce.swappers.swappers.fragments.principal.StatisticsFragment;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.UpdateImageTaskInterface;
import br.edu.ifce.swappers.swappers.miscellaneous.tasks.UpdateCoverUserTask;
import br.edu.ifce.swappers.swappers.miscellaneous.tasks.UpdatePhotoProfileUserTask;
import br.edu.ifce.swappers.swappers.miscellaneous.utils.AndroidUtils;
import br.edu.ifce.swappers.swappers.miscellaneous.utils.ImageUtil;
import br.edu.ifce.swappers.swappers.model.User;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialAccountListener;

public class MainActivity extends MaterialNavigationDrawer implements MaterialAccountListener, UserPhotoDialogFragment.UserPhotoDialogListener,UpdateImageTaskInterface {

    private static final short CAMERA_INTENT_CODE  = 1015;
    private static final short GALLERY_INTENT_CODE = 1016;
    private String photoCoverBase64,photoProfileBase64;
    private MaterialAccount userAccount;

 @Override
    public void init(Bundle savedInstance) {

        this.userAccount = this.loadAccount();

        this.addAccount(userAccount);
        this.setAccountListener(this);

        this.getToolbar().setTitleTextColor(Color.WHITE);

        this.buildMainMenu();
    }

    private void buildMainMenu() {

        Drawable notificationSectionIcon = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_help);
        Drawable profileSectionIcon      = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_person);
        Drawable booksSectionIcon        = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_book);
        Drawable placesSectionIcon       = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_room);
        Drawable statisticsSectionIcon   = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_dashboard);
        Drawable aboutSectionIcon        = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_help);
        Drawable settingsSectionIcon     = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_settings);

        MaterialSection notificationSection = this.newSection("Notification", notificationSectionIcon, new NotificationsFragment());
        MaterialSection profileSection      = this.newSection(getString(R.string.profile_section_title), profileSectionIcon, new ProfileFragment());
        MaterialSection booksSection        = this.newSection(getString(R.string.books_section_title), booksSectionIcon, new BooksFragment());
        MaterialSection placesSection       = this.newSection(getString(R.string.places_section_title), placesSectionIcon, new PlacesFragment());
        MaterialSection statisticsSection   = this.newSection(getString(R.string.statistics_section_title), statisticsSectionIcon, new StatisticsFragment());
        MaterialSection aboutSection        = this.newSection(getString(R.string.about_section_title), aboutSectionIcon, new AboutFragment());
        MaterialSection settingsSection     = this.newSection(getString(R.string.settings_section_title), settingsSectionIcon, new SettingsFragment());

        this.addSection(notificationSection);
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

        Bitmap coverPhotoBitmap;
        String coverPhotoBase64 = AndroidUtils.loadCoverPictureBase64(this);

        try {
            if (coverPhotoBase64.isEmpty()) {
                coverPhotoBitmap = ((BitmapDrawable) ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_05)).getBitmap();
            } else {
                coverPhotoBitmap = ImageUtil.stringToBitMap(coverPhotoBase64);
            }
        }
        catch (Exception e){
            coverPhotoBitmap = ((BitmapDrawable) ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_05)).getBitmap();
        }

        return coverPhotoBitmap;
    }

    private Bitmap loadUserPhoto() {
        Bitmap userPhotoBitmap;
        try {

            if (MockSingleton.INSTANCE.user.getPhoto2().isEmpty()) {
                userPhotoBitmap = ((BitmapDrawable) ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_person_giant)).getBitmap();
            } else {
                userPhotoBitmap = ImageUtil.stringToBitMap(MockSingleton.INSTANCE.user.getPhoto2());
            }
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

    public void usePhotoCamera(Intent data){
        Bitmap userPhotoBitmap = ImageUtil.retrieveImageFromCameraResult(data);
        String photoBase64     = ImageUtil.BitMapToString(userPhotoBitmap);

        if(MockSingleton.INSTANCE.codePhoto == 10){
            this.photoProfileBase64 = photoBase64;

            User user = AndroidUtils.loadUser(this);
            user.setPhoto2(this.photoProfileBase64);

            UpdatePhotoProfileUserTask updatePhotoProfileUserTask = new UpdatePhotoProfileUserTask(this,this);
            updatePhotoProfileUserTask.execute(user);
        }
        else if(MockSingleton.INSTANCE.codePhoto == 20){
            this.photoCoverBase64 = photoBase64;

            User user = AndroidUtils.loadUser(this);
            user.setCover(this.photoCoverBase64);

            UpdateCoverUserTask updateCoverUserTask = new UpdateCoverUserTask(this,this);
            updateCoverUserTask.execute(user);
        }

        MockSingleton.INSTANCE.codePhoto = 0;
    }

    public void usePhotoGallery(Intent data){
        Bitmap userPhotoBitmap = ImageUtil.retrieveImageFromGalleryResult(data, this);
        String photoBase64     = ImageUtil.BitMapToString(userPhotoBitmap);

        if(MockSingleton.INSTANCE.codePhoto == 10){
            this.photoProfileBase64 = photoBase64;

            User user = AndroidUtils.loadUser(this);
            user.setPhoto2(this.photoProfileBase64);

            UpdatePhotoProfileUserTask updatePhotoProfileUserTask = new UpdatePhotoProfileUserTask(this,this);
            updatePhotoProfileUserTask.execute(user);
        }
        else if(MockSingleton.INSTANCE.codePhoto == 20){
            this.photoCoverBase64 = photoBase64;

            User user = AndroidUtils.loadUser(this);
            user.setCover(this.photoCoverBase64);

            UpdateCoverUserTask updateCoverUserTask = new UpdateCoverUserTask(this,this);
            updateCoverUserTask.execute(user);
        }

        MockSingleton.INSTANCE.codePhoto = 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_INTENT_CODE && resultCode == RESULT_OK){
            usePhotoCamera(data);
        }
        else if (requestCode == GALLERY_INTENT_CODE && resultCode == RESULT_OK) {
            usePhotoGallery(data);
        }
    }

    @Override
    public void onUpdateImageCoverHadFinished() {
        AndroidUtils.saveCoverPicture(this, this.photoCoverBase64);
        MockSingleton.INSTANCE.user = AndroidUtils.loadUser(this);

        String coverPhotoBase64 = AndroidUtils.loadCoverPictureBase64(this);
        if (coverPhotoBase64 != null & !coverPhotoBase64.isEmpty()) {
            this.userAccount.setBackground(ImageUtil.stringToBitMap(coverPhotoBase64));
        }
    }

    @Override
    public void onUpdateImageProfileHadFinished() {
        AndroidUtils.saveProfilePicture(this, this.photoProfileBase64);
        MockSingleton.INSTANCE.user = AndroidUtils.loadUser(this);

        this.userAccount.setPhoto(ImageUtil.stringToBitMap(MockSingleton.INSTANCE.user.getPhoto2()));
    }
}
