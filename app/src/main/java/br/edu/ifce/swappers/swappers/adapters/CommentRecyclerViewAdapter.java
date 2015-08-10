package br.edu.ifce.swappers.swappers.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.model.Comment;
import br.edu.ifce.swappers.swappers.util.RecycleViewOnClickListenerHack;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by francisco on 28/07/15.
 */
public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<CommentRecyclerViewAdapter.ViewHolder>{

    private ArrayList<Comment> commentsDataSource;
    private RecycleViewOnClickListenerHack mRecycleViewOnClickListenerHack;

    public CommentRecyclerViewAdapter(ArrayList<Comment> commentsDataSource) {
        this.commentsDataSource = commentsDataSource;
    }

    public void setRecycleViewOnClickListenerHack(RecycleViewOnClickListenerHack r) {
        this.mRecycleViewOnClickListenerHack = r;
    }

    @Override
    public CommentRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.adapter_readers_comments, null);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Comment comment= this.commentsDataSource.get(position);

        viewHolder.authorNameTextView.setText(comment.getAuthorName());
        viewHolder.timeStampTextView.setText(comment.getTimeStamp());
        viewHolder.commentContentTextView.setText(comment.getCommentContent());
//        viewHolder.authorImageView.setImageDrawable(comment.getAuthorImage());

    }

    @Override
    public int getItemCount() {
        return commentsDataSource.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private CircleImageView authorImageView;
        private TextView        authorNameTextView;
        private TextView        timeStampTextView;
        private TextView        commentContentTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            this.authorImageView    = (CircleImageView) itemView.findViewById(R.id.adapter_comment_author_image);
            this.authorNameTextView = (TextView) itemView.findViewById(R.id.adapter_comment_author_name);
            this.timeStampTextView  = (TextView) itemView.findViewById(R.id.adapter_comment_timestamp);
            this.commentContentTextView = (TextView) itemView.findViewById(R.id.adapter_comment_content);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mRecycleViewOnClickListenerHack != null){
                mRecycleViewOnClickListenerHack.onClickListener(view, getAdapterPosition());
            }

        }

        public CircleImageView getAuthorImageView() {
            return authorImageView;
        }

        public void setAuthorImageView(CircleImageView authorImageView) {
            this.authorImageView = authorImageView;
        }

        public TextView getAuthorNameTextView() {
            return authorNameTextView;
        }

        public void setAuthorNameTextView(TextView authorNameTextView) {
            this.authorNameTextView = authorNameTextView;
        }

        public TextView getTimeStampTextView() {
            return timeStampTextView;
        }

        public void setTimeStampTextView(TextView timeStampTextView) {
            this.timeStampTextView = timeStampTextView;
        }

        public TextView getCommentContentTextView() {
            return commentContentTextView;
        }

        public void setCommentContentTextView(TextView commentContentTextView) {
            this.commentContentTextView = commentContentTextView;
        }
    }
}
