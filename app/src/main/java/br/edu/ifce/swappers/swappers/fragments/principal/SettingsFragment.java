package br.edu.ifce.swappers.swappers.fragments.principal;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
        this.settingsListView.setOnItemClickListener(this.buildSettingsListDialogListener());

        return rootView;
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        String message = String.format("Data escolhida: %d - %d - %d", day, month, year);
        SwappersToast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    /*
     * This method builds an {@link SettingsListItem} {@link ArrayList}
     * that will be used as data source by the settings listview.
     *
     * @return an arraylist with the settings menu options.
     */
    private ArrayList<SettingsListItem> createSettingsDataSource() {
        ArrayList<SettingsListItem> dataSource = new ArrayList<>();

        Drawable profilePictureIcon  = this.getResources().getDrawable(R.drawable.ic_portrait);
        Drawable profileCoverIcon    = this.getResources().getDrawable(R.drawable.ic_cover_photo);
        Drawable birthDateIcon       = this.getResources().getDrawable(R.drawable.ic_cake);
        Drawable cityIcon            = this.getResources().getDrawable(R.drawable.ic_location_city);
        Drawable changePasswordIcon  = this.getResources().getDrawable(R.drawable.ic_lock);
        Drawable logoutIcon          = this.getResources().getDrawable(R.drawable.ic_exit);
        Drawable deleteAccountIcon   = this.getResources().getDrawable(R.drawable.ic_delete_black_48dp);

        SettingsListItem changeProfilePicture = new SettingsListItem(profilePictureIcon, "Change Profile Picture");
        SettingsListItem changeCoverPicture   = new SettingsListItem(profileCoverIcon, "Change Cover Picture");
        SettingsListItem myBirthDate          = new SettingsListItem(birthDateIcon, "My Birth Date");
        SettingsListItem myCity               = new SettingsListItem(cityIcon, "My City");
        SettingsListItem changePassword = new SettingsListItem(changePasswordIcon, "Change Account Password");
        SettingsListItem logout         = new SettingsListItem(logoutIcon, "Logout");
        SettingsListItem deleteAccount  = new SettingsListItem(deleteAccountIcon, "Delete Account");

        dataSource.add(changeProfilePicture);
        dataSource.add(changeCoverPicture);
        dataSource.add(myBirthDate);
        dataSource.add(myCity);
        dataSource.add(changePassword);
        dataSource.add(logout);
        dataSource.add(deleteAccount);

        return dataSource;
    }

    /*
    * This method creates a listener to handle a settings listview item click.
    * We switch the options using the position param on the onItemClick
    * method. The possibilities are expressed bellow:
    *
    * CASE 0: Change profile picture
    * CASE 1: Change cover picture
    * CASE 2: Change birth date
    * CASE 3: Change current city
    * CASE 4: Change current password
    * CASE 5: Do logout
    * CASE 6: Delete Account
    *
    * */
    private AdapterView.OnItemClickListener buildSettingsListDialogListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        changeProfilePicture();
                        break;
                    case 1:
                        changeCoverPicture();
                        break;
                    case 2:
                        showChangeBirthdayDatePicker();
                        break;
                    case 3:
                        changeCurrentCity();
                        break;
                    case 4:
                        showChangePasswordDialog();
                        break;
                    case 5:
                        showLogoutDialog();
                        break;
                    case 6:
                        showDeleteAccountDialog();
                        break;
                }
            }
        };
    }

    private void showLogoutDialog() {
        AlertDialog logoutDialog = buildLogoutDialog();
        logoutDialog.show();
    }



    /*
    *This method changes the current city
    **/
    private void changeCurrentCity() {
        AlertDialog changeCityDialog = buildChangeCityDialog();
        changeCityDialog.show();
    }

    /*
    *This method shows a delete account alert.
    * */
    private void showDeleteAccountDialog() {
        AlertDialog deleteAccountDialog = buildDeleteAccountDialog();
        deleteAccountDialog.show();
    }

    /*
    *This method shows a change password account alert.
    * */
    private void showChangePasswordDialog() {
        AlertDialog changePasswordAlertDialog = buildChangePasswordDialog();
        changePasswordAlertDialog.show();
    }

    /*
    *This method shows a change cover picture alert.
    * */
    private void changeCoverPicture() {

    }

    /*
    *This method shows a change profile picture alert.
    * */
    private void changeProfilePicture() {

    }

    /*
    * This method creates an {@link AlertDialog} to change the current city
    * Also, this method initializes the alert and sets its listeners and sets
    * a custom view that contains the editTexts to the alert body.
    *
    * @return The alert to perform the password changes.
    * */
    private AlertDialog buildChangeCityDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.SWDialogTheme);
        builder.setTitle("Change Current City");
        builder.setMessage("Type the current city name or click on location button.");
        builder.setView(R.layout.dialog_change_city);
        builder.setPositiveButton("OK", this.onChangeCityPositiveButton());
        builder.setNegativeButton("CANCEL", this.onChangeCityNegativeButton());

        return builder.create();
    }

    /*
    * This method creates an {@link AlertDialog} to the user change
    * his password.
    * Also, this method initializes the alert and sets its listeners and sets
    * a custom view that contains the editTexts to the alert body.
    *
    * @return The alert to perform the password changes.
    * */
    private AlertDialog buildChangePasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.SWDialogTheme);
        builder.setTitle("Change Password");
        builder.setMessage("Set correctly the fields bellow to change your password.");
        builder.setView(R.layout.dialog_change_password);
        builder.setPositiveButton("OK", this.onChangePasswordPositiveButton());
        builder.setNegativeButton("CANCEL", this.onChangePasswordNegativeButton());

        return builder.create();
    }

    /*
    * This method creates a {@link DatePickerDialog} in material
    * design style to ask the correct birth date of the user. I want
    * to remember you that here we user an external library because
    * the default Android DatePickerDialog was not working right.
    *
    * What this method do:
    *   Creates a DatePickerDialog
    *   Initializes it with the current date
    *   Set its listener
    *   Shows it on the screen.
    * */
    private void showChangeBirthdayDatePicker() {
        Calendar currentDate = Calendar.getInstance();
        DatePickerDialog datePickerDialog;

        datePickerDialog = DatePickerDialog.newInstance(this,
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setVibrate(false);
        datePickerDialog.setOnDateSetListener(this);
        datePickerDialog.show(getActivity().getSupportFragmentManager(), BIRTHDAY_DATEPICKER_TAG);
    }

    /*
    * This method creates an {@link AlertDialog} to ask the user
    * if he really wants to delete his swappers account.
    * Also, this method initializes the alert and sets its listeners.
    *
    * @return The alert that asks if the user really wants to delete
    * his swappers account.
    * */
    private AlertDialog buildDeleteAccountDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.SWDialogTheme);
        builder.setTitle("Delete Account");
        builder.setMessage("Do you really want to delete your account?");
        builder.setPositiveButton("OK", this.onDeleteAccountPositiveButton());
        builder.setNegativeButton("CANCEL", this.onDeleteAccountNegativeButton());

        return builder.create();
    }

    /*
    * This method creates an {@link AlertDialog} to ask the user
    * if he really wants to logout from his swappers account.
    * Also, this method initializes the alert and sets its listeners.
    *
    * @return The alert that asks if the user really wants to delete
    * his swappers account.
    * */
    private AlertDialog buildLogoutDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.SWDialogTheme);
        builder.setTitle("Logout");
        builder.setMessage("Do you really want to logout from your account?");
        builder.setPositiveButton("OK", this.onLogoutAccountPositiveButton());
        builder.setNegativeButton("CANCEL", this.onLogoutAccountNegativeButton());

        return builder.create();
    }

    /*
   * This method creates the listener for the positive button
   * of the delete account alert.
   * This method also should:
   *   Connect to WS to communicate account deletion
   *   Clean the user account from the device
   *   Do a logout from the app
   *   Load the Sign In screen and clean the stack.
   *
   * @return The listener for the positive button of the delete
   * account listener.
   * */
    private DialogInterface.OnClickListener onDeleteAccountPositiveButton() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SwappersToast.makeText(getActivity().getApplicationContext(), "Account Deleted Successfully", Toast.LENGTH_SHORT).show();
                //TODO send message to ws to disable user account
                //TODO load inicial screen and go out of current screen.
            }
        };
    }

    /*
       * This method creates the listener for the negative button
       * of the delete account alert.
       * This method also should:
       *   Show a pretty message saying that we're so happy for
       *   the had changed his mind.
       *
       * @return The listener for the negative button of the delete
       * account listener.
       * */
    private DialogInterface.OnClickListener onDeleteAccountNegativeButton() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SwappersToast.makeText(getActivity().getApplicationContext(), "Thanks so much! We're working so hard to make you really happy. :]", Toast.LENGTH_SHORT).show();
            }
        };
    }

    /*
   * This method creates the listener for the positive button
   * of the logout account alert.
   * This method also should:
   *   Connect to WS to communicate account logout
   *   Clean the user account from the device
   *   Do a logout from the app
   *   Load the Sign In screen and clean the stack.
   *
   * @return The listener for the positive button of the delete
   * account listener.
   * */
    private DialogInterface.OnClickListener onLogoutAccountPositiveButton() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SwappersToast.makeText(getActivity().getApplicationContext(), "Logout done successfully", Toast.LENGTH_SHORT).show();
                //TODO send message to ws to disable user account
                //TODO load inicial screen and go out of current screen.
            }
        };
    }

    /*
       * This method creates the listener for the negative button
       * of the delete account alert.
       * This method also should:
       *   Show a pretty message saying that we're so happy for
       *   the had changed his mind.
       *
       * @return The listener for the negative button of the delete
       * account listener.
       * */
    private DialogInterface.OnClickListener onLogoutAccountNegativeButton() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SwappersToast.makeText(getActivity().getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            }
        };
    }

    /*
       * This method creates the listener for the positive button
       * of the change password alert.
       * This method also should:
       *   Connect to WS to validate the current password
       *   validate if the two new passwords fields contains equal
       *   passwords.
       *   Alert the user if something is not correct
       *   and, if everything was ok, communicate to WS the password
       *   changes and store it local in a secure way.
       *
       * @return The listener for the positive button of the delete
       * account listener.
       * */
    private DialogInterface.OnClickListener onChangePasswordPositiveButton() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SwappersToast.makeText(getActivity(), "Password changed Successfully", Toast.LENGTH_SHORT).show();
            }
        };
    }

    /*
      * This method creates the listener for the negative button
      * of the change password alert.
      * This method also should:
      *   Show a pretty message saying that the actions was cancelled.
      *
      * @return The listener for the negative button of the delete
      * account listener.
      * */
    private DialogInterface.OnClickListener onChangePasswordNegativeButton() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SwappersToast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
            }
        };
    }

    /*
       * This method creates the listener for the positive button
       * of the change ciry alert.
       * This method also should:
       *   Connect to WS to communicate that the city changed
       *   Update current city local field
       *
       * @return The listener for the positive button of the delete
       * account listener.
       * */
    private DialogInterface.OnClickListener onChangeCityPositiveButton() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SwappersToast.makeText(getActivity(), "City changed Successfully", Toast.LENGTH_SHORT).show();
            }
        };
    }

    /*
      * This method creates the listener for the negative button
      * of the change city alert.
      * This method also should:
      *   Show a pretty message saying that the actions was cancelled.
      *
      * @return The listener for the negative button of the delete
      * account listener.
      * */
    private DialogInterface.OnClickListener onChangeCityNegativeButton() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SwappersToast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
            }
        };
    }
}
