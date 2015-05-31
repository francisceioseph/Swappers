package br.edu.ifce.swappers.swappers.model;

import android.graphics.drawable.Drawable;

/**
 * Created by francisco on 31/05/15.
 */
public class SettingsListItem {


    private Drawable icon;
    private String text;

    public SettingsListItem(Drawable icon, String text) {
        this.icon = icon;
        this.text = text;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getText() {
        return text;
    }


}
