package br.edu.ifce.swappers.swappers.fragments.principal;


import android.app.Fragment;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import br.edu.ifce.swappers.swappers.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BooksFragment extends Fragment {

    private TabHost tabHost;

    private static final int NEAR_BOOKS_TAB_POSITION = 0;
    private static final int RECOMMENDATIONS_TAB_POSITION = 1;


    public BooksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_books, container, false);

        this.tabHost = (TabHost) rootView.findViewById(R.id.tabHost);
        this.tabHost.setup();

        TabHost.TabSpec nearBooks       = this.tabHost.newTabSpec("nearBooks");
        TabHost.TabSpec recommendations = this.tabHost.newTabSpec("recommendations");

        nearBooks.setIndicator("NEAR BOOKS");
        nearBooks.setContent(R.id.nearBooks);

        recommendations.setIndicator("RECOMMENDATIONS");
        recommendations.setContent(R.id.recommendations);

        this.tabHost.addTab(nearBooks);
        this.tabHost.addTab(recommendations);

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
