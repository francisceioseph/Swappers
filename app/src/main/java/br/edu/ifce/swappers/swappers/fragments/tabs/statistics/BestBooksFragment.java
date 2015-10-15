package br.edu.ifce.swappers.swappers.fragments.tabs.statistics;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.model.Book;
import br.edu.ifce.swappers.swappers.model.User;
import br.edu.ifce.swappers.swappers.util.BestBookInterface;
import br.edu.ifce.swappers.swappers.util.StatisticBookTask;
import br.edu.ifce.swappers.swappers.util.SwappersToast;
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

        StatisticBookTask statisticBookTask = new StatisticBookTask(getActivity(),this);
        statisticBookTask.execute();

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

        if(!bestBooks.isEmpty()){
            updateCardView(this.index);

            if(!bestBooks.get(this.index).getPhoto().isEmpty()) {
                Picasso.with(getActivity()).load(bestBooks.get(this.index).getPhoto()).into(coverBestBookCircleImageView);
            }else{
                Picasso.with(getActivity()).load(R.drawable.blue_book).into(coverBestBookCircleImageView);
            }

        }else{
            titleBestBookTextView.setText("Não há livros doados ou retirados este mês");
            authorBestBookTextView.setText("nenhum");
            retrievedBestBookTextView.setText("Adotado 0 vezes");
            donatedBestBookTextView.setText("Doado 0 vezes");
        }
    }

    private void updateCardView(int index){
        this.titleBestBookTextView.setText(bestBooks.get(index).getTitle());
        this.authorBestBookTextView.setText(bestBooks.get(index).getAuthor());
        this.retrievedBestBookTextView.setText("Adotado "+String.valueOf(bestBooks.get(index).getRecovered())+" vezes");
        this.donatedBestBookTextView.setText("Doado "+String.valueOf(bestBooks.get(index).getDonation())+" vezes");

        if(!bestBooks.get(this.index).getPhoto().isEmpty()) {
            Picasso.with(getActivity()).load(bestBooks.get(index).getPhoto()).into(coverBestBookCircleImageView);
        }else{
            Picasso.with(getActivity()).load(R.drawable.blue_book).into(coverBestBookCircleImageView);
        }
    }

}
