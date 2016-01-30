package br.edu.ifce.swappers.swappers.fragments.tabs.statistics;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.BestPlaceInterface;
import br.edu.ifce.swappers.swappers.miscellaneous.tasks.StatisticPlaceTask;
import br.edu.ifce.swappers.swappers.miscellaneous.utils.ImageUtil;
import br.edu.ifce.swappers.swappers.model.Place;
import de.hdodenhof.circleimageview.CircleImageView;


public class HotSpotsFragment extends Fragment implements BestPlaceInterface{

    ImageButton nextHotspotImageButton;
    ImageButton previousHotspotImageButton;

    CircleImageView coverHotspotCircleImageView;

    TextView nameHotspotTextView;
    TextView addressHotspotTextView;
    TextView cityHotspotTextView;
    TextView retrievedHotspotTextView;
    TextView receivedHotspotTextView;
    private int index = 0;

    ArrayList<Place> bestPlace = new ArrayList<>();


    public HotSpotsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_hot_spots, container, false);
        this.initViewComponents(rootView);
        this.initViewListeners();

        if(MockSingleton.INSTANCE.getStatisticPlace().isEmpty()) {
            String city = MockSingleton.INSTANCE.user.getCity();
            String state = MockSingleton.INSTANCE.user.getState();
            if(city != null) {
                StatisticPlaceTask statisticPlaceTask = new StatisticPlaceTask(getActivity(), this);
                statisticPlaceTask.execute(city, state);
            }else{
                Toast.makeText(getActivity(),getString(R.string.place_city_state_not_found),Toast.LENGTH_LONG).show();
            }
        }else{
            bestPlace = MockSingleton.INSTANCE.getStatisticPlace();
            updateCardView(index);
        }
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
                if(!bestPlace.isEmpty()) {
                    index--;
                    if (index < 0) index = bestPlace.size() - 1;
                    updateCardView(index);
                }
            }
        };
    }

    private View.OnClickListener nextHotspotClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bestPlace.isEmpty()) {
                    index++;
                    if (index >= bestPlace.size()) index = 0;
                    updateCardView(index);
                }
            }
        };
    }

    @Override
    public void updateStatisticPlace(ArrayList<Place> placesList) {
        bestPlace = placesList;
        MockSingleton.INSTANCE.statisticPlace = bestPlace;

        if(!bestPlace.isEmpty()){
            updateCardView(this.index);
        }else{
            this.nameHotspotTextView.setText(getString(R.string.place_name_for_no_place_statistics_found));
            this.addressHotspotTextView.setText(getString(R.string.place_address_for_no_place_statistics_found));
            this.cityHotspotTextView.setText(getString(R.string.place_city_for_no_place_statistics_found));
            this.receivedHotspotTextView.setText(getString(R.string.donations_text_for_no_month_found));
            this.retrievedHotspotTextView.setText(getString(R.string.adoptions_text_for_no_month_found));
        }
    }

    private void updateCardView(int index){
        this.nameHotspotTextView.setText(bestPlace.get(index).getName());
        this.addressHotspotTextView.setText(bestPlace.get(index).getStreet() + ", " +bestPlace.get(index).getNumber());
        this.cityHotspotTextView.setText(bestPlace.get(index).getCity());

        int recovered = bestPlace.get(index).getRecovered();
        int donated   = bestPlace.get(index).getDonation();

        this.receivedHotspotTextView.setText(String.format(getString(R.string.donations_text_for_place_statistics), donated));
        this.retrievedHotspotTextView.setText(String.format(getString(R.string.adoptions_text_for_place_statistics),recovered ));

        this.coverHotspotCircleImageView.setImageBitmap(ImageUtil.stringToBitMap(bestPlace.get(index).getPhoto2()));
    }
}
