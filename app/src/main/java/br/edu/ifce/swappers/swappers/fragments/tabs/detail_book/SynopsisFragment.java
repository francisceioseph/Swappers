package br.edu.ifce.swappers.swappers.fragments.tabs.detail_book;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.model.Book;
import br.edu.ifce.swappers.swappers.util.AndroidUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class SynopsisFragment extends Fragment {
    private FragmentActivity fragmentActivity;
    TextView synopsisText;
    Intent currentIntent;
    Book book;

    //private RatingBar ratingBar;

    public SynopsisFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_synopsis, container, false);
        synopsisText = (TextView) rootView.findViewById(R.id.synopsis_book);
        fragmentActivity = super.getActivity();
        currentIntent = fragmentActivity.getIntent();
        book = (Book) currentIntent.getSerializableExtra(AndroidUtils.SELECTED_BOOK_ID);
        synopsisText.setText(book.getDescription());

        return rootView;
    }

}
