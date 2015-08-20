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
import br.edu.ifce.swappers.swappers.activities.DetailBookActivity;
import br.edu.ifce.swappers.swappers.fragments.tabs.detail_book.ReadersCommentsFragment;
import br.edu.ifce.swappers.swappers.fragments.tabs.detail_book.SynopsisFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailBookFragment extends Fragment {

    private FragmentTabHost detailBookTabHost;
    private DetailBookActivity detailBookActivity;

    public DetailBookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_book, container, false);

       this.detailBookTabHost = (FragmentTabHost) rootView.findViewById(R.id.books_tabHost);
        this.detailBookTabHost.setup(this.getActivity(), this.getChildFragmentManager(), android.R.id.tabcontent);

        TabHost.TabSpec readersCommentsTab   = this.detailBookTabHost.newTabSpec("readersCommentsTab");
        TabHost.TabSpec synopsisTab = this.detailBookTabHost.newTabSpec("synopsisTab");


        readersCommentsTab.setIndicator("COMMENTS READERS");
        synopsisTab.setIndicator("SYNOPSIS");


        this.detailBookTabHost.addTab(readersCommentsTab, ReadersCommentsFragment.class, null);
        this.detailBookTabHost.addTab(synopsisTab, SynopsisFragment.class, null);


        this.detailBookTabHost.setCurrentTab(0);

        this.stylizeTabsTextView(this.detailBookTabHost);

        return rootView;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        this.detailBookTabHost = null;
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
