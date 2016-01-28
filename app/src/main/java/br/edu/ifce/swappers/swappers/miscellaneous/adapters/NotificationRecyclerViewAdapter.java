package br.edu.ifce.swappers.swappers.miscellaneous.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.RecycleViewOnClickListenerHack;
import br.edu.ifce.swappers.swappers.miscellaneous.utils.ImageUtil;
import br.edu.ifce.swappers.swappers.model.Notification;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by francisco on 27/01/16.
 */
public class NotificationRecyclerViewAdapter extends RecyclerView.Adapter<NotificationRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Notification> dataSource;
    private RecycleViewOnClickListenerHack mRecycleViewOnClickListenerHack;
    private Context context;

    public NotificationRecyclerViewAdapter(Context context,ArrayList<Notification> dataSource) {
        this.dataSource = dataSource;
        this.context = context;
    }

    public Notification getItem(int position){
        return this.dataSource.get(position);
    }

    public void setmRecycleViewOnClickListenerHack(RecycleViewOnClickListenerHack mRecycleViewOnClickListenerHack) {
        this.mRecycleViewOnClickListenerHack = mRecycleViewOnClickListenerHack;
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.adapter_notification, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Notification notification = this.dataSource.get(position);

        Locale currentLocale        = Locale.getDefault();
        SimpleDateFormat formatter  = (SimpleDateFormat) SimpleDateFormat.getDateInstance(SimpleDateFormat.FULL, currentLocale);


        holder.notificationUserName.setText(notification.getUserName());
        holder.notificationText.setText(setUpMessage(notification));

        if(notification.getTimestamp()!=null) {
            holder.notificationDate.setText(formatter.format(notification.getTimestamp()));
        }

        if(notification.getPhotoUser()!=null) {
            holder.notificationImage.setImageBitmap(ImageUtil.stringToBitMap(notification.getPhotoUser()));
        }else{
            Picasso.with(context).load(R.drawable.ic_person_giant).into(holder.notificationImage);
        }
    }

    public String setUpMessage(Notification notification){
        String message = "";

        if(notification.getEventType() !=null){
            if(notification.getEventType().equals("donation")){
                message = "Livro doado : "+notification.getBookTitle()
                        + " | \nLocal da doação : " + notification.getPlaceName()
                        + " - "+notification.getCity() + " - "+notification.getRegion();
            }else if(notification.getEventType().equals("recovered")){
                message = "Livro retirado : "+notification.getBookTitle()
                        + " | \nLocal da retirada : " + notification.getPlaceName()
                        + " - "+notification.getCity() + " - "+notification.getRegion();
            }else if(notification.getEventType().equals("review")) {
                message = "Livro : "+ notification.getBookTitle()
                        + " | \nComentario : "+notification.getReviewText();
            }
        }else {
            message = "Nenhum evento...";
        }

        return message;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private CircleImageView notificationImage;
        private TextView notificationUserName;
        private TextView notificationDate;
        private TextView notificationText;

        public ViewHolder(View itemView) {
            super(itemView);

            this.notificationImage      = (CircleImageView) itemView.findViewById(R.id.notification_image);
            this.notificationUserName   = (TextView) itemView.findViewById(R.id.notification_user_name);
            this.notificationDate       = (TextView) itemView.findViewById(R.id.notification_date);
            this.notificationText       = (TextView) itemView.findViewById(R.id.notification_text);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mRecycleViewOnClickListenerHack != null){
                mRecycleViewOnClickListenerHack.onClickListener(view, getAdapterPosition());
            }

        }

        public CircleImageView getNotificationImage() {
            return notificationImage;
        }

        public void setNotificationImage(CircleImageView notificationImage) {
            this.notificationImage = notificationImage;
        }

        public TextView getNotificationUserName() {
            return notificationUserName;
        }

        public void setNotificationUserName(TextView notificationUserName) {
            this.notificationUserName = notificationUserName;
        }

        public TextView getNotificationDate() {
            return notificationDate;
        }

        public void setNotificationDate(TextView notificationDate) {
            this.notificationDate = notificationDate;
        }

        public TextView getNotificationText() {
            return notificationText;
        }

        public void setNotificationText(TextView notificationText) {
            this.notificationText = notificationText;
        }
    }
}
