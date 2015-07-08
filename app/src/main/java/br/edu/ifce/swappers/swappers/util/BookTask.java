package br.edu.ifce.swappers.swappers.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.model.Book;
import br.edu.ifce.swappers.swappers.webservice.BookService;

/**
 * Created by FAMÍLIA on 06/07/2015.
 */
public class BookTask extends AsyncTask<String,Void,ArrayList<Book>> {
    private ProgressDialog progressDialog;
    private Context context;

    public BookTask(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Buscando...");
        progressDialog.show();
    }

    @Override
    protected ArrayList<Book> doInBackground(String... params) {
        return BookService.getBooksByTitleWS(params[0]);
    }

    @Override
    protected void onProgressUpdate(Void... params) {
    }

    @Override
    protected void onPostExecute(ArrayList<Book> listBook) {

    }
}
