package br.edu.ifce.swappers.swappers.activities;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toolbar;


import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.fragments.tabs.detail_book.ReadersCommentsFragment;
import br.edu.ifce.swappers.swappers.fragments.tabs.detail_book.SynopsisFragment;
import br.edu.ifce.swappers.swappers.fragments.tabs.detail_place.AvailableBooksFragment;
import br.edu.ifce.swappers.swappers.fragments.tabs.detail_place.InformationFragment;

public class DetailBookActivity extends AppCompatActivity {

    private FragmentTabHost bookDetailTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_book);
        this.initToolbar();
        this.initTabHost();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.bookDetailTabHost = null;
    }

    private void initToolbar() {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("A Book"); /*Inserir a consulta ao banco de dados que retornará o título do livro*/
        if (toolbar != null){
            this.setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void initTabHost() {

        this.bookDetailTabHost = (FragmentTabHost) findViewById(R.id.books_tabHost);
        this.bookDetailTabHost.setup(this, this.getSupportFragmentManager(), android.R.id.tabcontent);

        TabHost.TabSpec synopsisTab     = this.bookDetailTabHost.newTabSpec("synopsisTab");
        TabHost.TabSpec readersCommentsTab   = this.bookDetailTabHost.newTabSpec("readersCommentsTab");


        synopsisTab.setIndicator("SYNOPSES");
        readersCommentsTab.setIndicator("READERS COMMENTS");


        this.bookDetailTabHost.addTab(synopsisTab, SynopsisFragment.class, null);
        this.bookDetailTabHost.addTab(readersCommentsTab, ReadersCommentsFragment.class, null);


        this.bookDetailTabHost.setCurrentTab(1);

        this.stylizeTabsTextView(bookDetailTabHost);
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
