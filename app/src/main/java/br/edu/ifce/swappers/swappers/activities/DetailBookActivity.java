package br.edu.ifce.swappers.swappers.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toolbar;


import br.edu.ifce.swappers.swappers.R;

public class DetailBookActivity extends AppCompatActivity {

    private FragmentTabHost bookDetailTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_book);
        this.initToolbar();
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
}
