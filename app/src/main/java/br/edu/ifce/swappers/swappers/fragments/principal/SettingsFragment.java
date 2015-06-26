package br.edu.ifce.swappers.swappers.fragments.principal;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;

import java.util.ArrayList;
import java.util.Calendar;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.adapters.SettingsArrayAdapter;
import br.edu.ifce.swappers.swappers.model.SettingsListItem;
import br.edu.ifce.swappers.swappers.util.SwappersToast;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements OnDateSetListener{

    private ListView settingsListView;
    private static String BIRTHDAY_DATEPICKER_TAG = "BIRTHDAY_DATEPICKER";

    public SettingsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        ArrayList<SettingsListItem> settingsDataSource = this.createSettingsDataSource();
        SettingsArrayAdapter personalInfoListViewAdapter = new SettingsArrayAdapter(rootView.getContext(), R.layout.adapter_layout_settings, settingsDataSource);

        this.settingsListView = (ListView) rootView.findViewById(R.id.settings_list_view);
        this.settingsListView.setAdapter(personalInfoListViewAdapter);

        this.settingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        //Mudar foto do perfil
                        break;
                    case 1:
                        //Mudar foto de capa
                        break;
                    case 2:
                        //Mudar data de nascimento
                        showBirthdayDatePicker();
                        break;
                    case 3:
                        //Mudar cidade
                        break;
                    case 4:
                        //Mudar senha
                        break;
                    case 5:
                        //Deletar conta
                        break;
                }
            }
        });

        return rootView;
    }

    private ArrayList<SettingsListItem> createSettingsDataSource() {
        ArrayList<SettingsListItem> dataSource = new ArrayList<SettingsListItem>();

        Drawable profilePictureIcon  = this.getResources().getDrawable(R.drawable.ic_portrait);
        Drawable profileCoverIcon    = this.getResources().getDrawable(R.drawable.ic_cover_photo);
        Drawable birthDateIcon       = this.getResources().getDrawable(R.drawable.ic_cake);
        Drawable cityIcon            = this.getResources().getDrawable(R.drawable.ic_location_city);
        Drawable changePasswordIcon  = this.getResources().getDrawable(R.drawable.ic_lock);
        Drawable deleteAccountIcon   = this.getResources().getDrawable(R.drawable.ic_delete_black_48dp);

        SettingsListItem changeProfilePicture = new SettingsListItem(profilePictureIcon, "Change Profile Picture");
        SettingsListItem changeCoverPicture   = new SettingsListItem(profileCoverIcon, "Change Cover Picture");
        SettingsListItem myBirthDate          = new SettingsListItem(birthDateIcon, "My Birth Date");
        SettingsListItem myCity               = new SettingsListItem(cityIcon, "My City");
        SettingsListItem changePassword = new SettingsListItem(changePasswordIcon, "Change Account Password");
        SettingsListItem deleteAccount  = new SettingsListItem(deleteAccountIcon, "Delete Account");

        dataSource.add(changeProfilePicture);
        dataSource.add(changeCoverPicture);
        dataSource.add(myBirthDate);
        dataSource.add(myCity);
        dataSource.add(changePassword);
        dataSource.add(deleteAccount);

        return dataSource;
    }

    private void showBirthdayDatePicker() {
        Calendar currentDate = Calendar.getInstance();
        DatePickerDialog datePickerDialog;

        datePickerDialog = DatePickerDialog.newInstance(this,
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setVibrate(false);
        datePickerDialog.show(getActivity().getSupportFragmentManager(), BIRTHDAY_DATEPICKER_TAG);
        datePickerDialog.setOnDateSetListener(this);
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        String message = String.format("Data escolhida: %d - %d - %d", day, month, year);
        SwappersToast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
}
