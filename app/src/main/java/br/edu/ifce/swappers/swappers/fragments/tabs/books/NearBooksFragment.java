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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.activities.DetailPlaceActivity;
import br.edu.ifce.swappers.swappers.model.Book;
import br.edu.ifce.swappers.swappers.model.Place;
import de.hdodenhof.circleimageview.CircleImageView;

public class NearBooksFragment extends Fragment {

    ImageButton nextNearBookImageButton;
    ImageButton previousNearBookImageButton;
    Button seePlacesOnMapsButton;

    CircleImageView coverNearBookCircleImageView;

    TextView titleNearBookTextView;
    TextView authorsNearBookTextView;

    List<Place> nearPlaces = MockSingleton.INSTANCE.places;
    List<Book> nearBooks = new ArrayList<Book>();
    List<Integer> idPlaces = new ArrayList<>();
    int indexBook = 0;

    public NearBooksFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_near_books, container, false);

        this.initViewComponents(rootView);
        this.initViewListeners();


        if (nearPlaces!=null) {
            for (int i = 0; i < nearPlaces.size(); i++) {
                if (nearPlaces.get(i).getBooks().size()>0) {
                    nearBooks.addAll(nearPlaces.get(i).getBooks());
                    for(int j=0; j<nearPlaces.get(i).getBooks().size(); j++){
                        idPlaces.add(nearPlaces.get(i).getId());
                    }
                }
            }

            if(!nearBooks.isEmpty()) {
                Picasso.with(getActivity()).load(nearBooks.get(0).getPhoto()).into(coverNearBookCircleImageView);
                titleNearBookTextView.setText(nearBooks.get(0).getTitle());
                authorsNearBookTextView.setText(nearBooks.get(0).getAuthor());
            }else{
                Picasso.with(getActivity()).load(R.drawable.blue_book).into(coverNearBookCircleImageView);
                titleNearBookTextView.setText("Ainda não há livros.");
                authorsNearBookTextView.setText("Faça uma doação!");
            }
        }else {
            Picasso.with(getActivity()).load(R.drawable.blue_book).into(coverNearBookCircleImageView);
            titleNearBookTextView.setText("Ainda não há livros.");
            authorsNearBookTextView.setText("");
        }

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
                if(!nearBooks.isEmpty()){
                    for (int j=0; j<nearPlaces.size(); j++){
                        if(idPlaces.get(indexBook) == nearPlaces.get(j).getId()){
                            Place place =  new Place();
                            place.setId(nearPlaces.get(j).getId());
                            place.setName(nearPlaces.get(j).getName());
                            place.setStreet(nearPlaces.get(j).getStreet());
                            place.setNumber(nearPlaces.get(j).getNumber());
                            place.setDistrict(nearPlaces.get(j).getDistrict());
                            place.setCity(nearPlaces.get(j).getCity());
                            place.setStates(nearPlaces.get(j).getStates());
                            place.setCep(nearPlaces.get(j).getCep());
                            place.setHour_func(nearPlaces.get(j).getHour_func());
                            place.setPhoto2(nearPlaces.get(j).getPhoto2());
                            place.setBooks(nearPlaces.get(j).getBooks());

                            Intent detailPlaceActivityIntent = new Intent(getActivity(), DetailPlaceActivity.class);
                            detailPlaceActivityIntent.putExtra("SELECTED_BOOK_PLACE", place);
                            startActivity(detailPlaceActivityIntent);
                        }
                    }
                }


            }
        };
    }

    private View.OnClickListener previousNearBookClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nearBooks.isEmpty()){
                    indexBook--;
                    if (indexBook<0) indexBook = nearBooks.size()-1;

                    titleNearBookTextView.setText(nearBooks.get(indexBook).getTitle());
                    authorsNearBookTextView.setText(nearBooks.get(indexBook).getAuthor());

                    if(!nearBooks.get(indexBook).getPhoto().isEmpty()) {
                        Picasso.with(getActivity()).load(nearBooks.get(indexBook).getPhoto()).into(coverNearBookCircleImageView);
                    }else{
                        Picasso.with(getActivity()).load(R.drawable.blue_book).into(coverNearBookCircleImageView);
                    }
                }

            }
        };
    }

    private View.OnClickListener nextNearBookClickListener() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!nearBooks.isEmpty()){
                    indexBook++;
                    if(indexBook>=nearBooks.size()) indexBook = 0;

                    titleNearBookTextView.setText(nearBooks.get(indexBook).getTitle());
                    authorsNearBookTextView.setText(nearBooks.get(indexBook).getAuthor());

                    if(!nearBooks.get(indexBook).getPhoto().isEmpty()) {
                        Picasso.with(getActivity()).load(nearBooks.get(indexBook).getPhoto()).into(coverNearBookCircleImageView);
                    }else{
                        Picasso.with(getActivity()).load(R.drawable.blue_book).into(coverNearBookCircleImageView);
                    }
                }

            }
        };
    }
}