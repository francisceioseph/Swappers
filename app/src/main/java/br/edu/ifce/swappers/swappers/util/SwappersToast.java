package br.edu.ifce.swappers.swappers.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.ifce.swappers.swappers.R;

/**
 * Created by francisco on 07/06/15.
 */
public class SwappersToast {

    public static Toast makeText(Context context, String text, int durantion){

        LayoutInflater inflater;
        View rootView;
        TextView toastTextView;
        Toast toast;

        inflater      = LayoutInflater.from(context);
        rootView      = inflater.inflate(R.layout.swappers_custom_toast, null);
        toastTextView = (TextView) rootView.findViewById(R.id.swappers_toast_text);

        toastTextView.setText(text);

        toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 0);
        toast.setDuration(durantion);
        toast.setView(rootView);

        return toast;
    }
}
