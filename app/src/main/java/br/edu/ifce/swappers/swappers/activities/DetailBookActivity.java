package br.edu.ifce.swappers.swappers.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.fragments.tabs.detail_book.ReadersCommentsFragment;
import br.edu.ifce.swappers.swappers.fragments.tabs.detail_book.SynopsisFragment;
import br.edu.ifce.swappers.swappers.util.SwappersToast;

public class DetailBookActivity extends AppCompatActivity {

    private FragmentTabHost bookDetailTabHost;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_book);


//        findViewById(R.id.floating_action_adop).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SwappersToast.makeText(DetailBookActivity.this, "This book has been adopted by you! <3", Toast.LENGTH_SHORT).show();
//            }
//        });

        findViewById(R.id.floating_action_donate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwappersToast.makeText(DetailBookActivity.this, "This book has been donated by you! <3", Toast.LENGTH_SHORT).show();;
            }
        });

        findViewById(R.id.floating_action_favorite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imgView =(ImageView) findViewById(R.id.is_book_favorite);
                if (flag){
                    Drawable  drawable  = getResources().getDrawable(R.drawable.ic_is_book_favorite);
                    imgView.setImageDrawable(drawable);
                    flag = false;
                }else {
                    imgView.setImageDrawable(null);
                    flag = true;
                }

            }
        });

        findViewById(R.id.floating_action_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReaderCommentActivity.class);
                startActivity(intent);
            }
        });

        this.initToolbar();
        this.initTabHost();

    }

//    @Override
//    public void onBackPressed() {
//        finish();
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.bookDetailTabHost = null;
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("A Book"); /*Inserir a consulta ao banco de dados que retornará o título do livro*/
        if (toolbar != null){

            this.setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
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
