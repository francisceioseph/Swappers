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
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.fragments.tabs.profile.DonatedBooksFragment;
import br.edu.ifce.swappers.swappers.fragments.tabs.profile.FavoriteBooksFragment;
import br.edu.ifce.swappers.swappers.fragments.tabs.profile.RetrievedBooksFragment;
import br.edu.ifce.swappers.swappers.miscellaneous.utils.AndroidUtils;
import br.edu.ifce.swappers.swappers.miscellaneous.utils.ImageUtil;
import br.edu.ifce.swappers.swappers.model.User;
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
        ImageView coverPhoto = (ImageView) rootView.findViewById(R.id.user_cover_photo);

        coverPhoto.setImageBitmap(this.getUserCoverPhoto());
        userImageView.setImageBitmap(this.getUserPhoto());

        if(MockSingleton.INSTANCE.user.getBirthday() != null && MockSingleton.INSTANCE.user.getBirthday() != 0){
            usernameTextView.setText(this.getUserNameFromSingleton() + ", " + this.getUserAge());
        }
        else {
            usernameTextView.setText(this.getUserNameFromSingleton());
        }
        userCityTextView.setText(this.getUserCityFromSingleton());
    }

    private void initTabHost(View rootView) {

        this.profileTabHost = (FragmentTabHost) rootView.findViewById(R.id.place_tabHost);
        this.profileTabHost.setup(this.getActivity(), this.getChildFragmentManager(), android.R.id.tabcontent);

        TabHost.TabSpec donatedBooksTab   = this.profileTabHost.newTabSpec("donatedBooksTab");
        TabHost.TabSpec retrievedBooksTab = this.profileTabHost.newTabSpec("retrievedBooksTab");
        TabHost.TabSpec favoriteBooksTab  = this.profileTabHost.newTabSpec("favoriteBooksTab");

        donatedBooksTab.setIndicator(getString(R.string.donated_tab_title));
        retrievedBooksTab.setIndicator(getString(R.string.retrieved_tab_title));
        favoriteBooksTab.setIndicator(getString(R.string.favourited_tab_title));

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
        User user  = MockSingleton.INSTANCE.user;
        String username = getString(R.string.guest_name);

        try {
            if (!user.getName().isEmpty())
                username = user.getName();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return username;
    }

    public String getUserCityFromSingleton() {
        User user = MockSingleton.INSTANCE.user;
        String city = getString(R.string.guest_city);

        try{
            if (!user.getCity().isEmpty())
                city = user.getCity();
        }
        catch (Exception e){
           e.printStackTrace();
        }

        return city;
    }

    public int getUserAge(){
        int age;

        Calendar dateOfToday = Calendar.getInstance();

        Calendar birthDateCalendar = new GregorianCalendar();
        birthDateCalendar.setTimeInMillis(MockSingleton.INSTANCE.user.getBirthday());

        if(dateOfToday.get(Calendar.MONTH) > birthDateCalendar.get(Calendar.MONTH)){
            age = dateOfToday.get(Calendar.YEAR) - birthDateCalendar.get(Calendar.YEAR);
        }
        else if (dateOfToday.get(Calendar.MONTH) == birthDateCalendar.get(Calendar.MONTH)){
            if (dateOfToday.get(Calendar.DAY_OF_MONTH) >= birthDateCalendar.get(Calendar.DAY_OF_MONTH)){
                age = dateOfToday.get(Calendar.YEAR) - birthDateCalendar.get(Calendar.YEAR);
            }
            else
                age = (dateOfToday.get(Calendar.YEAR) - birthDateCalendar.get(Calendar.YEAR))-1;
        }
        else {
            age = (dateOfToday.get(Calendar.YEAR) - birthDateCalendar.get(Calendar.YEAR))-1;
        }

        return age;
    }

    private Bitmap getUserPhoto() {
        Bitmap userPhotoBitmap;
        User user = MockSingleton.INSTANCE.user;

        try {

            if (!user.getPhoto2().isEmpty()) {
                String photoBase64 = AndroidUtils.loadProfilePictureBase64(getActivity().getApplicationContext());
                userPhotoBitmap = ImageUtil.stringToBitMap(photoBase64);
            }
            else {
                userPhotoBitmap = ((BitmapDrawable) ContextCompat.getDrawable(getActivity(), R.drawable.ic_person_giant)).getBitmap();
            }

        }
        catch (Exception e){
            userPhotoBitmap = ((BitmapDrawable) ContextCompat.getDrawable(getActivity(), R.drawable.ic_person_giant)).getBitmap();
        }

        return userPhotoBitmap;
    }

    private Bitmap getUserCoverPhoto() {
        String photoBase64 = AndroidUtils.loadCoverPictureBase64(getActivity().getApplicationContext());
        return ImageUtil.stringToBitMap(photoBase64);
    }
}
