/**
 * Last modified by Joamila on 07/12/2015
 */

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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.activities.DetailPlaceActivity;
import br.edu.ifce.swappers.swappers.dao.BookDAO;
import br.edu.ifce.swappers.swappers.miscellaneous.CategoryBook;
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
    TextView dateDonationRecommendationTextView;

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

        setRecommendations();

        return rootView;
    }

    private void setRecommendations(){

        if (nearPlaces!=null && MockSingleton.INSTANCE.nearBooks.size()>0) {
            ArrayList<String> authorsForRecommendation = getAuthorsForRecommendations();

            for(int i = 0; i < MockSingleton.INSTANCE.nearBooks.size(); i++){
                for (int j = 0; j < authorsForRecommendation.size(); j++){
                    if(MockSingleton.INSTANCE.nearBooks.get(i).getBook().getAuthor().equals(authorsForRecommendation.get(j))){
                        recommendationBooks.add(MockSingleton.INSTANCE.nearBooks.get(i));
                    }
                }
            }

            if(recommendationBooks.isEmpty()){
                Picasso.with(getActivity()).load(R.drawable.blue_book).into(coverRecommendationCircleImageView);
                titleRecommendationTextView.setText(getString(R.string.no_recommendations_yet));
                authorsRecommendationTextView.setText(getString(R.string.we_working));
                dateDonationRecommendationTextView.setText("");
            }
            else if(!recommendationBooks.isEmpty()) {
                Picasso.with(getActivity()).load(recommendationBooks.get(0).getBook().getPhoto()).into(coverRecommendationCircleImageView);
                titleRecommendationTextView.setText(recommendationBooks.get(0).getBook().getTitle());
                authorsRecommendationTextView.setText(recommendationBooks.get(0).getBook().getAuthor());
                dateDonationRecommendationTextView.setText(getString(R.string.date_donation_description) + " " +
                        getBookDateDonation(recommendationBooks.get(0).getBook()));
            }

        }else {
            Picasso.with(getActivity()).load(R.drawable.blue_book).into(coverRecommendationCircleImageView);
            titleRecommendationTextView.setText(getString(R.string.no_recommendations_yet));
            authorsRecommendationTextView.setText("");
            dateDonationRecommendationTextView.setText("");
        }
    }

    private ArrayList<String> getAuthorsForRecommendations(){
        BookDAO bookDAO = new BookDAO(getActivity());
        ArrayList<Book> booksFavorites;
        ArrayList<Book> booksAdopted;
        ArrayList<String> authors = new ArrayList<>();

        if (!bookDAO.getBookAllByCategory(CategoryBook.FAVORITE.toString()).isEmpty()) {
            booksFavorites = bookDAO.getBookAllByCategory(CategoryBook.FAVORITE.toString());

            for(int i = 0; i < booksFavorites.size(); i++){
                authors.add(booksFavorites.get(i).getAuthor());
            }
        }

        if (!bookDAO.getBookAllByCategory(CategoryBook.RETRIEVED.toString()).isEmpty()) {
            booksAdopted = bookDAO.getBookAllByCategory(CategoryBook.RETRIEVED.toString());

            for(int i = 0; i < booksAdopted.size(); i++){
                authors.add(booksAdopted.get(i).getAuthor());
            }
        }

        return authors;
    }

    private void initViewComponents(View rootView) {
        this.nextRecommendationImageButton      = (ImageButton) rootView.findViewById(R.id.next_recommendation_image_button);
        this.previousRecommendationImageButton  = (ImageButton) rootView.findViewById(R.id.previous_recommendation_image_button);
        this.seePlacesOnMapsButton              = (Button) rootView.findViewById(R.id.see_places_of_recommendation);

        this.coverRecommendationCircleImageView = (CircleImageView) rootView.findViewById(R.id.cover_recommendation_book);

        this.titleRecommendationTextView        = (TextView) rootView.findViewById(R.id.title_recommendation);
        this.authorsRecommendationTextView      = (TextView) rootView.findViewById(R.id.authors_recommendation);
        this.dateDonationRecommendationTextView = (TextView) rootView.findViewById(R.id.date_donation_recommendation);
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
                if(!recommendationBooks.isEmpty()){
                    indexBookRec--;
                    if (indexBookRec<0) indexBookRec = recommendationBooks.size()-1;

                    titleRecommendationTextView.setText(recommendationBooks.get(indexBookRec).getBook().getTitle());
                    authorsRecommendationTextView.setText(recommendationBooks.get(indexBookRec).getBook().getAuthor());
                    dateDonationRecommendationTextView.setText(getString(R.string.date_donation_description) + " " +
                            getBookDateDonation(recommendationBooks.get(indexBookRec).getBook()));

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
                if(!recommendationBooks.isEmpty()){
                    indexBookRec++;
                    if(indexBookRec>=recommendationBooks.size()) indexBookRec = 0;

                    titleRecommendationTextView.setText(recommendationBooks.get(indexBookRec).getBook().getTitle());
                    authorsRecommendationTextView.setText(recommendationBooks.get(indexBookRec).getBook().getAuthor());
                    dateDonationRecommendationTextView.setText(getString(R.string.date_donation_description) + " " +
                            getBookDateDonation(recommendationBooks.get(indexBookRec).getBook()));

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

    public String getBookDateDonation(Book book){
        String bookDateDonation ="";

        Calendar dateDonationCalendar = new GregorianCalendar();
        if(book.getDateDonation()!=null){
            dateDonationCalendar.setTimeInMillis(book.getDateDonation().getTime());

            bookDateDonation = String.valueOf(dateDonationCalendar.get(Calendar.DAY_OF_MONTH)) + "/" +
                    String.valueOf(dateDonationCalendar.get(Calendar.MONTH)) + "/" +
                    String.valueOf(dateDonationCalendar.get(Calendar.YEAR));
        }

        return bookDateDonation;
    }
}
