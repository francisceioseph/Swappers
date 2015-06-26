package br.edu.ifce.swappers.swappers.fragments.principal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.activities.ReferencedLibrariesActivity;

public class AboutFragment extends Fragment {

    TextView versionTextView;

    public AboutFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        this.versionTextView = (TextView) rootView.findViewById(R.id.about_version_text);
        this.versionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent referencedComponents = new Intent(getActivity().getApplicationContext(), ReferencedLibrariesActivity.class);
                getActivity().startActivity(referencedComponents);
            }
        });

        return rootView;
    }

}
