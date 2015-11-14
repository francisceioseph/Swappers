package br.edu.ifce.swappers.swappers.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.dao.BookDAO;
import br.edu.ifce.swappers.swappers.fragments.principal.PlacesFragment;
import br.edu.ifce.swappers.swappers.fragments.tabs.detail_book.ReadersReviewFragment;
import br.edu.ifce.swappers.swappers.fragments.tabs.detail_book.SynopsisFragment;
import br.edu.ifce.swappers.swappers.model.Book;
import br.edu.ifce.swappers.swappers.model.Place;
import br.edu.ifce.swappers.swappers.model.User;
import br.edu.ifce.swappers.swappers.miscellaneous.utils.AndroidUtils;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.BookInterface;
import br.edu.ifce.swappers.swappers.miscellaneous.CategoryBook;
import br.edu.ifce.swappers.swappers.miscellaneous.tasks.FavoriteTask;
import br.edu.ifce.swappers.swappers.miscellaneous.tasks.RetrievedTask;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailBookActivity extends AppCompatActivity implements BookInterface{

    public interface RequestRecyclerViewUpdate{
        void reloadRecyclerView();
    }

    private Book book;
    private CircleImageView photoBook;
    private FragmentTabHost bookDetailTabHost;
    private RequestRecyclerViewUpdate recyclerViewDelegate;
    private TextView nameBook;
    private TextView authourBook;
    private TextView editorBook;
    private Toolbar toolbar;
    private ImageView favouritedImageView;
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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        favouritedImageView = (ImageView) findViewById(R.id.is_book_favorite);

        favouritedImageView.setImageDrawable(null);

        book = (Book) currentIntent.getSerializableExtra(AndroidUtils.SELECTED_BOOK_ID);

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
            Picasso.with(DetailBookActivity.this).load(book.getPhoto()).into(photoBook);
        }
        else {
            Picasso.with(DetailBookActivity.this).load(R.drawable.blue_book).into(photoBook);
        }

        this.initFloatingButtons();
        this.initToolbar();
        this.initTabHost();
        this.displayFavouriteBook();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.bookDetailTabHost = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AndroidUtils.ADD_COMMENT_INTENT_CODE && resultCode == RESULT_OK) {
            this.recyclerViewDelegate.reloadRecyclerView();
        }
    }

//    UI Initialization

    private void initToolbar() {
        if (toolbar != null){
            toolbar.setTitle(getResources().getString(R.string.books_detail_toolbar_title));

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

        synopsisTab.setIndicator(getString(R.string.synopsis_tab_title));
        readersCommentsTab.setIndicator(getString(R.string.readers_comments_tab_title));

        this.bookDetailTabHost.addTab(synopsisTab, SynopsisFragment.class, null);
        this.bookDetailTabHost.addTab(readersCommentsTab, ReadersReviewFragment.class, null);

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

        FloatingActionButton donateButton   = (FloatingActionButton) findViewById(R.id.floating_action_donate);
        FloatingActionButton favoriteButton = (FloatingActionButton) findViewById(R.id.floating_action_favorite);
        FloatingActionButton commentButton  = (FloatingActionButton) findViewById(R.id.floating_action_comment);
        FloatingActionButton adoptButton    = (FloatingActionButton) findViewById(R.id.floating_action_adop);

        donateButton.setOnClickListener(this.makeDonateListener());
        favoriteButton.setOnClickListener(this.makeFavoriteListener());
        commentButton.setOnClickListener(this.makeCommentListener());

        Intent currentIntent = getIntent();
        int originIntentCode = currentIntent.getIntExtra(AndroidUtils.ORIGIN_DETAIL_BOOK_TITLE, -1);

        if (originIntentCode == AndroidUtils.FROM_DETAIL_PLACE_INTENT_CODE){
            adoptButton.setVisibility(View.VISIBLE);
            adoptButton.setOnClickListener(this.makeAdoptListener());
        }
        else {
            adoptButton.setVisibility(View.INVISIBLE);
        }
    }

//  Some local and WS Save Methods.

    private void displayFavouriteBook(){
        BookDAO bookDAO = new BookDAO(this);
        boolean isBookFavourited = bookDAO.isBookFavourited(book.getId());

        if (isBookFavourited){
            Drawable  heartDrawable  = ContextCompat.getDrawable(this, R.drawable.ic_is_book_favorite);
            favouritedImageView.setImageDrawable(heartDrawable);
        }
        else {
            favouritedImageView.setImageDrawable(null);
        }
    }

    @Override
    public void saveFavouriteBookIntoLocalBase() {
        BookDAO bookDAO = new BookDAO(this);

        if (bookDAO.isBookFavourited(book.getId()) == false) {
            bookDAO.insert(book, CategoryBook.FAVORITE);
        }
        else {
            bookDAO.removeBookFromCategory(book.getId(), CategoryBook.FAVORITE.toString());
        }

        displayFavouriteBook();
    }

    @Override
    public void saveRetrievedBookIntoLocalBase(){
        BookDAO bookDAO = new BookDAO(this);
        bookDAO.insert(book, CategoryBook.RETRIEVED);

        removeBookFromCurrentPlace();
        adoptionMarker();
    }

    @Override
    public void saveDonatedBookIntoLocalBase(){

    }

    private void saveFavoriteBookIntoWS(){
        User user = new User();
        user.setId(MockSingleton.INSTANCE.user.getId());

        user.setBook(book);

        FavoriteTask favoriteTask = new FavoriteTask(this,this);
        favoriteTask.execute(user);
    }

    private int removeBookFromCurrentPlace(){
        int size = MockSingleton.INSTANCE.places.size();
        int idPlace = getIntent().getIntExtra(AndroidUtils.SELECTED_PLACE_ID, 0);
        ArrayList<Book> refreshBooks = new ArrayList<>();

        for(int i=0; i<size; i++) {
            if(idPlace==MockSingleton.INSTANCE.places.get(i).getId()) {
                for(int j = 0; j< MockSingleton.INSTANCE.places.get(i).getBooks().size(); j++){
                    if(!(MockSingleton.INSTANCE.places.get(i).getBooks().get(j).getId()).equals(book.getId())){
                        refreshBooks.add(MockSingleton.INSTANCE.places.get(i).getBooks().get(j));
                    }
                }
                MockSingleton.INSTANCE.places.get(i).setBooks(refreshBooks);
                return 1;
            }
        }
        return 0;
    }

    public void adoptionMarker(){
        PlacesFragment placesFragment = new PlacesFragment();
        int idPlace = getIntent().getIntExtra(AndroidUtils.SELECTED_PLACE_ID, 0);
        placesFragment.refreshMarker(idPlace, 2);
    }

    private void initRetrieved() {
        User user = new User();
        user.setId(MockSingleton.INSTANCE.user.getId());

        Place place = new Place();
        place.setId(getIntent().getIntExtra(AndroidUtils.SELECTED_PLACE_ID, 0));

        book.setPlace(place);
        user.setBook(book);

        RetrievedTask retrievedTask = new RetrievedTask(DetailBookActivity.this, this);
        retrievedTask.execute(user);
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

//    Dialogs

    private AlertDialog makeConfirmDialogForBookAdoption(){

    AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.SWDialogTheme);

    builder.setTitle(getString(R.string.confirm_book_adoption_dialog_message));
    builder.setIcon(R.drawable.ic_ask_place_donate);

    builder.setNegativeButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    });

    builder.setPositiveButton(getString(R.string.book_adoption_positive_button_title), new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();

            initRetrieved();
        }
    });

    return builder.create();
}

//    View Listeners for this class

    private View.OnClickListener makeCommentListener() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailBookActivity.this, ReaderReviewActivity.class);
                intent.putExtra("CURRENT_BOOK", book);

                startActivityForResult(intent, AndroidUtils.ADD_COMMENT_INTENT_CODE);
            }
        };
    }

    private View.OnClickListener makeFavoriteListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFavoriteBookIntoWS();
            }
        };
    }

    private View.OnClickListener makeDonateListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent donateListPointIntent = new Intent(DetailBookActivity.this, DonationsListPointActivity.class);
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
                    makeConfirmDialogForBookAdoption().show();
                }
                else{
                    AndroidUtils.makeDialog(DetailBookActivity.this, getString(R.string.dialog_error_title), getString(R.string.book_not_available_for_adoption)).show();
                }
            }
        };
    }

//    Getters and Setters

    public Book getBook() {
        return book;
    }

    public void setRecyclerViewDelegate(RequestRecyclerViewUpdate recyclerViewDelegate) {
        this.recyclerViewDelegate = recyclerViewDelegate;
    }

}
