package br.edu.ifce.swappers.swappers.fragments.tabs.books;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.activities.DetailPlaceActivity;
import br.edu.ifce.swappers.swappers.miscellaneous.SwappersToast;
import br.edu.ifce.swappers.swappers.model.Book;
import br.edu.ifce.swappers.swappers.model.BookWithPlace;
import br.edu.ifce.swappers.swappers.model.Place;
import de.hdodenhof.circleimageview.CircleImageView;

public class RecommendationsFragment extends Fragment {

    ImageButton nextRecommendationImageButton;
    ImageButton previousRecommendationImageButton;
    Button seePlacesOnMapsButton;

    CircleImageView coverRecommendationCircleImageView;

    TextView titleRecommendationTextView;
    TextView authorsRecommendationTextView;

    List<Place> nearPlaces = MockSingleton.INSTANCE.places;
    List<BookWithPlace> recommendationBooks = new ArrayList<>();
    int indexBookRec = 0;

    public RecommendationsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recomendations, container, false);

        this.initViewComponents(rootView);
        this.initViewListeners();

        Log.i("INDEX", String.valueOf(indexBookRec));

        if (nearPlaces!=null) {
            for (int i = 0; i < nearPlaces.size(); i++) {
                if (nearPlaces.get(i).getBooks().size()>0) {
                    for (int j = 0; j<nearPlaces.get(i).getBooks().size(); j++){
                        recommendationBooks.add(new BookWithPlace(nearPlaces.get(i).getBooks().get(j), nearPlaces.get(i).getId()));
                    }
                }
            }

            Collections.shuffle(recommendationBooks);

            if(!recommendationBooks.isEmpty()) {
                Picasso.with(getActivity()).load(recommendationBooks.get(0).getBook().getPhoto()).into(coverRecommendationCircleImageView);
                titleRecommendationTextView.setText(recommendationBooks.get(0).getBook().getTitle());
                authorsRecommendationTextView.setText(recommendationBooks.get(0).getBook().getAuthor());
            }else{
                Picasso.with(getActivity()).load(R.drawable.blue_book).into(coverRecommendationCircleImageView);
                titleRecommendationTextView.setText("Ainda não há recomendações.");
                authorsRecommendationTextView.setText("");
            }
        }else {
            Picasso.with(getActivity()).load(R.drawable.blue_book).into(coverRecommendationCircleImageView);
            titleRecommendationTextView.setText("Ainda não há recomendações.");
            authorsRecommendationTextView.setText("");
        }


        //titleRecommendationTextView.setText("O Segundo Sexo");
        //authorsRecommendationTextView.setText("Simone de Beauvoir");

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
                //SwappersToast.makeText(getActivity(), getString(R.string.service_not_implemented), Toast.LENGTH_SHORT).show();;

                if(!recommendationBooks.isEmpty()){
                    for (int j=0; j<nearPlaces.size(); j++){
                        if(recommendationBooks.get(indexBookRec).getIdPlace() == nearPlaces.get(j).getId()){
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

    private View.OnClickListener previousRecommendationClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SwappersToast.makeText(getActivity(), getString(R.string.service_not_implemented), Toast.LENGTH_SHORT).show();;
                Log.i("INDEX", String.valueOf(indexBookRec));
                if(!recommendationBooks.isEmpty()){
                    if(recommendationBooks.size()<=5){
                        indexBookRec--;
                        if (indexBookRec<0) indexBookRec = 0;
                    }
                    else if (recommendationBooks.size()>5){
                        indexBookRec--;
                        if (indexBookRec<0) indexBookRec = 4;// (recommendationBooks.size()/4)-1;
                    }

                    titleRecommendationTextView.setText(recommendationBooks.get(indexBookRec).getBook().getTitle());
                    authorsRecommendationTextView.setText(recommendationBooks.get(indexBookRec).getBook().getAuthor());

                    if(!recommendationBooks.get(indexBookRec).getBook().getPhoto().isEmpty()) {
                        Picasso.with(getActivity()).load(recommendationBooks.get(indexBookRec).getBook().getPhoto()).
                                into(coverRecommendationCircleImageView);
                    }else{
                        Picasso.with(getActivity()).load(R.drawable.blue_book).into(coverRecommendationCircleImageView);
                    }
                }
            }
        };
    }

    private View.OnClickListener nextRecommendationClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SwappersToast.makeText(getActivity(), getString(R.string.service_not_implemented), Toast.LENGTH_SHORT).show();;

                if(!recommendationBooks.isEmpty()){
                    if (recommendationBooks.size()<=5){
                        indexBookRec++;
                        if(indexBookRec>0) indexBookRec = 0;
                    }
                    else if (recommendationBooks.size()>5){
                        indexBookRec++;
                        if(indexBookRec>=5) indexBookRec = 0;
                    }

                    titleRecommendationTextView.setText(recommendationBooks.get(indexBookRec).getBook().getTitle());
                    authorsRecommendationTextView.setText(recommendationBooks.get(indexBookRec).getBook().getAuthor());

                    if(!recommendationBooks.get(indexBookRec).getBook().getPhoto().isEmpty()) {
                        Picasso.with(getActivity()).load(recommendationBooks.get(indexBookRec).getBook().getPhoto()).
                                into(coverRecommendationCircleImageView);
                    }else{
                        Picasso.with(getActivity()).load(R.drawable.blue_book).into(coverRecommendationCircleImageView);
                    }
                }

            }
        };
    }
}
