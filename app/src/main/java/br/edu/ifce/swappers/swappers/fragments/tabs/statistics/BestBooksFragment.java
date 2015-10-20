package br.edu.ifce.swappers.swappers.fragments.tabs.statistics;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.model.Book;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.BestBookInterface;
import br.edu.ifce.swappers.swappers.miscellaneous.tasks.StatisticBookTask;
import de.hdodenhof.circleimageview.CircleImageView;


public class BestBooksFragment extends Fragment implements BestBookInterface{

    ImageButton nextBestBookImageButton;
    ImageButton previousBestBookImageButton;

    CircleImageView coverBestBookCircleImageView;

    TextView titleBestBookTextView;
    TextView authorBestBookTextView;
    TextView retrievedBestBookTextView;
    TextView donatedBestBookTextView;
    private int index = 0;

    ArrayList<Book> bestBooks = new ArrayList<>();

    public BestBooksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_best_books, container, false);
        this.initViewComponents(rootView);
        this.initViewListeners();

        if(MockSingleton.INSTANCE.getStatisticBook().isEmpty()) {
            StatisticBookTask statisticBookTask = new StatisticBookTask(getActivity(), this);
            statisticBookTask.execute();
        }else{
            bestBooks = MockSingleton.INSTANCE.getStatisticBook();
            updateCardView(index);
        }
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
                if(!bestBooks.isEmpty()) {
                    index--;
                    if (index < 0) index = bestBooks.size() - 1;
                    updateCardView(index);
                }
            }
        };
    }

    private View.OnClickListener nextBestBookClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bestBooks.isEmpty()) {
                    index++;
                    if (index >= bestBooks.size()) index = 0;
                    updateCardView(index);
                }
            }
        };
    }

    @Override
    public void updateStatisticBook(ArrayList<Book> booksList) {
        bestBooks = booksList;
        MockSingleton.INSTANCE.statisticBook = bestBooks;

        if(!bestBooks.isEmpty()){
            updateCardView(this.index);

            if(!bestBooks.get(this.index).getPhoto().isEmpty()) {
                Picasso.with(getActivity()).load(bestBooks.get(this.index).getPhoto()).into(coverBestBookCircleImageView);
            }else{
                Picasso.with(getActivity()).load(R.drawable.blue_book).into(coverBestBookCircleImageView);
            }

        }else{
            titleBestBookTextView.setText(getString(R.string.book_title_for_no_book_statistics_found));
            authorBestBookTextView.setText(getString(R.string.book_author_for_no_book_statistics_found));
            retrievedBestBookTextView.setText(getString(R.string.adoptions_text_for_no_book_statistics_found));
            donatedBestBookTextView.setText(getString(R.string.donations_text_for_no_book_statistics_found));
        }
    }

    private void updateCardView(int index){
        this.titleBestBookTextView.setText(bestBooks.get(index).getTitle());
        this.authorBestBookTextView.setText(bestBooks.get(index).getAuthor());

        int recovered = bestBooks.get(index).getRecovered();
        int donated   = bestBooks.get(index).getDonation();

        this.retrievedBestBookTextView.setText(String.format(getString(R.string.adoptions_text_for_book_statistics), recovered));
        this.donatedBestBookTextView.setText(String.format(getString(R.string.donations_text_for_book_statistics), donated));

        if(!bestBooks.get(this.index).getPhoto().isEmpty()) {
            Picasso.with(getActivity()).load(bestBooks.get(index).getPhoto()).into(coverBestBookCircleImageView);
        }
        else{
            Picasso.with(getActivity()).load(R.drawable.blue_book).into(coverBestBookCircleImageView);
        }
    }

}
