package br.edu.ifce.swappers.swappers.miscellaneous.interfaces;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.model.Notification;

/**
 * Created by gracyaneoliveira on 28/01/16.
 */

public interface NotificationTaskInterface {
    public void onReceiveNotification(ArrayList<Notification> notifications);
}
