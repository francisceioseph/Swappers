package br.edu.ifce.swappers.swappers.fragments.tabs.statistics;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.util.SwappersToast;
import de.hdodenhof.circleimageview.CircleImageView;


public class HotSpotsFragment extends Fragment {

    ImageButton nextHotspotImageButton;
    ImageButton previousHotspotImageButton;

    CircleImageView coverHotspotCircleImageView;

    TextView nameHotspotTextView;
    TextView addressHotspotTextView;
    TextView cityHotspotTextView;
    TextView retrievedHotspotTextView;
    TextView receivedHotspotTextView;


    public HotSpotsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_hot_spots, container, false);
        this.initViewComponents(rootView);
        this.initViewListeners();

        return rootView;
    }

    private void initViewComponents(View rootView) {
        this.nextHotspotImageButton      = (ImageButton) rootView.findViewById(R.id.next_place_image_button);
        this.previousHotspotImageButton  = (ImageButton) rootView.findViewById(R.id.previous_place_image_button);

        this.coverHotspotCircleImageView = (CircleImageView) rootView.findViewById(R.id.cover_best_place);

        this.nameHotspotTextView = (TextView) rootView.findViewById(R.id.best_place_name);
        this.addressHotspotTextView = (TextView) rootView.findViewById(R.id.best_place_address);
        this.cityHotspotTextView = (TextView) rootView.findViewById(R.id.best_place_city);
        this.retrievedHotspotTextView = (TextView) rootView.findViewById(R.id.retrieved_hotspot_text_view);
        this.receivedHotspotTextView = (TextView) rootView.findViewById(R.id.received_hotspot_text_view);

    }

    private void initViewListeners() {
        this.nextHotspotImageButton.setOnClickListener(this.nextHotspotClickListener());
        this.previousHotspotImageButton.setOnClickListener(this.previousHotspotClickListener());
    }

    private View.OnClickListener previousHotspotClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwappersToast.makeText(getActivity(), "Loading previous hotspot... #SQN", Toast.LENGTH_SHORT).show();;
            }
        };
    }

    private View.OnClickListener nextHotspotClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwappersToast.makeText(getActivity(), "Loading next hotspot... #SQN", Toast.LENGTH_SHORT).show();;
            }
        };
    }

}
