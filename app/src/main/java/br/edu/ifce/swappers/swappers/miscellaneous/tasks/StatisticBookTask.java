package br.edu.ifce.swappers.swappers.miscellaneous.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.model.Book;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.BestBookInterface;
import br.edu.ifce.swappers.swappers.webservice.StatisticBookService;

/**
 * Created by gracyaneolveira on 15/10/2015.
 */
public class StatisticBookTask extends AsyncTask<Void,Void,ArrayList<Book>> {
    private Context context;
    private BestBookInterface bestBookInterface;
    private ProgressDialog progressDialog;

    public StatisticBookTask(Context context,BestBookInterface bestBookInterface){
        this.context = context;
        this.bestBookInterface = bestBookInterface;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.progress_dialog_loading_message));
        progressDialog.show();
    }

    @Override
    protected ArrayList<Book> doInBackground(Void... params) {
        return StatisticBookService.getBestBooksCurrentMonth();
    }

    @Override
    protected void onPostExecute(ArrayList<Book> books) {
        progressDialog.dismiss();
        this.bestBookInterface.updateStatisticBook(books);
    }
}
