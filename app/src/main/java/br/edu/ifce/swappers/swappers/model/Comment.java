package br.edu.ifce.swappers.swappers.model;

import android.graphics.drawable.Drawable;

/**
 * Created by francisco on 28/07/15.
 */
public class Comment {
    private Drawable authorImage;

    private String authorName;
    private String timeStamp;
    private String commentContent;

    public Comment() {

    }

    public Comment(Drawable authorImage, String authorName, String timeStamp, String commentContent) {
        this.authorImage = authorImage;
        this.authorName = authorName;
        this.timeStamp = timeStamp;
        this.commentContent = commentContent;
    }

    public Comment(String authorName, String timeStamp, String commentContent) {
       // this.authorImage = ;
        this.authorName = authorName;
        this.timeStamp = timeStamp;
        this.commentContent = commentContent;
    }

    public Drawable getAuthorImage() {
        return authorImage;
    }

    public void setAuthorImage(Drawable authorImage) {
        this.authorImage = authorImage;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
