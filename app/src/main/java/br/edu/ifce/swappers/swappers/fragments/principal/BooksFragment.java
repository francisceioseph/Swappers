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
import br.edu.ifce.swappers.swappers.fragments.tabs.books.NearBooksFragment;
import br.edu.ifce.swappers.swappers.fragments.tabs.books.RecommendationsFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class BooksFragment extends Fragment {

    private FragmentTabHost tabHost;

    public BooksFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_books, container, false);

        this.tabHost = (FragmentTabHost) rootView.findViewById(R.id.books_tabHost);
        this.tabHost.setup(this.getActivity(), this.getChildFragmentManager(), android.R.id.tabcontent);

        TabHost.TabSpec nearBooks       = this.tabHost.newTabSpec("nearBooks");
        TabHost.TabSpec recommendations = this.tabHost.newTabSpec("recommendations");

        nearBooks.setIndicator("NEAR BOOKS");
        recommendations.setIndicator("RECOMMENDATIONS");

        this.tabHost.addTab(nearBooks, NearBooksFragment.class, null);
        this.tabHost.addTab(recommendations, RecommendationsFragment.class, null);

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
            tabTextView = (TextView) tabView.findViewById(android.R.id.title);
            tabTextView.setTextColor(tabTextColors);
        }
    }
}
