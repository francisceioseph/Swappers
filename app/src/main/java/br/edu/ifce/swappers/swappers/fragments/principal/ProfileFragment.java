package br.edu.ifce.swappers.swappers.fragments.principal;


import android.content.res.ColorStateList;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.fragments.tabs.profile.DonatedBooksFragment;
import br.edu.ifce.swappers.swappers.fragments.tabs.profile.FavoriteBooksFragment;
import br.edu.ifce.swappers.swappers.fragments.tabs.profile.RetrievedBooksFragment;

public class ProfileFragment extends Fragment {

    private FragmentTabHost profileTabHost;

    public ProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        this.profileTabHost = (FragmentTabHost) rootView.findViewById(R.id.profile_tabHost);
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

        return rootView;
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
            tabTextView = (TextView) tabView.findViewById(android.R.id.title);
            tabTextView.setTextColor(tabTextColors);
        }
    }
}
