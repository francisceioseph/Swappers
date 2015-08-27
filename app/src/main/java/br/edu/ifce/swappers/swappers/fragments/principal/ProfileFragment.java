package br.edu.ifce.swappers.swappers.fragments.principal;


import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.fragments.tabs.profile.DonatedBooksFragment;
import br.edu.ifce.swappers.swappers.fragments.tabs.profile.FavoriteBooksFragment;
import br.edu.ifce.swappers.swappers.fragments.tabs.profile.RetrievedBooksFragment;
import br.edu.ifce.swappers.swappers.util.ImageUtil;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private FragmentTabHost profileTabHost;

    public ProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        this.initUserInformationFields(rootView);
        this.initTabHost(rootView);

        return rootView;
    }

    private void initUserInformationFields(View rootView) {
        TextView usernameTextView = (TextView) rootView.findViewById(R.id.user_name_text_view);
        TextView userCityTextView = (TextView) rootView.findViewById(R.id.user_city_text_view);
        CircleImageView userImageView = (CircleImageView) rootView.findViewById(R.id.user_image_view);

        userImageView.setImageBitmap(this.getUserPhoto());
        usernameTextView.setText(this.getUserNameFromSingleton());
        userCityTextView.setText(this.getUserCityFromSingleton());



    }

    private void initTabHost(View rootView) {

        this.profileTabHost = (FragmentTabHost) rootView.findViewById(R.id.place_tabHost);
        this.profileTabHost.setup(this.getActivity(), this.getChildFragmentManager(), android.R.id.tabcontent);

        TabHost.TabSpec donatedBooksTab   = this.profileTabHost.newTabSpec("donatedBooksTab");
        TabHost.TabSpec retrievedBooksTab = this.profileTabHost.newTabSpec("retrievedBooksTab");
        TabHost.TabSpec favoriteBooksTab  = this.profileTabHost.newTabSpec("favoriteBooksTab");

        donatedBooksTab.setIndicator("DONATED");
        retrievedBooksTab.setIndicator("RETRIEVED");
        favoriteBooksTab.setIndicator("FAVORITES");

        this.profileTabHost.addTab(donatedBooksTab, DonatedBooksFragment.class, null);
        this.profileTabHost.addTab(retrievedBooksTab, RetrievedBooksFragment.class, null);
        this.profileTabHost.addTab(favoriteBooksTab, FavoriteBooksFragment.class, null);

        this.profileTabHost.setCurrentTab(0);

        this.stylizeTabsTextView(this.profileTabHost);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        this.profileTabHost = null;
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

    public String getUserNameFromSingleton() {
        String username;

        try {
            username = MockSingleton.INSTANCE.user.getName();

            if (username.isEmpty()) {
                username = "No name found...";
            }
        }
        catch (NullPointerException e){
            username = "Guest";
        }

        return username;
    }

    public String getUserCityFromSingleton() {
        String city;

        try {
            city = MockSingleton.INSTANCE.user.getCity();

            if (city.isEmpty()) {
                city = "X-Land City";
            }
        }
        catch (NullPointerException e){
            city = "X-Land City";
        }

        return city;
    }

    private Bitmap getUserPhoto() {
        Bitmap userPhotoBitmap;

        try {
            userPhotoBitmap = ImageUtil.StringToBitMap(MockSingleton.INSTANCE.user.getPhoto2());
        }
        catch (Exception e){
            userPhotoBitmap = ((BitmapDrawable) ContextCompat.getDrawable(getActivity(), R.drawable.ic_person_giant)).getBitmap();
        }

        return userPhotoBitmap;
    }
}
