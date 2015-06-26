package br.edu.ifce.swappers.swappers.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.model.Book;
import android.widget.Toast;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.util.AndroidUtils;
import br.edu.ifce.swappers.swappers.util.SwappersToast;

/**
 * Created by francisco on 16/06/15.
 */
public class BookRecyclerViewAdapter extends RecyclerView.Adapter<BookRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Book> booksDataSource;

    public BookRecyclerViewAdapter(ArrayList<Book> books) {
        this.booksDataSource = books;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.adapter_layout_book_info, null);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Book book = this.booksDataSource.get(position);

        viewHolder.bookTitleTextView.setText(book.getTitle());
        viewHolder.bookAuthorsTextView.setText(book.getAuthor());
        viewHolder.bookPublisherTextView.setText(book.getPublisher());
        viewHolder.bookEvaluationAvarageTextView.setText(String.format("%.1f", book.getEvaluationAvarage()));
        viewHolder.bookUserEvaluationRatingBar.setRating(book.getUserEvaluation());
    }

    @Override
    public int getItemCount() {
        return this.booksDataSource.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView  bookTitleTextView;
        private TextView  bookAuthorsTextView;
        private TextView  bookPublisherTextView;
        private TextView  bookEvaluationAvarageTextView;
        private RatingBar bookUserEvaluationRatingBar;

        public ViewHolder(View itemView) {
            super(itemView);

            this.bookTitleTextView             = (TextView)  itemView.findViewById(R.id.adapter_book_title);
            this.bookAuthorsTextView           = (TextView)  itemView.findViewById(R.id.adapter_book_authors);
            this.bookPublisherTextView         = (TextView)  itemView.findViewById(R.id.adapter_book_publisher);
            this.bookEvaluationAvarageTextView = (TextView)  itemView.findViewById(R.id.adapter_book_avarage);
            this.bookUserEvaluationRatingBar   = (RatingBar) itemView.findViewById(R.id.adapter_book_rating_bar);
        }

        public TextView getBookTitleTextView() {
            return bookTitleTextView;
        }

        public void setBookTitleTextView(TextView bookTitleTextView) {
            this.bookTitleTextView = bookTitleTextView;
        }

        public TextView getBookAuthorsTextView() {
            return bookAuthorsTextView;
        }

        public void setBookAuthorsTextView(TextView bookAuthorsTextView) {
            this.bookAuthorsTextView = bookAuthorsTextView;
        }

        public TextView getBookPublisherTextView() {
            return bookPublisherTextView;
        }

        public void setBookPublisherTextView(TextView bookPublisherTextView) {
            this.bookPublisherTextView = bookPublisherTextView;
        }

        public TextView getBookEvaluationAvarageTextView() {
            return bookEvaluationAvarageTextView;
        }

        public void setBookEvaluationAvarageTextView(TextView bookEvaluationAvarageTextView) {
            this.bookEvaluationAvarageTextView = bookEvaluationAvarageTextView;
        }

        public RatingBar getBookUserEvaluationRatingBar() {
            return bookUserEvaluationRatingBar;
        }

        public void setBookUserEvaluationRatingBar(RatingBar bookUserEvaluationRatingBar) {
            this.bookUserEvaluationRatingBar = bookUserEvaluationRatingBar;
        }

        @Override
        public void onClick(View v) {
            Log.i("tag", "Clique");
            Toast toast = SwappersToast.makeText(v.getContext(), "cliques", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}
