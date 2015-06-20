package br.edu.ifce.swappers.swappers.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.model.SettingsListItem;

/**
 * Created by francisco on 31/05/15.
 */
public class SettingsArrayAdapter extends ArrayAdapter<SettingsListItem> {

    private LayoutInflater inflater;
    private ArrayList<SettingsListItem> datasource;

    public SettingsArrayAdapter(Context context, int resource, ArrayList<SettingsListItem> datasource) {
        super(context, resource, datasource);

        this.inflater   = LayoutInflater.from(context);
        this.datasource = datasource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        SettingsListItem item = this.datasource.get(position);

        if (convertView == null){
            convertView = inflater.inflate(R.layout.adapter_layout_settings, null, false);
            viewHolder  = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.getIconImageView().setImageDrawable(item.getIcon());
        viewHolder.getSettingsDescriptionTextView().setText(item.getText());

        return convertView;
    }

    public class ViewHolder{
        private View row;
        private ImageView iconImageView;
        private TextView settingsDescriptionTextView;

        public ViewHolder(View row) {
            this.row = row;
            this.iconImageView = (ImageView) row.findViewById(R.id.adapter_settings_icon_image);
            this.settingsDescriptionTextView = (TextView) row.findViewById(R.id.adapter_settings_title_item);
        }

        public View getRow() {
            return row;
        }

        public ImageView getIconImageView() {
            return iconImageView;
        }

        public TextView getSettingsDescriptionTextView() {
            return settingsDescriptionTextView;
        }
    }
}
