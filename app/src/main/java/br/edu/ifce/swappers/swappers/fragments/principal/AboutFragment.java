package br.edu.ifce.swappers.swappers.fragments.principal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.activities.ReferencedLibrariesActivity;
import br.edu.ifce.swappers.swappers.util.SwappersToast;
import de.hdodenhof.circleimageview.CircleImageView;

public class AboutFragment extends Fragment {

    private TextView versionTextView;
    private CircleImageView francisImageView;
    private int francisTapCounter;
    private Runnable resetFrancisTapCounterRunnable;
    private Handler resetFrancisTapCounterHandler;

    public AboutFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        this.versionTextView = (TextView) rootView.findViewById(R.id.about_version_text);
        this.francisImageView = (CircleImageView) rootView.findViewById(R.id.francis_photo);
        this.resetFrancisTapCounterRunnable = this.buildResetFrancisTapCounterRunnable();
        this.resetFrancisTapCounterHandler = new Handler();
        this.francisTapCounter = 0;

        this.versionTextView.setOnClickListener(this.onVersionTextViewClick());
        this.francisImageView.setOnClickListener(this.onFrancisImageViewClick());
        return rootView;
    }

    private Runnable buildResetFrancisTapCounterRunnable() {
        return new Runnable() {
            @Override
            public void run() {

                synchronized (AboutFragment.class) {
                    Log.e("EASTER EGGS", "RESET FRANCIS TAP CUNTER");
                    francisTapCounter = 0;
                }
            }
        };
    }

    private View.OnClickListener onFrancisImageViewClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                synchronized (AboutFragment.class) {
                    francisTapCounter++;
                }

                if(francisTapCounter == 1){
                    resetFrancisTapCounterHandler.postDelayed(resetFrancisTapCounterRunnable, 3000);
                }
                else if (francisTapCounter == 15) {
                    resetFrancisTapCounterHandler.removeCallbacks(resetFrancisTapCounterRunnable);
                    francisTapCounter = 0;
                    SwappersToast.makeText(getActivity().getApplicationContext(), "#loveWins", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private View.OnClickListener onVersionTextViewClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent referencedComponents = new Intent(getActivity().getApplicationContext(), ReferencedLibrariesActivity.class);
                getActivity().startActivity(referencedComponents);
            }
        };
    }

}
