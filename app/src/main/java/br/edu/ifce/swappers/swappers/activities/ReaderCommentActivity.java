package br.edu.ifce.swappers.swappers.activities;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.model.Book;
import br.edu.ifce.swappers.swappers.model.Review;
import br.edu.ifce.swappers.swappers.util.ImageUtil;
import br.edu.ifce.swappers.swappers.util.ReviewTask;

import static br.edu.ifce.swappers.swappers.util.ImageUtil.*;

public class ReaderCommentActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText commentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader_comment);

        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.initToolbar();

        commentEditText = (EditText) findViewById(R.id.new_comment_edit_text);
        commentEditText.addTextChangedListener(this.buildTextWatcher());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reader_comment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.review_menu){
            sendReviewToWS();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendReviewToWS() {
        Integer userId      = MockSingleton.INSTANCE.user.getId();
        Book book           = (Book) getIntent().getExtras().get("CURRENT_BOOK");
        String reviewText   = commentEditText.getText().toString();

        Review review = new Review(userId, book, reviewText);

        ReviewTask task = new ReviewTask(this);
        task.execute(review);

    }

    private void initToolbar() {
        toolbar.setTitle(MockSingleton.INSTANCE.user.getName()); /*Inserir a consulta ao banco de dados que retornará o título do livro*/
        toolbar.setSubtitle("250 caracteres restantes");
        Bitmap userPhoto = StringToBitMap(MockSingleton.INSTANCE.user.getPhoto2());
        Bitmap userPhotoResized = Bitmap.createScaledBitmap(userPhoto, 56, 56, false);

        toolbar.setLogo(new BitmapDrawable(getResources(), userPhotoResized));

        if (toolbar != null){
            this.setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
    }

    private TextWatcher buildTextWatcher(){
        return new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String subtitle;
                int residualChars = 250 - s.length();

                if (residualChars > 1)
                    subtitle = String.format("%d caracteres disponíveis", residualChars);
                else
                    subtitle = String.format("%d caracter disponível", residualChars);

                toolbar.setSubtitle(subtitle);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }
}
