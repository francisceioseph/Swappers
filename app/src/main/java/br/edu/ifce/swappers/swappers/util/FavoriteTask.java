package br.edu.ifce.swappers.swappers.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.Toast;

import br.edu.ifce.swappers.swappers.model.User;
import br.edu.ifce.swappers.swappers.webservice.DonationService;
import br.edu.ifce.swappers.swappers.webservice.FavoriteService;

/**
 * Created by Bolsista on 05/09/2015.
 */
public class FavoriteTask extends AsyncTask<User,Void,Integer> {

    private Context context;
    private BookInterface bookInterface;
    private ProgressDialog progressDialog;

    public FavoriteTask(Context context,BookInterface bookInterface) {
        this.context = context;
        this.bookInterface = bookInterface;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Favoritando ...");
        progressDialog.show();
    }

    @Override
    protected Integer doInBackground(User... params) {
        return FavoriteService.registerFavoriteWS(params[0]);
    }

    @Override
    protected void onPostExecute(Integer statusCode) {
        Toast toast;
        progressDialog.dismiss();
        if(statusCode == 200){
            bookInterface.saveBookBaseLocal();
            //toast = SwappersToast.makeText(context,"Doação do livro efetuada com sucesso!", Toast.LENGTH_LONG);
            //toast.setGravity(Gravity.CENTER, 0, 0);
            //toast.show();
        }else {
            toast = SwappersToast.makeText(context,"Falha ao favoritar livro! Tente novamente!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}
