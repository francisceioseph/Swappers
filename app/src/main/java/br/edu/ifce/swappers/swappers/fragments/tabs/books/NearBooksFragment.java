package br.edu.ifce.swappers.swappers.fragments.tabs.books;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.model.Place;
import br.edu.ifce.swappers.swappers.util.SwappersToast;
import de.hdodenhof.circleimageview.CircleImageView;

public class NearBooksFragment extends Fragment {

    ImageButton nextNearBookImageButton;
    ImageButton previousNearBookImageButton;
    Button seePlacesOnMapsButton;

    CircleImageView coverNearBookCircleImageView;

    TextView titleNearBookTextView;
    TextView authorsNearBookTextView;

    List<Place> nearPlaces;

    public NearBooksFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_near_books, container, false);

        this.initViewComponents(rootView);
        this.initViewListeners();

        return rootView;
    }

    private void initViewComponents(View rootView) {
        this.nextNearBookImageButton      = (ImageButton) rootView.findViewById(R.id.next_book_image_button);
        this.previousNearBookImageButton  = (ImageButton) rootView.findViewById(R.id.previous_book_image_button);
        this.seePlacesOnMapsButton        = (Button) rootView.findViewById(R.id.see_places_of_near_book);

        this.coverNearBookCircleImageView = (CircleImageView) rootView.findViewById(R.id.cover_near_book);

        this.titleNearBookTextView        = (TextView) rootView.findViewById(R.id.title_near_book);
        this.authorsNearBookTextView      = (TextView) rootView.findViewById(R.id.authors_near_book);
    }

    private void initViewListeners() {
        this.nextNearBookImageButton.setOnClickListener(this.nextNearBookClickListener());
        this.previousNearBookImageButton.setOnClickListener(this.previousNearBookClickListener());
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

    private View.OnClickListener previousNearBookClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwappersToast.makeText(getActivity(), "Loading previous book... #SQN", Toast.LENGTH_SHORT).show();;
            }
        };
    }

    private View.OnClickListener nextNearBookClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwappersToast.makeText(getActivity(), "Opening next book... #SQN", Toast.LENGTH_SHORT).show();;
            }
        };
    }
}
