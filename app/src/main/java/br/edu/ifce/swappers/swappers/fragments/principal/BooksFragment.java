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

    private static final int NEAR_BOOKS_TAB_POSITION = 0;
    private static final int RECOMMENDATIONS_TAB_POSITION = 1;


    public BooksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_books, container, false);

        this.tabHost = (FragmentTabHost) rootView.findViewById(R.id.tabHost);
        this.tabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);

        TabHost.TabSpec nearBooks       = this.tabHost.newTabSpec("nearBooks");
        TabHost.TabSpec recommendations = this.tabHost.newTabSpec("recommendations");

        nearBooks.setIndicator("NEAR BOOKS");
        recommendations.setIndicator("RECOMMENDATIONS");

        this.tabHost.addTab(nearBooks, NearBooksFragment.class, null);
        this.tabHost.addTab(recommendations, RecommendationsFragment.class, null);

        this.tabHost.setCurrentTab(0);

        this.stylizeTabsTextView();

        return rootView;
    }

    private void stylizeTabsTextView(){
        ColorStateList tabTextColors;
        TabWidget tabWidget;
        TextView tabTextView;
        View tabView;

        int tabCount;

        tabWidget     = this.tabHost.getTabWidget();
        tabTextColors = this.getResources().getColorStateList(R.color.tab_text_selector);
        tabCount      = tabWidget.getTabCount();

        for (int i = 0; i < tabCount; i++){
            tabView = tabWidget.getChildTabViewAt(i);
            tabTextView = (TextView) tabView.findViewById(android.R.id.title);
            tabTextView.setTextColor(tabTextColors);
        }
    }
}
