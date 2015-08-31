package br.edu.ifce.swappers.swappers.activities;

import android.content.res.ColorStateList;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.fragments.tabs.books.NearBooksFragment;
import br.edu.ifce.swappers.swappers.fragments.tabs.books.RecommendationsFragment;
import br.edu.ifce.swappers.swappers.fragments.tabs.detail_book.ReadersCommentsFragment;
import br.edu.ifce.swappers.swappers.fragments.tabs.detail_book.SynopsisFragment;

public class BookActivity extends AppCompatActivity {

    private FragmentTabHost tabHost;
    Toolbar toolbar;

    public BookActivity() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        initTabHost();
        initToolbar();

    }

    private void initToolbar() {
        if (toolbar != null){
            this.setSupportActionBar(toolbar);
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            this.getSupportActionBar().setHomeButtonEnabled(true);

        }
    }

    private void initTabHost() {

        this.tabHost = (FragmentTabHost) findViewById(R.id.books_tabHost);
        this.tabHost.setup(this, this.getSupportFragmentManager(), android.R.id.tabcontent);


        TabHost.TabSpec nearBooksTab     = this.tabHost.newTabSpec("nearBooksTab");
        TabHost.TabSpec recommendationsTab   = this.tabHost.newTabSpec("recommendationsTab");

        nearBooksTab.setIndicator("NEAR BOOKS");
        recommendationsTab.setIndicator("RECOMMENDATIONS");

        this.tabHost.addTab(nearBooksTab, NearBooksFragment.class, null);
        this.tabHost.addTab(recommendationsTab, RecommendationsFragment.class, null);

        this.tabHost.setCurrentTab(1);

        this.stylizeTabsTextView(tabHost);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book, menu);
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
}
