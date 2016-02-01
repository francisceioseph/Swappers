package br.edu.ifce.swappers.swappers.fragments.principal;

/**
 * Last modified by Joamila on 07/12/2015
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.dao.BookDAO;
import br.edu.ifce.swappers.swappers.fragments.dialogs.UserPhotoDialogFragment;
import br.edu.ifce.swappers.swappers.miscellaneous.adapters.SettingsArrayAdapter;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.DeleteUserTaskInterface;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.UpdateBirthDayTaskInterface;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.UpdateCityStateUserTaskInterface;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.UpdateImageTaskInterface;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.UpdatePwdTaskInterface;
import br.edu.ifce.swappers.swappers.miscellaneous.tasks.DeleteUserTask;
import br.edu.ifce.swappers.swappers.miscellaneous.tasks.UpdateCityStateUserTask;
import br.edu.ifce.swappers.swappers.miscellaneous.tasks.UpdateUserBirthDayTask;
import br.edu.ifce.swappers.swappers.miscellaneous.tasks.UpdateUserPwdTask;
import br.edu.ifce.swappers.swappers.miscellaneous.utils.AndroidUtils;
import br.edu.ifce.swappers.swappers.model.SettingsListItem;
import br.edu.ifce.swappers.swappers.miscellaneous.Settings;
import br.edu.ifce.swappers.swappers.miscellaneous.SwappersToast;
import br.edu.ifce.swappers.swappers.model.StateCity;
import br.edu.ifce.swappers.swappers.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements OnDateSetListener, UserPhotoDialogFragment.UserPhotoDialogListener,UpdatePwdTaskInterface,UpdateBirthDayTaskInterface,DeleteUserTaskInterface,UpdateCityStateUserTaskInterface {

    private ListView settingsListView;
    private static String BIRTHDAY_DATEPICKER_TAG = "BIRTHDAY_DATEPICKER";
    Spinner optionStates;
    Spinner optionCities;
    private String cityOptionCities, stateOptionStates;
    private static String[] cities = new String[]{};
    private static String[] STATES =null;
    private Map<String, String> states = new HashMap<>();
    private AlertDialog changePasswordAlertDialog;
    private Map<String,String> mapAcronymeCapital;
    private List<StateCity> stateCityListMock;

//    private void createHashStates(){
//        String[] nameStates = new String[]{"Ceará", "São Paulo",};
//
//        for (int i = 0; i<nameStates.length; i++){
//            states.put(STATES[i], nameStates[i]);
//        }
//    }

    private void createHashStates2(){

        stateCityListMock = MockSingleton.INSTANCE.cityStateList;
        if(!stateCityListMock.isEmpty()) {
        }

        for (StateCity sc : stateCityListMock) {
            String state = sc.getState();
            states.put(mapAcronymeCapital.get(state), state);//CE,Ceará
        }

        int count=0;
        STATES = new String[states.size()];
        for (Map.Entry<String, String> entry : states.entrySet()) {
            STATES[count]= entry.getKey();//CE-SP
            count++;
        }

    }

    private void initCitiesSpinner2(int position,String siglaState){

        String state = "";

        for (Map.Entry<String, String> entry : mapAcronymeCapital.entrySet()) {
            String siglaStateValue = entry.getValue();
            if(siglaStateValue.equals(siglaState)){
                state = entry.getKey();
            }
        }

        ArrayList<String> citiesParticularList = new ArrayList<>();

        for (StateCity sc: stateCityListMock){
            if(sc.getState().equals(state)){
                citiesParticularList.add(sc.getCity());
            }
        }

        cities = citiesParticularList.toArray(new String[citiesParticularList.size()]);
    }

//    private void initCitiesSpinner(int position){
//        switch (position){
//            case 0: cities = Settings.getCitiesAvailableCE();
//                break;
//            case 1: cities = Settings.getCitiesAvailableSP();
//                break;
//        }
//    }

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
        this.mapAcronymeCapital = Settings.mapAcronymCapital();

        createHashStates2();

        return rootView;
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        Long birthDayDate = buildBirthDayToUpdate(year, month, day);
        updateBirthServerServer(birthDayDate);
    }

    private Long buildBirthDayToUpdate(int year, int month, int day){
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        return c.getTime().getTime();
    }

    private void updateBirthServerServer(Long birthDayDate){
          User user = AndroidUtils.loadUser(getActivity());
          user.setBirthday(birthDayDate);

          UpdateUserBirthDayTask updateUserBirthDayTask = new UpdateUserBirthDayTask(getActivity(),this);
          updateUserBirthDayTask.execute(user);
    }

    @Override
    public void onUpdateBirthDayHadFinished(Long birthday) {
        AndroidUtils.updateBirthDaySharedPreferences(getActivity(), birthday);
        MockSingleton.INSTANCE.user = AndroidUtils.loadUser(getActivity());
        SwappersToast.makeText(getActivity(),getString(R.string.settings_sucess_update_birthday_message),Toast.LENGTH_LONG).show();
    }

    private ArrayList<SettingsListItem> createSettingsDataSource() {
        ArrayList<SettingsListItem> dataSource = new ArrayList<>();

        Drawable profilePictureIcon  = ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.ic_portrait);
        Drawable profileCoverIcon    = ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.ic_cover_photo);
        Drawable birthDateIcon       = ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.ic_cake);
        Drawable cityIcon            = ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.ic_city);
        Drawable changePasswordIcon  = ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.ic_lock);
        Drawable logoutIcon          = ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.ic_exit);
        Drawable deleteAccountIcon   = ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.ic_delete_black_48dp);

        SettingsListItem changeProfilePicture = new SettingsListItem(profilePictureIcon, getString(R.string.settings_item_change_profile_picture));
        SettingsListItem changeCoverPicture   = new SettingsListItem(profileCoverIcon, getString(R.string.settings_item_change_cover_picture));
        SettingsListItem myBirthDate          = new SettingsListItem(birthDateIcon, getString(R.string.settings_item_change_birth_date));
        SettingsListItem myCity               = new SettingsListItem(cityIcon, getString(R.string.settings_item_change_city));
        SettingsListItem changePassword = new SettingsListItem(changePasswordIcon, getString(R.string.settings_item_change_password));
        SettingsListItem logout         = new SettingsListItem(logoutIcon, getString(R.string.settings_item_logout));
        SettingsListItem deleteAccount  = new SettingsListItem(deleteAccountIcon, getString(R.string.settings_item_delete_account));

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

    /*
    *This method shows a change profile picture alert.
    * */
    private void changeProfilePicture() {
        UserPhotoDialogFragment dialogFragment = new UserPhotoDialogFragment(getString(R.string.userPhotoDialogTitle),
                getString(R.string.new_profile_picture_message));
        MockSingleton.INSTANCE.codePhoto = 10;
        dialogFragment.show(getActivity().getSupportFragmentManager(), "User Photo Dialog Fragment");
    }

    @Override
    public void onGalleryClick(DialogFragment dialogFragment) {
        //Implementado em MainActivity
    }

    @Override
    public void onCameraClick(DialogFragment dialogFragment) {
        //Implementado em MainActivity
    }

    /*
    *This method shows a change cover picture alert.
    * */
    private void changeCoverPicture() {
        UserPhotoDialogFragment dialogFragment = new UserPhotoDialogFragment(getString(R.string.coverPhotoDialogTitle),
                getString(R.string.new_cover_picture_message));
        MockSingleton.INSTANCE.codePhoto = 20;
        dialogFragment.show(getActivity().getSupportFragmentManager(), "User Photo Dialog Fragment");
    }


    /*
    * This method hows an dialog asking if the user
    * really wants do do a logout from his swappers
    * account
    * */

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
        this.changePasswordAlertDialog = buildChangePasswordDialog();
        this.changePasswordAlertDialog.show();
    }


    /*
    * This method creates an {@link AlertDialog} to change the current city
    * Also, this method initializes the alert and sets its listeners and sets
    * a custom view that contains the editTexts to the alert body.
    *
    * @return The alert to perform the password changes.
    * */
    private AlertDialog buildChangeCityDialog() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.SWDialogTheme);

        View rootView = inflater.inflate(R.layout.dialog_change_city, null);
        optionStates = (Spinner) rootView.findViewById(R.id.option_state_spinner);
        optionCities = (Spinner) rootView.findViewById(R.id.option_city_spinner);

        ArrayAdapter adapterState = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, STATES);
        adapterState.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        optionStates.setAdapter(adapterState);

        optionStates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sigla = (String) parent.getSelectedItem();

                initCitiesSpinner2(position, sigla);

                ArrayAdapter adapterCities = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item,
                        cities);
                adapterCities.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                optionCities.setAdapter(adapterCities);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        builder.setTitle(getString(R.string.dialog_change_city_title));
        builder.setMessage(getString(R.string.dialog_change_city_message));
        builder.setView(rootView);
        builder.setPositiveButton(getString(android.R.string.ok), this.onChangeCityPositiveButton());
        builder.setNegativeButton(getString(android.R.string.cancel), this.onChangeCityNegativeButton());

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

        builder.setTitle(getString(R.string.dialog_change_password_title));
        builder.setMessage(getString(R.string.dialog_change_password_message));
        builder.setView(R.layout.dialog_change_password);
        builder.setPositiveButton(getString(android.R.string.ok), this.onChangePasswordPositiveButton());
        builder.setNegativeButton(getString(android.R.string.cancel), this.onChangePasswordNegativeButton());

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
        builder.setTitle(getString(R.string.dialog_delete_account_title));
        builder.setMessage(getString(R.string.dialog_delete_account_message));
        builder.setPositiveButton(getString(android.R.string.ok), this.onDeleteAccountPositiveButton());
        builder.setNegativeButton(getString(android.R.string.cancel), this.onDeleteAccountNegativeButton());

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
        builder.setTitle(getString(R.string.dialog_logout_title));
        builder.setMessage(getString(R.string.dialog_logout_message));
        builder.setPositiveButton(getString(android.R.string.ok), this.onLogoutAccountPositiveButton());
        builder.setNegativeButton(getString(android.R.string.cancel), this.onLogoutAccountNegativeButton());

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

                //SwappersToast.makeText(getActivity().getApplicationContext(), getString(R.string.delete_account_dialog_positive_button_message), Toast.LENGTH_SHORT).show();
                //TODO send message to ws to disable user account
                //TODO load inicial screen and go out of current screen.

                callDeleteUserTask();
            }
        };

    }

    public void callDeleteUserTask(){
        User user = MockSingleton.INSTANCE.user;
        DeleteUserTask deleteUserTask = new DeleteUserTask(getActivity(),this);
        deleteUserTask.execute(user.getId());
    }


    @Override
    public void onDeleteUserHadFinished() {
        AndroidUtils.deleteUser(getActivity());
        BookDAO bookDAO = new BookDAO(getActivity());
        bookDAO.delete();
        AndroidUtils.startSignInActivity(getActivity());
        SwappersToast.makeText(getActivity(),getActivity().getString(R.string.settings_sucess_delete_user_message),Toast.LENGTH_LONG).show();
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
                dialog.dismiss();
                SwappersToast.makeText(getActivity().getApplicationContext(), getString(R.string.delete_account_dialog_negative_button_message), Toast.LENGTH_SHORT).show();
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
                Context context = getActivity().getApplicationContext();

                AndroidUtils.deleteUser(context);
                BookDAO bookDAO = new BookDAO(getActivity());
                bookDAO.delete();
                SwappersToast.makeText(context, getString(R.string.logout_dialog_positive_button_message), Toast.LENGTH_SHORT).show();
                AndroidUtils.startSignInActivity(context);
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
                dialog.dismiss();
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
                updatePasswordServer();

            }
        };
    }

    private boolean validateCurrentPassword(){

        EditText currentPasswordEditText = (EditText) this.changePasswordAlertDialog.findViewById(R.id.current_password);
        String currentPassword = currentPasswordEditText.getText().toString();

        SharedPreferences manager = AndroidUtils.getSharedPreferences(getActivity());

        String password = manager.getString("password", null);

        if(currentPassword !=null && !currentPassword.equals("")) {

            String currentPasswordCodec = AndroidUtils.codecSHA256(currentPassword);

            if (password != null && currentPasswordCodec.equals(password)) {
                return true;
            }else{
                SwappersToast.makeText(getActivity(),getString(R.string.current_error_password_message),Toast.LENGTH_LONG).show();
            }
        }else{
            SwappersToast.makeText(getActivity(),getString(R.string.current_password_message),Toast.LENGTH_LONG).show();
        }

        return false;
    }


    private boolean validatePassword(String pwd , String pwdConfirmation) {
        if (pwd.length() > 5 && pwdConfirmation.length() > 5) {
            if (pwd.equals(pwdConfirmation)) {
                return true;
            }
            else {
                SwappersToast.makeText(getActivity(), getString(R.string.mismatching_password_error_message), Toast.LENGTH_LONG).show();
                return false;
            }
        }
        else{
            SwappersToast.makeText(getActivity(), getString(R.string.blank_password_error_message), Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private void updatePasswordServer(){

        EditText newPasswordEditText = (EditText) this.changePasswordAlertDialog.findViewById(R.id.new_password);
        EditText newPasswordConfirmationEditText = (EditText) this.changePasswordAlertDialog.findViewById(R.id.new_password_confirmation);

        String newPassword = newPasswordEditText.getText().toString();
        String newPasswordConfirmation = newPasswordConfirmationEditText.getText().toString();

        if(validateCurrentPassword()){
            if (validatePassword(newPassword,newPasswordConfirmation)){

                User user = AndroidUtils.loadUser(getActivity());
                String newPasswordCodec = AndroidUtils.codecSHA256(newPassword);
                user.setPassword(newPasswordCodec);

                UpdateUserPwdTask updateUserPwdTask = new UpdateUserPwdTask(getActivity(),this);
                updateUserPwdTask.execute(user);
            }
        }
    }

    @Override
    public void onUpdatePwdHadFinished() {
        EditText newPasswordEditText = (EditText) this.changePasswordAlertDialog.findViewById(R.id.new_password);
        String newPassword = newPasswordEditText.getText().toString();
        String newPasswordCodec = new String(Hex.encodeHex(DigestUtils.sha256(newPassword.getBytes())));
        AndroidUtils.updatePasswordSharedPreferences(getActivity(),newPasswordCodec);
        MockSingleton.INSTANCE.user = AndroidUtils.loadUser(getActivity());
        SwappersToast.makeText(getActivity(),getString(R.string.settings_sucess_update_password_message),Toast.LENGTH_LONG).show();
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
                dialog.dismiss();
            }
        };
    }

    /*
       * This method creates the listener for the positive button
       * of the change city alert.
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
//                String city = optionCities.getSelectedItem().toString();
//                String state = optionStates.getSelectedItem().toString();
//
//                String nameState = states.get(state);
//
//                MockSingleton.INSTANCE.user.setCity(city);
//                MockSingleton.INSTANCE.user.setState(state);
//                MockSingleton.INSTANCE.userChangeCity = city;
//                MockSingleton.INSTANCE.userChangeState = nameState;

                //SwappersToast.makeText(getActivity(), getString(R.string.change_city_dialog_positive_button_parcial_message) + " " + city + "," + state, Toast.LENGTH_SHORT).show();
                callTaskUpdateCityStateServer();
            }
        };
    }

    public void callTaskUpdateCityStateServer(){
        cityOptionCities = optionCities.getSelectedItem().toString();
        stateOptionStates = states.get(optionStates.getSelectedItem().toString());

        User user = MockSingleton.INSTANCE.user;
        user.setCity(cityOptionCities);
        user.setState(stateOptionStates);

        UpdateCityStateUserTask updateCityStateUserTask = new UpdateCityStateUserTask(getActivity(),this);
        updateCityStateUserTask.execute(user);
    }

    @Override
    public void onUpdateCityStateUserHadFinished() {
        String nameState = states.get(stateOptionStates);

        MockSingleton.INSTANCE.user.setCity(cityOptionCities);
        MockSingleton.INSTANCE.user.setState(stateOptionStates);
        MockSingleton.INSTANCE.userChangeCity = cityOptionCities;
        MockSingleton.INSTANCE.userChangeState = stateOptionStates;

        SwappersToast.makeText(getActivity(), getString(R.string.change_city_dialog_positive_button_parcial_message) + " " + cityOptionCities + "," + stateOptionStates, Toast.LENGTH_SHORT).show();
        AndroidUtils.saveCityState(getActivity(), cityOptionCities, stateOptionStates);

        MockSingleton.INSTANCE.nearBooks.clear();
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
            }
        };
    }

}
