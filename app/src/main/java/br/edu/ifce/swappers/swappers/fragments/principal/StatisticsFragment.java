package br.edu.ifce.swappers.swappers.fragments.principal;


import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.fragments.tabs.statistics.BestBooksFragment;
import br.edu.ifce.swappers.swappers.fragments.tabs.statistics.DonatorsFragment;
import br.edu.ifce.swappers.swappers.fragments.tabs.statistics.HotSpotsFragment;

public class StatisticsFragment extends Fragment {

    private FragmentTabHost tabHost;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_statistics, container, false);

        this.tabHost = (FragmentTabHost) rootView.findViewById(R.id.statistics_tabHost);
        this.tabHost.setup(this.getActivity(), this.getChildFragmentManager(), android.R.id.tabcontent);

        TabHost.TabSpec donators       = this.tabHost.newTabSpec("donators");
        TabHost.TabSpec hotspots = this.tabHost.newTabSpec("hotspots");
        TabHost.TabSpec bestBooks = this.tabHost.newTabSpec("bestBooks");

        donators.setIndicator("DONATORS");
        hotspots.setIndicator("HOTSPOTS");
        bestBooks.setIndicator("BEST BOOKS");

        this.tabHost.addTab(donators, DonatorsFragment.class, null);
        this.tabHost.addTab(hotspots, HotSpotsFragment.class, null);
        this.tabHost.addTab(bestBooks, BestBooksFragment.class, null);

        this.tabHost.setCurrentTab(0);

        this.stylizeTabsTextView(this.tabHost);

        return rootView;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        this.tabHost = null;
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
