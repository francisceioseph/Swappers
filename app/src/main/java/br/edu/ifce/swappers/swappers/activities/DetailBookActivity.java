package br.edu.ifce.swappers.swappers.activities;



import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.fragments.tabs.detail_book.ReadersCommentsFragment;
import br.edu.ifce.swappers.swappers.fragments.tabs.detail_book.SynopsisFragment;
import br.edu.ifce.swappers.swappers.model.Book;
import br.edu.ifce.swappers.swappers.util.AndroidUtils;
import de.hdodenhof.circleimageview.CircleImageView;


public class DetailBookActivity extends AppCompatActivity{

    private boolean flag = true;
    TextView nameBook;
    TextView authourBook;
    TextView editorBook;
    CircleImageView photoBook;
    Toolbar toolbar;
    Book book;
    private DrawerLayout mDrawerLayout;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail_book);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("Detail Book");
            setSupportActionBar(toolbar);

            final ActionBar ab = getSupportActionBar();

            ab.setDisplayHomeAsUpEnabled(true);

            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
            if (viewPager != null) {
                setupViewPager(viewPager);
            }


            Intent currentIntent = getIntent();
            book = (Book) currentIntent.getSerializableExtra(AndroidUtils.SELECTED_BOOK_ID);


            nameBook = (TextView) findViewById(R.id.title_detail_book);
            authourBook = (TextView) findViewById(R.id.author_detail_book);
            editorBook = (TextView) findViewById(R.id.editor_detail_book);
            photoBook = (CircleImageView) findViewById(R.id.photoBook);

            onCreateLabels();
            initFloatingButtons();

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);
        }


        public void onCreateLabels(){
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
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            return super.onOptionsItemSelected(item);
        }

        private void setupViewPager(ViewPager viewPager) {
            Adapter adapter = new Adapter(getSupportFragmentManager());
            adapter.addFragment(new SynopsisFragment(), "SYNOPSIS");
            adapter.addFragment(new ReadersCommentsFragment(), "READERS COMMENTS");
            viewPager.setAdapter(adapter);
        }



    private void initFloatingButtons(){

      /*  FloatingActionButton adoptButton    = (FloatingActionButton) findViewById(R.id.floating_action_adop);
        FloatingActionButton donateButton   = (FloatingActionButton) findViewById(R.id.floating_action_donate);
        FloatingActionButton favoriteButton = (FloatingActionButton) findViewById(R.id.floating_action_favorite);
        FloatingActionButton commentButton  = (FloatingActionButton) findViewById(R.id.floating_action_comment);

        adoptButton.setOnClickListener(this.makeAdoptListener());
        donateButton.setOnClickListener(this.makeDonateListener());
        favoriteButton.setOnClickListener(this.makeFavoriteListener());
        commentButton.setOnClickListener(this.makeCommentListener());*/
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
                    Drawable drawable  = getResources().getDrawable(R.drawable.ic_is_book_favorite);
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
        public class Adapter extends FragmentPagerAdapter {
            private final List<Fragment> mFragments = new ArrayList<>();
            private final List<String> mFragmentTitles = new ArrayList<>();

            public Adapter(FragmentManager fm) {
                super(fm);
            }

            public void addFragment(Fragment fragment, String title) {
                mFragments.add(fragment);
                mFragmentTitles.add(title);
            }

            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentTitles.get(position);
            }
        }

}
