package br.edu.ifce.swappers.swappers.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.fragments.principal.AboutFragment;
import br.edu.ifce.swappers.swappers.fragments.principal.BooksFragment;
import br.edu.ifce.swappers.swappers.fragments.principal.PlacesFragment;
import br.edu.ifce.swappers.swappers.fragments.principal.ProfileFragment;
import br.edu.ifce.swappers.swappers.fragments.principal.SettingsFragment;
import br.edu.ifce.swappers.swappers.fragments.principal.StatisticsFragment;
import it.neokree.googlenavigationdrawer.GAccount;
import it.neokree.googlenavigationdrawer.GAccountListener;
import it.neokree.googlenavigationdrawer.GSection;
import it.neokree.googlenavigationdrawer.GoogleNavigationDrawer;

public class MainActivity extends GoogleNavigationDrawer implements GAccountListener {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void init(Bundle bundle) {

        GAccount userAccount = this.loadAccount();

        this.addAccount(userAccount);
        this.setAccountListener(this);

        this.buildMainMenu();
        this.buildBottomMenu();
    }

    private void buildMainMenu() {

        //TODO load icons from resources
        Drawable profileSectionIcon;
        Drawable booksSectionIcon;
        Drawable placesSectionIcon;
        Drawable statisticsSectionIcon;

        GSection profileSection     = this.newSection("Profile", new ProfileFragment());
        GSection booksSection       = this.newSection("Books", new BooksFragment());
        GSection placesSection      = this.newSection("Places", new PlacesFragment());
        GSection statisticsSection  = this.newSection("Statistics", new StatisticsFragment());

        this.addSection(profileSection);
        this.addSection(booksSection);
        this.addSection(placesSection);
        this.addSection(statisticsSection);
        this.addDivisor();
    }

    private void buildBottomMenu() {
        Drawable aboutSectionIcon;
        Drawable settingsSectionIcon;

        GSection aboutSection    = this.newSection("About", new AboutFragment());
        GSection settingsSection = this.newSection("Settings", new SettingsFragment());

        this.addSection(aboutSection);
        this.addSection(settingsSection);
    }

    @Override
    public void onAccountOpening(GAccount gAccount) {

    }

    private GAccount loadAccount(){
        String username         = this.loadUserName();
        String email            = this.loadUserEmail();
        Drawable profilePicture = this.loadUserPicture();
        Drawable coverPicture   = this.loadCoverPicture();

        GAccount userAccount = new GAccount(username, email, profilePicture, coverPicture);
        return userAccount;
    }

    private Drawable loadCoverPicture() {
        return this.getResources().getDrawable(R.drawable.background_splash);
    }

    private Drawable loadUserPicture() {
        return this.getResources().getDrawable(R.drawable.ic_launcher);
    }

    private String loadUserEmail() {
        return "francisthomas@swappers.com";
    }

    private String loadUserName() {
        return "Francis Thomas";
    }

}
