package br.edu.ifce.swappers.swappers.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.dao.BookDAO;
import br.edu.ifce.swappers.swappers.fragments.tabs.detail_book.ReadersCommentsFragment;
import br.edu.ifce.swappers.swappers.fragments.tabs.detail_book.SynopsisFragment;
import br.edu.ifce.swappers.swappers.model.Book;
import br.edu.ifce.swappers.swappers.model.Place;
import br.edu.ifce.swappers.swappers.model.User;
import br.edu.ifce.swappers.swappers.util.AndroidUtils;
import br.edu.ifce.swappers.swappers.util.BookInterface;
import br.edu.ifce.swappers.swappers.util.CategoryBook;
import br.edu.ifce.swappers.swappers.util.FavoriteTask;
import br.edu.ifce.swappers.swappers.util.SwappersToast;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailBookActivity extends AppCompatActivity implements BookInterface{

    private FragmentTabHost bookDetailTabHost;
    private boolean flag = true;
    TextView nameBook;
    TextView authourBook;
    TextView editorBook;
    CircleImageView photoBook;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
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

        toolbar.setTitle("Detail Book");

        if(book.getTitle().length() > 45){
            StringBuilder titleBook = new StringBuilder(book.getTitle());
            titleBook.setCharAt(42, '.');
            titleBook.setCharAt(43, '.');
            titleBook.setCharAt(44, '.');
            nameBook.setText(titleBook);
        }else{
            nameBook.setText(book.getTitle());
        }

        if(book.getAuthor().length() > 20){
            StringBuilder authorBook = new StringBuilder(book.getAuthor());
            authorBook.setCharAt(18, '.');
            authorBook.setCharAt(19, '.');
            authorBook.setCharAt(20, '.');
            authourBook.setText(authorBook);
        }else{
            authourBook.setText(book.getAuthor());
        }

        if(book.getPublisher().length() > 25){
            StringBuilder editorBooks = new StringBuilder(book.getPublisher());
            editorBooks.setCharAt(22, '.');
            editorBooks.setCharAt(23, '.');
            editorBooks.setCharAt(24, '.');
            editorBook.setText(editorBooks);
        }else {
            editorBook.setText(book.getPublisher());
        }


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

                registryFavoriteBookWS();

                /*
                if (flag){
                    Drawable  drawable  = getResources().getDrawable(R.drawable.ic_is_book_favorite);
                    imgView.setImageDrawable(drawable);
                    flag = false;
                }else {
                    imgView.setImageDrawable(null);
                    flag = true;
                }
                **/

            }
        };
    }

    private void setFavoriteBookView(){
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

    @Override
    public void saveBookBaseLocal() {
        BookDAO bookDAO = new BookDAO(this);
        bookDAO.insert(book, CategoryBook.FAVORITE);

        setFavoriteBookView();
    }

    private void registryFavoriteBookWS(){
        User user = new User();
        user.setId(MockSingleton.INSTANCE.user.getId());

        user.setBook(book);

        FavoriteTask favoriteTask = new FavoriteTask(this,this);
        favoriteTask.execute(user);
    }

    private View.OnClickListener makeDonateListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent donateListPointIntent = new Intent(getApplicationContext(), DonationsListPointActivity.class);
              //  donateListPointIntent.putExtra(AndroidUtils.BOOK_INTENT_CODE_ID, AndroidUtils.BOOK_DONATION_INTENT_CODE);
                donateListPointIntent.putExtra(AndroidUtils.SELECTED_BOOK_ID, book);

                startActivity(donateListPointIntent);

            }
        };
    }

    private View.OnClickListener makeAdoptListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!getPlaces().isEmpty()) {
                    Intent adoptListPointIntent = new Intent(getApplicationContext(), AdoptionListPointActivity.class);
                    //donateListPointIntent.putExtra(AndroidUtils.BOOK_INTENT_CODE_ID, AndroidUtils.BOOK_ADOPTION_INTENT_CODE);
                    adoptListPointIntent.putExtra(AndroidUtils.SELECTED_BOOK_ID, book);

                    startActivity(adoptListPointIntent);
                }else{
                    Toast toast = SwappersToast.makeText(getApplication(), "Este livro não está disponível para adoção!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        };
    }


    private ArrayList<Place> getPlaces(){
        ArrayList<Place> places = MockSingleton.INSTANCE.getPlaces();
        ArrayList<Place> placeRetrieved = new ArrayList<>();

        for (int i =0; i<places.size();i++) {
            for (int j =0; j<places.get(i).getBooks().size();j++)
                if (places.get(i).getBooks().get(j).getId().equals(book.getId())){
                    placeRetrieved.add(places.get(i));
                }
        }
        return placeRetrieved;
    }

}
