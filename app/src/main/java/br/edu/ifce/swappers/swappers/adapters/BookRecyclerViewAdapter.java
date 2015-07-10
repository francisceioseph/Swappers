package br.edu.ifce.swappers.swappers.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.model.Book;

/**
 * Created by francisco on 16/06/15.
 */
public class BookRecyclerViewAdapter extends RecyclerView.Adapter<BookRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Book> booksDataSource;
    private Context context;

    public BookRecyclerViewAdapter(Context context, ArrayList<Book> books) {
        this.booksDataSource = books;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.adapter_layout_book_info,viewGroup,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Book book = this.booksDataSource.get(position);

        viewHolder.bookTitleTextView.setText(book.getTitle());
        viewHolder.bookAuthorsTextView.setText(book.getAuthor());
        viewHolder.bookPublisherTextView.setText(book.getPublisher());
        viewHolder.bookEvaluationAvarageTextView.setText(String.format("%.1f", book.getEvaluationAvarage()));
        viewHolder.bookUserEvaluationRatingBar.setRating(book.getEvaluationAvarage());

        if(!book.getPhoto().isEmpty()) {
            Picasso.with(context).load(book.getPhoto()).into(viewHolder.bookImageImageView);
        }else{
            Picasso.with(context).load("https://books.google.com.br/googlebooks/images/no_cover_thumb.gif").into(viewHolder.bookImageImageView);
        }
    }

    @Override
    public int getItemCount() {
        return this.booksDataSource.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView  bookTitleTextView;
        private TextView  bookAuthorsTextView;
        private TextView  bookPublisherTextView;
        private TextView  bookEvaluationAvarageTextView;
        private RatingBar bookUserEvaluationRatingBar;
        private ImageView bookImageImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            this.bookTitleTextView             = (TextView)  itemView.findViewById(R.id.adapter_book_title);
            this.bookAuthorsTextView           = (TextView)  itemView.findViewById(R.id.adapter_book_authors);
            this.bookPublisherTextView         = (TextView)  itemView.findViewById(R.id.adapter_book_publisher);
            this.bookEvaluationAvarageTextView = (TextView)  itemView.findViewById(R.id.adapter_book_avarage);
            this.bookUserEvaluationRatingBar   = (RatingBar) itemView.findViewById(R.id.adapter_book_rating_bar);
            this.bookImageImageView            = (ImageView) itemView.findViewById(R.id.adapter_book_cover);
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

        public ImageView getBookImageImageView() {
            return bookImageImageView;
        }

        public void setBookImageImageView(ImageView bookImageImageView) {
            this.bookImageImageView = bookImageImageView;
        }
    }
}
