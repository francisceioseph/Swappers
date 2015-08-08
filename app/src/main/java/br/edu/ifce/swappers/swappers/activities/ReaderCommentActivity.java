package br.edu.ifce.swappers.swappers.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import br.edu.ifce.swappers.swappers.R;

public class ReaderCommentActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader_comment);

        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.initToolbar();

        EditText commentEditText = (EditText) findViewById(R.id.new_comment_edit_text);
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

    private void initToolbar() {
        toolbar.setTitle("User Name"); /*Inserir a consulta ao banco de dados que retornará o título do livro*/
        toolbar.setSubtitle("250 caracteres restantes");
        toolbar.setLogo(R.drawable.ic_star);

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
                int residualChars = 250 - count;

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
