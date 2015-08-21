package br.edu.ifce.swappers.swappers.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
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
import com.squareup.picasso.Picasso;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.fragments.tabs.detail_book.ReadersCommentsFragment;
import br.edu.ifce.swappers.swappers.fragments.tabs.detail_book.SynopsisFragment;
import br.edu.ifce.swappers.swappers.model.Book;
import br.edu.ifce.swappers.swappers.util.AndroidUtils;
import br.edu.ifce.swappers.swappers.util.SwappersToast;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailBookActivity extends AppCompatActivity{

    private FragmentTabHost bookDetailTabHost;
    private boolean flag = true;
    TextView nameBook;
    TextView authourBook;
    TextView editorBook;
    CircleImageView photoBook;
    Toolbar toolbar;

    Book book;

    public DetailBookActivity(){

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_book);

        Intent currentIntent = getIntent();

        nameBook = (TextView) findViewById(R.id.title_detail_book);
        authourBook = (TextView) findViewById(R.id.author_detail_book);
        editorBook = (TextView) findViewById(R.id.editor_detail_book);
        photoBook = (CircleImageView) findViewById(R.id.photoBook);


        book = (Book) currentIntent.getSerializableExtra(AndroidUtils.SELECTED_BOOK_ID);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(book.getTitle());

        nameBook.setText(book.getTitle());
        authourBook.setText(book.getAuthor());
        editorBook.setText(book.getPublisher());



        if(!book.getPhoto().isEmpty()) {
            Picasso.with(getApplicationContext()).load(book.getPhoto()).into(photoBook);
        }else{
            Picasso.with(getApplicationContext()).load(R.drawable.blue_book).into(photoBook);
        }

        this.initFloatingButtons();
        this.initToolbar();
        this.initTabHost();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.bookDetailTabHost = null;
    }

    private void initToolbar() {
        if (toolbar != null){
            this.setSupportActionBar(toolbar);
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            this.getSupportActionBar().setHomeButtonEnabled(true);

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

    private void initFloatingButtons(){

        FloatingActionButton adoptButton    = (FloatingActionButton) findViewById(R.id.floating_action_adop);
        FloatingActionButton donateButton   = (FloatingActionButton) findViewById(R.id.floating_action_donate);
        FloatingActionButton favoriteButton = (FloatingActionButton) findViewById(R.id.floating_action_favorite);
        FloatingActionButton commentButton  = (FloatingActionButton) findViewById(R.id.floating_action_comment);

        adoptButton.setOnClickListener(this.makeAdoptListener());
        donateButton.setOnClickListener(this.makeDonateListener());
        favoriteButton.setOnClickListener(this.makeFavoriteListener());
        commentButton.setOnClickListener(this.makeCommentListener());
    }

    private View.OnClickListener makeCommentListener() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReaderCommentActivity.class);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener makeFavoriteListener() {
        return new View.OnClickListener() {
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
        };
    }

    private View.OnClickListener makeDonateListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent donateListPointIntent = new Intent(getApplicationContext(), DonationsListPointActivity.class);
                donateListPointIntent.putExtra(AndroidUtils.BOOK_INTENT_CODE_ID, AndroidUtils.BOOK_DONATION_INTENT_CODE);

                startActivity(donateListPointIntent);

            }
        };
    }

    private View.OnClickListener makeAdoptListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent donateListPointIntent = new Intent(getApplicationContext(), DonationsListPointActivity.class);
                donateListPointIntent.putExtra(AndroidUtils.BOOK_INTENT_CODE_ID, AndroidUtils.BOOK_ADOPTION_INTENT_CODE);

                startActivity(donateListPointIntent);
            }
        };
    }
}
