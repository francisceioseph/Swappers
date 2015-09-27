package br.edu.ifce.swappers.swappers.util;

import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.Toast;

import br.edu.ifce.swappers.swappers.model.User;
import br.edu.ifce.swappers.swappers.webservice.RetrievedService;

/**
 * Created by Bolsista on 04/09/2015.
 */
public class RetrievedTask extends AsyncTask<User,Void,Integer> {

    private Context context;
    private BookInterface bookInterface;

    public RetrievedTask(Context context,BookInterface bookInterface) {
        this.context = context;
        this.bookInterface = bookInterface;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Integer doInBackground(User... params) {
        return RetrievedService.registerRecoveredWS(params[0]);
    }

    @Override
    protected void onPostExecute(Integer statusCode) {
        Toast toast;
        if(statusCode == 200){
            bookInterface.saveBookBaseLocal();
            toast = SwappersToast.makeText(context,"Adoção registrada com sucesso!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }else {
            toast = SwappersToast.makeText(context,"Falha ao registrar adoção do livro!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}
