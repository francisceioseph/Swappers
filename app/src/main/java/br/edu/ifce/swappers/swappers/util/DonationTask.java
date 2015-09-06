package br.edu.ifce.swappers.swappers.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.Toast;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.dao.BookDAO;
import br.edu.ifce.swappers.swappers.model.User;
import br.edu.ifce.swappers.swappers.webservice.DonationService;

/**
 * Created by alessandra on 29/08/15.
 */
public class DonationTask extends AsyncTask<User,Void,Integer> {
    private ProgressDialog progressDialog;
    private Context context;
    private BookInterface bookInterface;

    public DonationTask(Context context,BookInterface bookInterface) {
        this.context = context;
        this.bookInterface = bookInterface;
    }


    @Override
    protected void onPreExecute() {
        /*progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Registrando doaçao...");
        progressDialog.show();*/
    }

    @Override
    protected Integer doInBackground(User... params) {
        return DonationService.registerDonationWS(params[0]);
    }


    @Override
    protected void onPostExecute(Integer statusCode) {
        Toast toast;
        MockSingleton.INSTANCE.statusCodeDonation = statusCode;
       // progressDialog.dismiss();
        if(statusCode == 200){
            bookInterface.saveBookBaseLocal();
            toast = SwappersToast.makeText(context,"Doação do livro efetuada com sucesso!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }else {
            toast = SwappersToast.makeText(context,"Falha ao registrar doação do livro!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}
