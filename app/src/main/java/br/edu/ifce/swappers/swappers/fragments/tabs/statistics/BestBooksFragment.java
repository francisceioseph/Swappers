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
import de.hdodenhof.circleimageview.CircleImageView;


public class BestBooksFragment extends Fragment {

    ImageButton nextBestBookImageButton;
    ImageButton previousBestBookImageButton;

    CircleImageView coverBestBookCircleImageView;

    TextView titleBestBookTextView;
    TextView authorBestBookTextView;
    TextView retrievedBestBookTextView;
    TextView donatedBestBookTextView;

    public BestBooksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_best_books, container, false);
        this.initViewComponents(rootView);
        this.initViewListeners();

        return rootView;
    }

    private void initViewComponents(View rootView) {
        this.nextBestBookImageButton      = (ImageButton) rootView.findViewById(R.id.next_best_book_image_button);
        this.previousBestBookImageButton  = (ImageButton) rootView.findViewById(R.id.previous_best_book_image_button);

        this.coverBestBookCircleImageView = (CircleImageView) rootView.findViewById(R.id.cover_best_book);

        this.titleBestBookTextView = (TextView) rootView.findViewById(R.id.best_book_title);
        this.authorBestBookTextView = (TextView) rootView.findViewById(R.id.best_book_author);
        this.retrievedBestBookTextView = (TextView) rootView.findViewById(R.id.times_retrieved_text_view);
        this.donatedBestBookTextView = (TextView) rootView.findViewById(R.id.times_donated_text_view);

    }

    private void initViewListeners() {
        this.nextBestBookImageButton.setOnClickListener(this.nextBestBookClickListener());
        this.previousBestBookImageButton.setOnClickListener(this.previousBestBookClickListener());
    }

    private View.OnClickListener previousBestBookClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Loading previous book... #SQN", Toast.LENGTH_SHORT).show();;
            }
        };
    }

    private View.OnClickListener nextBestBookClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Opening next book... #SQN", Toast.LENGTH_SHORT).show();;
            }
        };
    }


}
