package br.edu.ifce.swappers.swappers.fragments.tabs.books;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.util.SwappersToast;
import de.hdodenhof.circleimageview.CircleImageView;

public class RecommendationsFragment extends Fragment {

    ImageButton nextRecommendationImageButton;
    ImageButton previousRecommendationImageButton;
    Button seePlacesOnMapsButton;

    CircleImageView coverRecommendationCircleImageView;

    TextView titleRecommendationTextView;
    TextView authorsRecommendationTextView;

    public RecommendationsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recomendations, container, false);

        this.initViewComponents(rootView);
        this.initViewListeners();

        return rootView;
    }

    private void initViewComponents(View rootView) {
        this.nextRecommendationImageButton      = (ImageButton) rootView.findViewById(R.id.next_recommendation_image_button);
        this.previousRecommendationImageButton  = (ImageButton) rootView.findViewById(R.id.previous_recommendation_image_button);
        this.seePlacesOnMapsButton              = (Button) rootView.findViewById(R.id.see_places_of_recommendation);

        this.coverRecommendationCircleImageView = (CircleImageView) rootView.findViewById(R.id.cover_recommendation_book);

        this.titleRecommendationTextView        = (TextView) rootView.findViewById(R.id.title_recommendation);
        this.authorsRecommendationTextView      = (TextView) rootView.findViewById(R.id.authors_recommendation);
    }

    private void initViewListeners() {
        this.nextRecommendationImageButton.setOnClickListener(this.nextRecommendationClickListener());
        this.previousRecommendationImageButton.setOnClickListener(this.previousRecommendationClickListener());
        this.seePlacesOnMapsButton.setOnClickListener(this.seePlacesOnMapsClickListener());
    }

    private View.OnClickListener seePlacesOnMapsClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwappersToast.makeText(getActivity(), "Opening Map... #SQN", Toast.LENGTH_SHORT).show();;
            }
        };
    }

    private View.OnClickListener previousRecommendationClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwappersToast.makeText(getActivity(), "Loading previous book... #SQN", Toast.LENGTH_SHORT).show();;
            }
        };
    }

    private View.OnClickListener nextRecommendationClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwappersToast.makeText(getActivity(), "Opening next book... #SQN", Toast.LENGTH_SHORT).show();;
            }
        };
    }
}
