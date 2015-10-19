package br.edu.ifce.swappers.swappers.fragments.tabs.statistics;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.nio.charset.Charset;
import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.BestPlaceInterface;
import br.edu.ifce.swappers.swappers.miscellaneous.tasks.StatisticPlaceTask;
import br.edu.ifce.swappers.swappers.miscellaneous.utils.ImageUtil;
import br.edu.ifce.swappers.swappers.model.Book;
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

//        nameHotspotTextView.setText("Winterfell");
//        addressHotspotTextView.setText("Norte");
//        cityHotspotTextView.setText("Sete Reinos");
//        retrievedHotspotTextView.setText("55 livros adotados");
//        receivedHotspotTextView.setText("70 livros doados");

        StatisticPlaceTask statisticPlaceTask = new StatisticPlaceTask(getActivity(),this);
        statisticPlaceTask.execute();

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

        if(!bestPlace.isEmpty()){
            updateCardView(this.index);
        }else{
            this.nameHotspotTextView.setText("Não houve nem doacao adocao este mês.");
            this.addressHotspotTextView.setText("Nenhum lugar");
            this.cityHotspotTextView.setText("Nenhuma cidade");
            this.receivedHotspotTextView.setText("0 livros doados");
            this.retrievedHotspotTextView.setText("0 livros adotados");
        }
    }

    private void updateCardView(int index){
        this.nameHotspotTextView.setText(bestPlace.get(index).getName());
        this.addressHotspotTextView.setText(bestPlace.get(index).getStreet() + ", " +bestPlace.get(index).getNumber());
        this.cityHotspotTextView.setText(bestPlace.get(index).getCity());
        this.receivedHotspotTextView.setText(bestPlace.get(index).getDonation() + " livros doados");
        this.retrievedHotspotTextView.setText(bestPlace.get(index).getRecovered() + " livros adotados");

        this.coverHotspotCircleImageView.setImageBitmap(ImageUtil.StringToBitMap(bestPlace.get(index).getPhoto2()));
    }
}
