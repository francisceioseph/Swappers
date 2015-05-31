package br.edu.ifce.swappers.swappers.fragments.principal;


import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.adapters.SettingsArrayAdapter;
import br.edu.ifce.swappers.swappers.model.SettingsListItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private ListView settingsPersonalInfoListView;
    private ListView settingsAccountListView;

    public SettingsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        ArrayList<SettingsListItem> personalInfoDataSource = this.createPersonalInfoDataSource();
        ArrayList<SettingsListItem> accountInfoDataSource  = this.createAccountDataSource();

        SettingsArrayAdapter personalInfoListViewAdapter = new SettingsArrayAdapter(rootView.getContext(), R.layout.adapter_layout_settings, personalInfoDataSource);
        SettingsArrayAdapter accountInfoListViewAdapter  = new SettingsArrayAdapter(rootView.getContext(), R.layout.adapter_layout_settings, accountInfoDataSource);

        this.settingsPersonalInfoListView = (ListView) rootView.findViewById(R.id.settings_personal_list_view);
        this.settingsAccountListView      = (ListView) rootView.findViewById(R.id.settings_account_list_view);

        this.settingsPersonalInfoListView.setAdapter(personalInfoListViewAdapter);
        this.settingsAccountListView.setAdapter(accountInfoListViewAdapter);

        return rootView;
    }

    private ArrayList<SettingsListItem> createPersonalInfoDataSource() {
        ArrayList<SettingsListItem> dataSource = new ArrayList<SettingsListItem>();

        Drawable profilePictureIcon  = this.getResources().getDrawable(R.drawable.ic_portrait);
        Drawable profileCoverIcon    = this.getResources().getDrawable(R.drawable.ic_cover_photo);
        Drawable birthDateIcon       = this.getResources().getDrawable(R.drawable.ic_cake);
        Drawable cityIcon            = this.getResources().getDrawable(R.drawable.ic_location_city);

        SettingsListItem changeProfilePicture = new SettingsListItem(profilePictureIcon, "Change Profile Picture");
        SettingsListItem changeCoverPicture   = new SettingsListItem(profileCoverIcon, "Change Cover Picture");
        SettingsListItem myBirthDate          = new SettingsListItem(birthDateIcon, "My Birth Date");
        SettingsListItem myCity               = new SettingsListItem(cityIcon, "My City");

        dataSource.add(changeProfilePicture);
        dataSource.add(changeCoverPicture);
        dataSource.add(myBirthDate);
        dataSource.add(myCity);

        return dataSource;
    }

    private ArrayList<SettingsListItem> createAccountDataSource() {
        ArrayList<SettingsListItem> dataSource = new ArrayList<SettingsListItem>();

        Drawable changePasswordIcon  = this.getResources().getDrawable(R.drawable.ic_lock);
        Drawable deleteAccountIcon   = this.getResources().getDrawable(R.drawable.ic_delete_black_48dp);

        SettingsListItem changePassword = new SettingsListItem(changePasswordIcon, "Change Account Password");
        SettingsListItem deleteAccount  = new SettingsListItem(deleteAccountIcon, "Delete Account");

        dataSource.add(changePassword);
        dataSource.add(deleteAccount);

        return dataSource;
    }

}
