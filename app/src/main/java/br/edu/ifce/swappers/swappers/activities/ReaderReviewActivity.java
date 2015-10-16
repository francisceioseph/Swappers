package br.edu.ifce.swappers.swappers.activities;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.net.HttpURLConnection;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.model.Book;
import br.edu.ifce.swappers.swappers.model.Review;
import br.edu.ifce.swappers.swappers.miscellaneous.utils.AndroidUtils;
import br.edu.ifce.swappers.swappers.miscellaneous.tasks.UploadReviewTask;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.UploadReviewTaskInterface;

import static br.edu.ifce.swappers.swappers.miscellaneous.utils.ImageUtil.*;

public class ReaderReviewActivity extends AppCompatActivity implements UploadReviewTaskInterface {

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

        UploadReviewTask task = new UploadReviewTask(this, this);
        task.execute(review);

    }

    private void initToolbar() {
        toolbar.setTitle(MockSingleton.INSTANCE.user.getName()); /*Inserir a consulta ao banco de dados que retornará o título do livro*/
        toolbar.setSubtitle("250 " + getString(R.string.readers_review_toolbar_subtitle_plural));
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
                    subtitle = String.format("%d ", residualChars) + getString(R.string.readers_review_toolbar_subtitle_plural);
                else
                    subtitle = String.format("%d ", residualChars) + getString(R.string.readers_review_toolbar_subtitle);

                toolbar.setSubtitle(subtitle);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    @Override
    public void onUploadReviewHadFinished(Integer status_code) {
        AlertDialog alertDialog;

        if (status_code == HttpURLConnection.HTTP_CREATED){
            alertDialog = AndroidUtils.makeDialog(this, getString(R.string.success_review_upload_dialog_message));
        }
        else{
            alertDialog = buildReviewUploadErrorDialog(getString(R.string.error_review_upload_dialog_message));
        }

        alertDialog.show();
    }

    private AlertDialog buildReviewUploadErrorDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.SWDialogTheme);
        builder.setTitle(getString(R.string.error_review_upload_dialog_title));
        builder.setMessage(message);
        builder.setPositiveButton(R.string.error_review_upload_dialog_positive_button_title, this.onErrorPostDialogPositiveButton());
        builder.setNegativeButton(getString(android.R.string.cancel), this.onErrorPostDialogNegativeButton());

        return builder.create();
    }

    private DialogInterface.OnClickListener onErrorPostDialogPositiveButton() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                sendReviewToWS();
            }
        };
    }

    private DialogInterface.OnClickListener onErrorPostDialogNegativeButton() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };
    }
}
